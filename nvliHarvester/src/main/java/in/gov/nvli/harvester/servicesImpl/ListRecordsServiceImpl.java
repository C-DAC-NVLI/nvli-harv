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
import in.gov.nvli.harvester.beans.MetadataType;
import in.gov.nvli.harvester.beans.OAIDC;
import in.gov.nvli.harvester.constants.CommonConstants;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author richa
 */
@Service
public class ListRecordsServiceImpl implements ListRecordsService {

  private static int i = 0;

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
  public void getListRecord(String baseUrl) throws MalformedURLException, IOException, JAXBException, ParseException {
    connection = HttpURLConnectionUtil.getConnection(baseUrl, "GET", "", "");
    int responseCode = connection.getResponseCode();

    if (responseCode == 200) {
      String response = OAIResponseUtil.createResponseFromXML(connection);

      OAIPMHtype getRecordObj = UnmarshalUtils.xmlToOaipmh(response);

      List<RecordType> records = getRecordObj.getListRecords().getRecord();

      HarRecord harRecord;
      HarRecordMetadataDc recordMetadataDc;

      List<HarRecord> harRecords = new ArrayList<>();
      List<HarRecordMetadataDc> recordMetadataDcs = new ArrayList<>();
      HarMetadataType metadataType = metadataTypeDao.getMetadataType(CommonConstants.OAIDC);
      for (RecordType record : records) {

        harRecord = new HarRecord();
        harRecord.setIdentifier(record.getHeader().getIdentifier());
        DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        Date sourceDate = formatter.parse(record.getHeader().getDatestamp());
        harRecord.setSoureDatestamp(sourceDate);
        harRecord.setMetadataTypeId(metadataType);
        List<AboutType> aboutTypes = record.getAbout();
        String temp = "";

        if (aboutTypes != null) {
          for (AboutType about : aboutTypes) {
            temp += about;
          }
        }
        harRecord.setAbout(temp);

        harRecords.add(harRecord);

        recordMetadataDc = new HarRecordMetadataDc();
        recordMetadataDc.setRecordId(harRecord);
        getRecordService.getMetadataFromObj(record.getMetadata().getOaidc(), recordMetadataDc);

        recordMetadataDcs.add(recordMetadataDc);

      }
      recordDao.saveListHarRecord(harRecords);
      metadataDcDao.saveList(recordMetadataDcs);
      System.out.println("Saved======================== "+harRecords.size()+" metadata "+recordMetadataDcs.size());
      System.out.println("resumption token " + getRecordObj.getListRecords().getResumptionToken().getValue());
      if (getRecordObj.getListRecords().getResumptionToken() != null) {
        try {
          Thread.sleep(1000);
          String urlSubtr[] = baseUrl.split("&");
          String requestUrl = urlSubtr[0] + "&resumptionToken=" + getRecordObj.getListRecords().getResumptionToken().getValue();
          System.out.println("Called func "+(++i));
          getListRecord(requestUrl);
        } catch (InterruptedException ex) {
          Logger.getLogger(ListRecordsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

      }

    }

  }

}
