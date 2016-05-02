/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.HeaderType;
import in.gov.nvli.harvester.OAIPMH_beans.ListIdentifiersType;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.ResumptionTokenType;
import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.customised.MethodEnum;
import in.gov.nvli.harvester.services.GetRecordService;
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
static int i = 0;
  @Override
  public void getListIdentifiers(String baseUrl) throws MalformedURLException, IOException, JAXBException {
    connection = HttpURLConnectionUtil.getConnection(baseUrl, MethodEnum.GET, "");
    int responseCode = connection.getResponseCode();
    if (responseCode != 200) {
      try{
      Thread.sleep(1000);
      getListIdentifiers(baseUrl);
      }catch(Exception e){
        e.printStackTrace();
      }
    } else {
      String[] repoUrl = baseUrl.split("&");
      String identifier = "";
      String metadataPrefix = "oai_dc";
      String response = OAIResponseUtil.createResponseFromXML(connection);

      OAIPMHtype getRecordObj = UnmarshalUtils.xmlToOaipmh(response);

      ListIdentifiersType listIdentifiers = getRecordObj.getListIdentifiers();

      List<HeaderType> headers = listIdentifiers.getHeader();
      
      for (HeaderType header : headers) {
        identifier = header.getIdentifier();
        System.out.println((++i) + " Identifier " + identifier);
      }

      ResumptionTokenType resumptionToken = listIdentifiers.getResumptionToken();
      if (resumptionToken != null) {
        getListIdentifiers(repoUrl[0] + "&resumptionToken=" + resumptionToken.getValue());
      }
    }

  }

  @Override
  public void getRecordFromIdentifiers(String baseUrl, String identifier, String metadataPrefix) {
    String repoUrl[] = baseUrl.split("=");
    String requestUrl = repoUrl[0] + VerbType.GET_RECORD + "&identifier=" + identifier + "&metadataPrefix=" + metadataPrefix;
    System.out.println("identifier " + identifier);
  }

  public static void main(String[] args) throws Exception {
    ListIdentifiersService lis = new ListIdentifiersServiceImpl();
    lis.getListIdentifiers("http://export.arxiv.org/oai2?verb=ListIdentifiers&metadataPrefix=oai_dc");
  }

}
