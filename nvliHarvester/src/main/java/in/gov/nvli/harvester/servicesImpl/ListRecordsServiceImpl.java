/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.AboutType;
import in.gov.nvli.harvester.OAIPMH_beans.GetRecordType;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.RecordType;
import in.gov.nvli.harvester.beans.HarMetadataType;
import in.gov.nvli.harvester.beans.HarRecord;
import in.gov.nvli.harvester.beans.HarRecordMetadataDc;
import in.gov.nvli.harvester.beans.OAIDC;
import in.gov.nvli.harvester.dao.HarMetadataTypeDao;
import in.gov.nvli.harvester.dao.HarRecordDao;
import in.gov.nvli.harvester.dao.HarRecordMetadataDcDao;
import in.gov.nvli.harvester.daoImpl.HarRecordMetadataDcDaoImpl;
import in.gov.nvli.harvester.services.GetRecordService;
import in.gov.nvli.harvester.services.ListRecordsService;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author richa
 */
public class ListRecordsServiceImpl implements ListRecordsService {

  private HttpURLConnection connection;
  @Autowired
  private GetRecordService getRecordService;

  @Autowired
  private HarRecordMetadataDcDao metadataDcDao;

  @Autowired
  private HarRecordDao recordDao;
  
  @Autowired
  private HarMetadataTypeDao metadataTypeDao;

  @Override
  public void getListRecord(String baseUrl) throws MalformedURLException, IOException, JAXBException {
    connection = HttpURLConnectionUtil.getConnection(baseUrl, "GET", "", "");
    int responseCode = connection.getResponseCode();
    String response = OAIResponseUtil.createResponseFromXML(connection);

    OAIPMHtype getRecordObj = UnmarshalUtils.xmlToOaipmh(response);

    List<RecordType> records = getRecordObj.getListRecords().getRecord();
    int i = 0;
    HarRecord harRecord;
    HarRecordMetadataDc recordMetadataDc;
    for (RecordType record : records) {
      harRecord = new HarRecord();
      harRecord.setIdentifier(record.getHeader().getIdentifier());
      harRecord.setMetadataTypeId(metadataTypeDao.getMetadataType((short)1));
      List<AboutType> aboutTypes = getRecordObj.getGetRecord().getRecord().getAbout();
      String temp = "";

      if (aboutTypes != null) {
        for (AboutType about : aboutTypes) {
          temp += about;
        }
        harRecord.setAbout(temp);
      }
      

      recordMetadataDc = new HarRecordMetadataDc();
      recordMetadataDc.setRecordId(harRecord);
      getRecordService.getMetadataFromObj(record.getMetadata().getOaidc(), recordMetadataDc);
      metadataDcDao.save(recordMetadataDc);
      System.out.println("Identifier " + (++i) + record.getHeader().getIdentifier());
      System.out.println("Metatadata " + (++i) + record.getMetadata().getOaidc().getSubject());
    }
    System.out.println("resumption token " + getRecordObj.getListRecords().getResumptionToken());

  }

  public static void main(String[] args) throws Exception {
    new ListRecordsServiceImpl().getListRecord("http://export.arxiv.org/oai2?verb=ListRecords&metadataPrefix=oai_dc");
  }

}
