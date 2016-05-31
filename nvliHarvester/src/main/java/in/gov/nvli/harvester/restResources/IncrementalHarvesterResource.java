/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.restResources;

import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.customised.RepoStatusEnum;
import in.gov.nvli.harvester.services.HarvesterService;
import in.gov.nvli.harvester.services.RepositoryService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@Path("/incremental_harvest")
@EnableAsync
@Component
public class IncrementalHarvesterResource {
    
    @Autowired
    private HarvesterService harvesterService;
    
    @Autowired
    private RepositoryService repositoryService;
    
        
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public  Map<String,String>  startIncrHarvestAll()
    {
        List<HarRepo> activeRepos = repositoryService.getRepositoriesByStaus(RepoStatusEnum.HARVEST_COMPLETE.getId());
        Map<String,String> result=new HashMap<>();
        if(activeRepos==null)
        {
            result.put("message","no Active Repositories,Please Active & Try Again");
           
        }else
        {
           harvesterService.harvestRepositoriesIncremental(activeRepos);
            for (HarRepo repo : activeRepos) {
               // result.put(repo.getRepoUID(), getHarvesterStatus(repo.getRepoUID()));
                result.put(repo.getRepoUID(),RepoStatusEnum.INCREMENT_HARVEST_PROCESSING.getName());
            }
       
        }
       return result;

    }
   
    @GET
    @Path("/list/{repoUIDS}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,String>  startIncrHarvestList(@PathParam("repoUIDS") String repoUIDS)
    {
       Map<String,String> result=new HashMap<>();
        List<String> repoUIDSList = new ArrayList<>(Arrays.asList(repoUIDS.split(",")));
        List<HarRepo> harRepos=repositoryService.getRepositoriesByUIDS(repoUIDSList);
       
         if(harRepos==null)
        {
            result.put("message", "no Repositories Exist on corresponding repoUIDS");
        }else
         {
             harvesterService.harvestRepositoriesIncremental(harRepos);
             for (String repoUID : repoUIDSList) {
                 // result.put(repoUID, getHarvesterStatus(repoUID));
                 result.put(repoUID, RepoStatusEnum.INCREMENT_HARVEST_PROCESSING.getName());
             }
         }
        
         return result;
    } 
    
    @GET
    @Path("/{repoUID}")
    @Produces(MediaType.TEXT_PLAIN)
    public String  startIncrHarvesting(@PathParam("repoUID") String repoUID) throws InterruptedException, ExecutionException
    {
        //if(harvesterService.harvestRepositoryByUID(repoUID, context))
        harvesterService.harvestRepositoryIncrementalBYUID(repoUID);
       // return getHarvesterStatus(repoUID);
        return RepoStatusEnum.INCREMENT_HARVEST_PROCESSING.getName();
    }
    
    


}
