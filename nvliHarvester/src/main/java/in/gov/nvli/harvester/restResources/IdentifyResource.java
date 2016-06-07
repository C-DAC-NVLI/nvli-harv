/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.restResources;

import in.gov.nvli.harvester.OAIPMH_beans.IdentifyType;
import in.gov.nvli.harvester.OAIPMH_beans.MetadataFormatType;
import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.custom.exception.OAIPMHerrorTypeException;
import in.gov.nvli.harvester.customised.IdentifyTypeCustomised;
import in.gov.nvli.harvester.custom.harvester_enum.MethodEnum;
import in.gov.nvli.harvester.services.IdentifyService;
import in.gov.nvli.harvester.services.ListMetadataFormatsService;
import in.gov.nvli.harvester.utilities.CustomBeansGenerator;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author vootla
 */
@Path("/identifies")
@Component
public class IdentifyResource {
    
    @Autowired
    public IdentifyService identifyService;
    
    @Autowired
    public ListMetadataFormatsService listMetadataFormatsService;
    
//    @GET
//    @Produces(MediaType.APPLICATION_XML)
//    public IdentifyTypeCustomised identifyXML(@QueryParam("baseURL") String baseURL,@QueryParam("adminEmail") String adminEmail) throws IOException, MalformedURLException, JAXBException
//    {
//       identifyService=new IdentifyServiceImpl(); 
//       return CustomBeansGenerator.convertIdentifyTypeToIdentifyTypeCustomised(identify(baseURL,adminEmail));
//    }
//    
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public IdentifyTypeCustomised identifyJSON(@QueryParam("baseURL") String baseURL,@QueryParam("adminEmail") String adminEmail) throws IOException, MalformedURLException, JAXBException
//    {
//      
//        identifyService=new IdentifyServiceImpl();
//       return CustomBeansGenerator.convertIdentifyTypeToIdentifyTypeCustomised(identify(baseURL,adminEmail));
//    }
//   
    @GET
    @Path("{baseURL}/{adminEmail}")
    @Produces(MediaType.APPLICATION_JSON)
    public IdentifyTypeCustomised identifyPathParamJSON(@PathParam("baseURL") String baseURL,@PathParam("adminEmail") String adminEmail) throws IOException, MalformedURLException, JAXBException, OAIPMHerrorTypeException
    {
        System.out.println("json");
        return identify(baseURL,adminEmail);
    }
    
    @GET
    @Path("{baseURL}/{adminEmail}")
    @Produces(MediaType.APPLICATION_XML)
    public IdentifyTypeCustomised identifyPathParamXML(@PathParam("baseURL") String baseURL,@PathParam("adminEmail") String adminEmail) throws IOException, MalformedURLException, JAXBException, OAIPMHerrorTypeException
    {
        System.out.println("xml"+adminEmail+baseURL);
        return identify(baseURL,adminEmail);
    } 
    
   
    @GET
    @Path("{baseURL}")
    @Produces(MediaType.APPLICATION_XML)
    public IdentifyTypeCustomised identifyPathParamXML1(@PathParam("baseURL") String baseURL,@QueryParam("adminEmail") String adminEmail) throws IOException, MalformedURLException, JAXBException, OAIPMHerrorTypeException
    {
        
        System.out.println("xml"+adminEmail+baseURL);
        return identify(baseURL,adminEmail);
    } 
    
    @GET
    @Path("{baseURL}")
    @Produces(MediaType.APPLICATION_JSON)
    public IdentifyTypeCustomised identifyPathParamJSON1(@PathParam("baseURL") String baseURL,@QueryParam("adminEmail") String adminEmail) throws IOException, MalformedURLException, JAXBException, OAIPMHerrorTypeException
    {
        System.out.println("json");
       return identify(baseURL,adminEmail);
    }
    
    
    
    
    
   private IdentifyTypeCustomised identify(String baseURL,String adminEmail) throws IOException, MalformedURLException, JAXBException, OAIPMHerrorTypeException
    {
         baseURL = URLDecoder.decode(baseURL, "UTF-8");
        // adminEmail = URLDecoder.decode(adminEmail, "UTF-8");
        IdentifyType identifyObj=identifyService.getIdentifyTypeObject(baseURL,MethodEnum.GET, adminEmail);
        HttpURLConnection connection = HttpURLConnectionUtil.getConnection(baseURL + CommonConstants.VERB + VerbType.LIST_METADATA_FORMATS.value(), MethodEnum.GET, adminEmail);
        List<MetadataFormatType> metaDataFormats = listMetadataFormatsService.getMetadataFormatTypeList(connection, baseURL);
        IdentifyTypeCustomised custObj = CustomBeansGenerator.convertIdentifyTypeToIdentifyTypeCustomised(identifyObj);
        if (metaDataFormats != null) {
            for (MetadataFormatType metadata : metaDataFormats) {
                if (metadata.getMetadataPrefix().trim().equals("ore")) {
                    custObj.setOreEnableFlag((byte) 1);
                }
            }
        }
        return custObj;
    }  
   
    
//    @PUT
//    @Path("update/resource/status/{repoUID}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public HarRepoCustomised identifyPathParamXML11(@PathParam("repoUID") String repoUID,HarRepoCustomised harRepoCustomised) throws IOException, MalformedURLException, JAXBException
//    {
//        System.out.println("repoUID"+repoUID);
//        return harRepoCustomised;
//    }  
}
