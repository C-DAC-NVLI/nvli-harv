/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.restResources;

import in.gov.nvli.harvester.beans.HarRecord;
import in.gov.nvli.harvester.beans.HarRecordMetadataDc;
import in.gov.nvli.harvester.crowdsource_beans.MetadataStandardResponse;
import in.gov.nvli.harvester.crowdsource_beans.dublincore.DublinCoreMetadata;
import in.gov.nvli.harvester.services.GetRecordService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author vootla
 */
@Component
@Path("/crowdsourcing")
public class CrowdsourcingResource {
   
    @Autowired
    private GetRecordService recordservice;
    
    
    @GET
    @Path("/dcc/{recordUID}")
    @Produces(MediaType.APPLICATION_JSON)
    public  MetadataStandardResponse getDCCMetadata(@PathParam("recordUID") Long recordUID)
    {
        HarRecord record = recordservice.getRecord(recordUID);
          DublinCoreMetadata obj=null;
        if(record!=null)
        {
            HarRecordMetadataDc metaData = recordservice.GetMetaDataByHarRecord(record);
            if(metaData!=null)
            {
                 obj=new DublinCoreMetadata();
                
                 if (metaData.getDcContributor() != null) {
                    obj.setContributor(Arrays.asList(metaData.getDcContributor().split("\\s*<>\\s*")));
                }

                if (metaData.getDcCoverage() != null) {
                    obj.setCoverage(Arrays.asList(metaData.getDcCoverage().split("\\s*<>\\s*")));
                }

                if (metaData.getDcCreator() != null) {
                    obj.setCreator(Arrays.asList(metaData.getDcCreator().split("\\s*<>\\s*")));
                }

                if (metaData.getDcDate() != null) {
                    obj.setDate(Arrays.asList(metaData.getDcDate().split("\\s*<>\\s*")));
                }

                if (metaData.getDcDescription() != null) {
                    obj.setDescription(Arrays.asList(metaData.getDcDescription().split("\\s*<>\\s*")));
                }

                if (metaData.getDcFormat() != null) {
                    obj.setFormat(Arrays.asList(metaData.getDcFormat().split("\\s*<>\\s*")));
                }

                if (metaData.getDcIdentifier() != null) {
                    obj.setIdentifier(Arrays.asList(metaData.getDcIdentifier().split("\\s*<>\\s*")));
                }

                if (metaData.getDcLanguage() != null) {
                    obj.setLanguage(Arrays.asList(metaData.getDcLanguage().split("\\s*<>\\s*")));
                }
                if (metaData.getDcPublisher() != null) {
                    obj.setPublisher(Arrays.asList(metaData.getDcPublisher().split("\\s*<>\\s*")));
                }
                if (metaData.getDcRights() != null) {
                    obj.setRights(Arrays.asList(metaData.getDcRights().split("\\s*<>\\s*")));
                }
                if (metaData.getDcSource() != null) {
                    obj.setSource(Arrays.asList(metaData.getDcSource().split("\\s*<>\\s*")));
                }
                if (metaData.getDcSubject() != null) {
                    obj.setSubject(Arrays.asList(metaData.getDcSubject().split("\\s*<>\\s*")));
                }
                if (metaData.getDcTitle() != null) {
                    obj.setTitle(Arrays.asList(metaData.getDcTitle().split("\\s*<>\\s*")));
                }
                if (metaData.getDcType() != null) {
                    obj.setType(Arrays.asList(metaData.getDcType().split("\\s*<>\\s*")));
                }
               
               
            }

            
        }

        MetadataStandardResponse respone=new MetadataStandardResponse();
      
        respone.setIsCloseForEdit("Yes");
        respone.setMetadata(obj);
        respone.setMetadataStandard("dublin-core");
        respone.setRecordIdentifier(recordUID.toString());
    return respone;
      //  return obj;
    }
    
    
    
}
