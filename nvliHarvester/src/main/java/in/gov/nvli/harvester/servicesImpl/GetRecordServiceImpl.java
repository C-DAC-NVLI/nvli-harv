/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;


import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.services.GetRecordService;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import javax.xml.bind.JAXBException;

/**
 *
 * @author richa
 */
public class GetRecordServiceImpl implements GetRecordService {

  private HttpURLConnectionUtil httpURLConnectionUtil;
  private OAIResponseUtil OAIResponseUtil;

  @Override
  public void getRecord(String baseUrl) throws MalformedURLException, IOException, JAXBException {
    httpURLConnectionUtil = new HttpURLConnectionUtil();
    HttpURLConnection con = httpURLConnectionUtil.getConnection(baseUrl, "GET", "", "");
    int responseCode = con.getResponseCode();
    OAIResponseUtil = new OAIResponseUtil();
    String response = OAIResponseUtil.createResponseFromXML(con);

    OAIPMHtype getRecordObj = UnmarshalUtils.xmlToOaipmh(response);

    System.out.println("Identifier " + getRecordObj.getGetRecord().getRecord().getHeader().getIdentifier());
    System.out.println("Metadata " + getRecordObj.getGetRecord().getRecord().getMetadata().getOaidc().getSubject());

  }

}
