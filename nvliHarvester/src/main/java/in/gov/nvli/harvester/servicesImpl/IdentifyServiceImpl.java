/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.services.IdentifyService;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import javax.xml.bind.JAXBException;

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
    public void getRepositoryInformation(String baseURL) throws ProtocolException, MalformedURLException, IOException,JAXBException
    {
        httpURLConnectionUtil=new HttpURLConnectionUtil();
        HttpURLConnection con = httpURLConnectionUtil.getConnection(baseURL, "GET", "", "");
        int responseCode = con.getResponseCode();
		if (responseCode != 200)
			         System.err.println("ERROR");
        OAIResponseUtil=new OAIResponseUtil();
        String response=OAIResponseUtil.createResponseFromXML(con);
        OAIPMHtype OAIPMHType= (OAIPMHtype)UnmarshalUtils.xmlToOaipmh(response);
        
        System.out.println(OAIPMHType.getResponseDate());
    //unMarshaling(con);
    }
  
    
    
    

}
    

