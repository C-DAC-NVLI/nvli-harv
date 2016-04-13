/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.HeaderType;
import in.gov.nvli.harvester.OAIPMH_beans.ListIdentifiersType;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.services.ListIdentifiersService;
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
public class ListIdentifiersServiceImpl implements ListIdentifiersService {

  private HttpURLConnection connection;

  @Override
  public void getListIdentifiers(String baseUrl) throws MalformedURLException, IOException, JAXBException {
    connection = HttpURLConnectionUtil.getConnection(baseUrl, "GET", "", "");
    int responseCode = connection.getResponseCode();
    String response = OAIResponseUtil.createResponseFromXML(connection);

    OAIPMHtype getRecordObj = UnmarshalUtils.xmlToOaipmh(response);

    ListIdentifiersType listIdentifiers = getRecordObj.getListIdentifiers();

    List<HeaderType> headers = listIdentifiers.getHeader();
    
    for (HeaderType header : headers) {
      System.out.println("Haeder "+header.getIdentifier());      
    }
  }

}
