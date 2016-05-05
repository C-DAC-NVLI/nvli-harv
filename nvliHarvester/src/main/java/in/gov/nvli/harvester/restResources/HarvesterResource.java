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
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author vootla
 */
@Path("/harvest")
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
         return "started";
    }
   
    @GET
    @Path("/list/{repoUIDS}")
    @Produces(MediaType.APPLICATION_XML)
    public String  startHarvestList(@PathParam("repoUIDS") String repoUIDS)
    {
        List<String> myList = new ArrayList<>(Arrays.asList(repoUIDS.split(",")));
        List<Integer> repoUIDSList=new ArrayList<>();
        for(String temp:myList)
        {
            repoUIDSList.add(Integer.parseInt(temp));
        }
        List<HarRepo> harRepos=repositoryService.getRepositoriesByUIDS(repoUIDSList);
       
        harvesterService.harvestRepositories(harRepos,context);
         return "STARTED";
    } 
    
    @GET
    @Path("/{repoUID}")
    @Produces(MediaType.APPLICATION_XML)
    public String  startHarvesting(@PathParam("repoUID") int repoUID)
    {
         if(harvesterService.harvestRepository(repoUID, context))
         return "STARTED";
         else
             return "ERROR";
    }
    
    
    
    
    
    
}
