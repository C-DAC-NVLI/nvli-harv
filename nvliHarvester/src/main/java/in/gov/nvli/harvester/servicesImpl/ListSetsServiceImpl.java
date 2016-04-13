/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.ListSetsType;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.SetType;
import in.gov.nvli.harvester.services.ListSetsService;
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
 * @author vootla
 */
public class ListSetsServiceImpl implements ListSetsService{
      
       private HttpURLConnection connection;
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

    @Override
    public List<SetType> getListSets() throws IOException, JAXBException 
    {
       String response=OAIResponseUtil.createResponseFromXML(connection);
        OAIPMHtype obj= (OAIPMHtype)UnmarshalUtils.xmlToOaipmh(response); 
        System.err.println(".set."+obj.getListSets().getSet().get(0).getSetName());
        return obj.getListSets().getSet();
    }
}
