/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.restResources;

import in.gov.nvli.harvester.OAIPMH_beans.IdentifyType;
import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.services.IdentifyService;
import in.gov.nvli.harvester.servicesImpl.IdentifyServiceImpl;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

/**
 *
 * @author vootla
 */
@Path("/identifies")
public class IdentifyResource {
    
    public IdentifyService identifyService;
    

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public IdentifyType identifyXML(@QueryParam("baseURL") String baseURL,@QueryParam("adminEmail") String adminEmail) throws IOException, MalformedURLException, JAXBException
    {
       identifyService=new IdentifyServiceImpl(); 
       return identify(baseURL,adminEmail);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public IdentifyType identifyJSON(@QueryParam("baseURL") String baseURL,@QueryParam("adminEmail") String adminEmail) throws IOException, MalformedURLException, JAXBException
    {
       identifyService=new IdentifyServiceImpl();
       return identify(baseURL,adminEmail);
    }
   
    @GET
    @Path("{baseURL}")
    @Produces(MediaType.APPLICATION_JSON)
    public IdentifyType identifyPathParamJSON(@PathParam("baseURL") String baseURL,@QueryParam("adminEmail") String adminEmail) throws IOException, MalformedURLException, JAXBException
    {
        identifyService=new IdentifyServiceImpl();
        baseURL = URLDecoder.decode(baseURL, "UTF-8");
       return identify(baseURL,adminEmail);
    }
    
  @GET
    @Path("{baseURL}")
    @Produces(MediaType.APPLICATION_XML)
    public IdentifyType identifyPathParamXML(@PathParam("baseURL") String baseURL,@QueryParam("adminEmail") String adminEmail) throws IOException, MalformedURLException, JAXBException
    {
        identifyService=new IdentifyServiceImpl();
        baseURL = URLDecoder.decode(baseURL, "UTF-8");
       return identify(baseURL,adminEmail);
    }  
   private IdentifyType identify(String baseURL,String adminEmail) throws IOException, MalformedURLException, JAXBException
    {
       
        baseURL=baseURL+"?verb="+VerbType.IDENTIFY.value();
        IdentifyType identifyObj=identifyService.identify(baseURL,adminEmail);
       return identifyObj;
    }  
    
}