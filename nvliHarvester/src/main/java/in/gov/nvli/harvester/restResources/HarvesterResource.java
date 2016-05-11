/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.restResources;

import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.services.HarvesterService;
import in.gov.nvli.harvester.services.RepositoryService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 *
 * @author vootla
 */
@Path("/harvest")
@EnableAsync
@Component
public class HarvesterResource {
    
    @Autowired
    private HarvesterService harvesterService;
    
    @Autowired
    private RepositoryService repositoryService;
    
    @javax.ws.rs.core.Context 
    ServletContext context;
     
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String  startHarvestAll()
    {
        harvesterService.harvestAllRepositories(context);
         return "STARTED";
    }
   
    @GET
    @Path("/list/{repoUIDS}")
    @Produces(MediaType.APPLICATION_XML)
    public String  startHarvestList(@PathParam("repoUIDS") String repoUIDS)
    {
        List<String> repoUIDSList = new ArrayList<>(Arrays.asList(repoUIDS.split(",")));
        List<HarRepo> harRepos=repositoryService.getRepositoriesByUIDS(repoUIDSList);
        harvesterService.harvestRepositories(harRepos,context);
         return "STARTED";
    } 
    
    @GET
    @Path("/{repoUID}")
    @Produces(MediaType.TEXT_PLAIN)
    public String  startHarvesting(@PathParam("repoUID") String repoUID) throws InterruptedException, ExecutionException
    {
        //if(harvesterService.harvestRepositoryByUID(repoUID, context))
        Future<String> response = harvesterService.harvestRepositoryByUID(repoUID, context); 
       if(response.isDone())
       {
          return "COMPLETED"; 
       }else
       {
           return "STARTED";
       }
        

    }
    
    
    
    
    
    
}
