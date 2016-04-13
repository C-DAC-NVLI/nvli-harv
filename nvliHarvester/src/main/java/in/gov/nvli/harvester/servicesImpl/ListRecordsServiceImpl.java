/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.GetRecordType;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.RecordType;
import in.gov.nvli.harvester.beans.OAIDC;
import in.gov.nvli.harvester.services.ListRecordsService;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 *
 * @author richa
 */
public class ListRecordsServiceImpl implements ListRecordsService{
  
   private HttpURLConnection connection;

  @Override
  public void getListRecord(String baseUrl) throws MalformedURLException, IOException, JAXBException {
     connection = HttpURLConnectionUtil.getConnection(baseUrl, "GET", "", "");
    int responseCode = connection.getResponseCode();
    String response = OAIResponseUtil.createResponseFromXML(connection);

    OAIPMHtype getRecordObj = UnmarshalUtils.xmlToOaipmh(response);

    List<RecordType> records=getRecordObj.getListRecords().getRecord();
    int i=0;
    for (RecordType record : records) {
      System.out.println("Identifier "+(++i)+record.getHeader().getIdentifier());
      System.out.println("Metatadata "+(++i)+record.getMetadata().getOaidc().getSubject());
    }
    System.out.println("resumption token "+getRecordObj.getListRecords().getResumptionToken());

  }
  
}
