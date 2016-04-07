/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.beans.OAIPMHtype;
import in.gov.nvli.harvester.dao.IdentifyDao;
import in.gov.nvli.harvester.services.IdentifyService;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author vootla
 */
public class IdentifyServiceImpl implements IdentifyService{

   // @Autowired
  //  IdentifyDao identifyDao; 
    private HttpURLConnectionUtil httpURLConnectionUtil;
    private OAIResponseUtil OAIResponseUtil;
    @Override
    public void getRepositoryInformation(String baseURL) throws ProtocolException, MalformedURLException, IOException
    {
        httpURLConnectionUtil=new HttpURLConnectionUtil();
        HttpURLConnection con = httpURLConnectionUtil.getConnection(baseURL, "GET", "", "");
        int responseCode = con.getResponseCode();
//		if (responseCode != 200)
//			return null;
        OAIResponseUtil=new OAIResponseUtil();
        System.out.println(OAIResponseUtil.createResponseFromXML(con));
    unMarshaling(con);
    }
  
    public void unMarshaling(HttpURLConnection con) throws IOException
    {
         try {  
     
        JAXBContext jaxbContext = JAXBContext.newInstance(OAIPMHtype.class);  
   
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
        OAIPMHtype que= (OAIPMHtype) jaxbUnmarshaller.unmarshal(con.getInputStream());  
          
        System.out.println(que.getResponseDate());  
        
   
      } catch (JAXBException e) {  
        e.printStackTrace();  
      }  
   
    }  
    }
    

