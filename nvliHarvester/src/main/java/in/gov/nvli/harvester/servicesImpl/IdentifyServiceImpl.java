/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.IdentifyType;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.customised.MethodEnum;
import in.gov.nvli.harvester.services.IdentifyService;
import in.gov.nvli.harvester.utilities.DatesRelatedUtil;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.Date;
import javax.xml.bind.JAXBException;
import org.springframework.stereotype.Service;

/**
 *
 * @author vootla
 */
@Service
public class IdentifyServiceImpl implements IdentifyService{

  
    private HttpURLConnection connection;
   @Override
    public HarRepo getRepositoryInformation() throws IOException,JAXBException
    {
        String response=OAIResponseUtil.createResponseFromXML(connection);
        OAIPMHtype obj= (OAIPMHtype)UnmarshalUtils.xmlToOaipmh(response); 
        IdentifyType identifyObj = obj.getIdentify();
       
//        HarRepoDetail repoDetails=new HarRepoDetail();
//        repoDetails.setRepoDetailCompression(response);
//        repoDetails.setRepoDetailDesc(response);
//        repoDetails.setRepoDetailEmail(response);
        
        HarRepo repo=new HarRepo();
        repo.setRepoName(identifyObj.getRepositoryName());
        repo.setRepoBaseUrl(identifyObj.getBaseURL());
        repo.setRepoProtocolVersion(identifyObj.getProtocolVersion());
        Date d=DatesRelatedUtil.convertDateToGranularityFormat(identifyObj.getGranularity().value(), identifyObj.getEarliestDatestamp());
        repo.setRepoEarliestTimestamp(d);  
        repo.setRepoGranularityDate(identifyObj.getGranularity().value());
        repo.setRepoDeletionMode(identifyObj.getDeletedRecord().value());
      StringBuilder compressions=null;
      boolean tempflag=true;
       for(String temp:identifyObj.getCompression())
       {
           if(tempflag)
           {
               compressions=new StringBuilder(temp);
               tempflag=false;
           }else
           {
              compressions.append(CommonConstants.COLUMNVALUESEPARARTOR+temp);
           }
       }
        repo.setRepoCompression(compressions.toString());
        
        return repo;
    }
   @Override
   public int getConnectionStatus(String  baseURL,String method,String userAgnet,String adminEmail) throws MalformedURLException, IOException
   {
       connection = HttpURLConnectionUtil.getConnection(baseURL, MethodEnum.GET, "");
       if(HttpURLConnectionUtil.isConnectionAlive(connection)){
           return 1;
       }else{
           connection.disconnect();
           return -1;
       }
       
   }

    @Override
    public HarRepo getRepositoryInformation(String baseURL) throws MalformedURLException,IOException, JAXBException 
    {
         connection = HttpURLConnectionUtil.getConnection(baseURL, MethodEnum.GET, "");
        if(connection.getResponseCode()!=200)
       {
           connection.disconnect();
           return null;
       }else
          {
              return getRepositoryInformation();
          }
    }

    @Override
    public IdentifyType identify(String baseURL, String adminEmail) throws MalformedURLException, IOException, JAXBException {
        connection = HttpURLConnectionUtil.getConnection(baseURL, MethodEnum.GET, "");
       if(HttpURLConnectionUtil.isConnectionAlive(connection)){
           String response=OAIResponseUtil.createResponseFromXML(connection);
        OAIPMHtype obj= (OAIPMHtype)UnmarshalUtils.xmlToOaipmh(response); 
        IdentifyType identifyObj = obj.getIdentify();
        return  identifyObj;
       }else{
           connection.disconnect();
           return null;
       }
        
       
       
    }
}
    

