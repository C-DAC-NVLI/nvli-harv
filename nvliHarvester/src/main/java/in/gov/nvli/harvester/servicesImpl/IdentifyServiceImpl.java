/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.IdentifyType;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarRepoDetail;
import in.gov.nvli.harvester.services.IdentifyService;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.springframework.stereotype.Service;

/**
 *
 * @author vootla
 */
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
//        System.out.println("dd"+identifyObj.getGranularity().value());
//        SimpleDateFormat formatter = new SimpleDateFormat(identifyObj.getGranularity().value());
//	
//
//	try {
//
//		Date date = formatter.parse(identifyObj.getEarliestDatestamp());
//		 repo.setRepoEarliestTimestamp(date);
//                System.out.println(date);
//		System.out.println(formatter.format(date));
//
//	} catch (ParseException e) {
//		e.printStackTrace();
//	}
//        
   
        repo.setRepoGranularityDate(identifyObj.getGranularity().value());
        repo.setRepoDeletionMode(identifyObj.getDeletedRecord().value());

        System.out.println("identifyObj.getRepositoryName()"+identifyObj.getRepositoryName());
        return repo;
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
}
    

