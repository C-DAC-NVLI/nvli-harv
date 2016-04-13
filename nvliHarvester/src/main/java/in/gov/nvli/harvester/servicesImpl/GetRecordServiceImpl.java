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
import java.util.List;
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
       
    

  }
  
  public void getMetadataFromObj(OAIDC oaiDC){
    List<String> titles=oaiDC.getTitle();
    List<String> creators=oaiDC.getCreator();
    List<String> subjects=oaiDC.getSubject();
    List<String> descriptions=oaiDC.getDescription();
    List<String> dates=oaiDC.getDate();
    List<String> types=oaiDC.getType();
    List<String> identifiers=oaiDC.getIdentifier();
    List<String> contributors=oaiDC.getContributor();
    List<String> coverages=oaiDC.getCoverage();
    List<String> languages=oaiDC.getLanguage();
    List<String> publishers=oaiDC.getPublisher();
    List<String> relations=oaiDC.getRelation();
    List<String> rights=oaiDC.getRights();
    List<String> sources=oaiDC.getSource();
    List<String> formats=oaiDC.getFormat();
  }

}
