/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.dao.IdentifyDao;
import in.gov.nvli.harvester.services.IdentifyService;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import javax.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author vootla
 */

public class IdentifyServiceImpl implements IdentifyService{

    @Autowired
  public IdentifyDao identifyDao; 
    private HttpURLConnection connection;
   @Override
    public OAIPMHtype getRepositoryInformation() throws IOException,JAXBException
    {
        String response=OAIResponseUtil.createResponseFromXML(connection);
        OAIPMHtype obj= (OAIPMHtype)UnmarshalUtils.xmlToOaipmh(response); 
        return obj;
    }
   @Override
   public int getConnectionStatus(String  baseURL,String method,String userAgnet,String adminEmail) throws MalformedURLException, IOException
   {
       connection = HttpURLConnectionUtil.getConnection(baseURL, "GET", "", "");
       int status= HttpURLConnectionUtil.getConnectionStatus(connection);
       if(status==-1)
       {
           connection.disconnect();
       }
       return status;
   }
    
public static void  main(String args[]) throws IOException, JAXBException
{
    // String response=OAIResponseUtil.createResponseFromXML(new File("D:/request.xml"));  
     String response= OAIResponseUtil.createResponseFromXML(HttpURLConnectionUtil.getConnection("http://dspace.library.iitb.ac.in/oai/request?verb=Identify", "GET", "", ""));
     OAIPMHtype OAIPMHType= (OAIPMHtype)UnmarshalUtils.xmlToOaipmh(response); 
        System.out.println(OAIPMHType.getIdentify().getRepositoryName());
}
}
    

