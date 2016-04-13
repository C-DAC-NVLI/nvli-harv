/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;


import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.beans.OAIDC;
import in.gov.nvli.harvester.services.GetRecordService;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import javax.xml.bind.JAXBException;
import org.springframework.stereotype.Service;

/**
 *
 * @author richa
 */
@Service
public class GetRecordServiceImpl implements GetRecordService {

  private HttpURLConnection connection;

  @Override
  public void getRecord(String baseUrl) throws MalformedURLException, IOException, JAXBException {
     connection = HttpURLConnectionUtil.getConnection(baseUrl, "GET", "", "");
    int responseCode = connection.getResponseCode();
    String response = OAIResponseUtil.createResponseFromXML(connection);

    OAIPMHtype getRecordObj = UnmarshalUtils.xmlToOaipmh(response);

    System.out.println("Identifier " + getRecordObj.getGetRecord().getRecord().getHeader().getIdentifier());
    
    OAIDC oaiDC=getRecordObj.getGetRecord().getRecord().getMetadata().getOaidc();
    System.out.println("Metadata " +oaiDC.getSubject());
    System.out.println("Metadata " +oaiDC.getIdentifier());

  }

}
