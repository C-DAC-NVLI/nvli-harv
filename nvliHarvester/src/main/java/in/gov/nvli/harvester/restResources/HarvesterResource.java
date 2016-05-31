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
@Path("/harvest")
@EnableAsync
@Component
public class HarvesterResource {
    
    @Autowired
    private HarvesterService harvesterService;
    
    @Autowired
    private RepositoryService repositoryService;
    
        
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public  Map<String,String>  startHarvestAll()
    {
        List<HarRepo> activeRepos = repositoryService.getActiveRepositories();
        Map<String,String> result=new HashMap<>();
        if(activeRepos==null)
        {
            result.put("message","no Active Repositories,Please Active & Try Again");
           
        }else
        {
           harvesterService.harvestAllActiveRepositories(activeRepos);
            for (HarRepo repo : activeRepos) {
               // result.put(repo.getRepoUID(), getHarvesterStatus(repo.getRepoUID()));
                result.put(repo.getRepoUID(),RepoStatusEnum.HARVEST_PROCESSING.getName());
            }
       
        }
       return result;

    }
   
    @GET
    @Path("/list/{repoUIDS}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,String>  startHarvestList(@PathParam("repoUIDS") String repoUIDS)
    {
       Map<String,String> result=new HashMap<>();
        List<String> repoUIDSList = new ArrayList<>(Arrays.asList(repoUIDS.split(",")));
        List<HarRepo> harRepos=repositoryService.getRepositoriesByUIDS(repoUIDSList);
       
         if(harRepos==null)
        {
            result.put("message", "no Repositories Exist on corresponding repoUIDS");
        }else
         {
             harvesterService.harvestRepositories(harRepos);
             for (String repoUID : repoUIDSList) {
                 // result.put(repoUID, getHarvesterStatus(repoUID));
                 result.put(repoUID, RepoStatusEnum.HARVEST_PROCESSING.getName());
             }
         }
        
         return result;
    } 
    
    @GET
    @Path("/{repoUID}")
    @Produces(MediaType.TEXT_PLAIN)
    public String  startHarvesting(@PathParam("repoUID") String repoUID) throws InterruptedException, ExecutionException
    {
        //if(harvesterService.harvestRepositoryByUID(repoUID, context))
        Future<String> response = harvesterService.harvestRepositoryByUID(repoUID); 
       // return getHarvesterStatus(repoUID);
        return RepoStatusEnum.HARVEST_PROCESSING.getName();
    }
    
    @GET
    @Path("/status/{repoUID}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getHarvesterStatus(@PathParam("repoUID") String repoUID) {
        return repositoryService.getRepositoryByUID(repoUID).getRepoStatusId().getRepoStatusName();
    }

    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> getAllRepoStatus() {
        List<HarRepo> harRepos = repositoryService.getAllRepositories();
        return reposStatusInternal(harRepos);
    }

    @GET
    @Path("/status/list/{repoUIDS}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> getListOfRepoStatus(@PathParam("repoUIDS") String repoUIDS) {

        List<String> repoUIDSList = new ArrayList<>(Arrays.asList(repoUIDS.split(",")));
        List<HarRepo> harRepos = repositoryService.getRepositoriesByUIDS(repoUIDSList);
        return reposStatusInternal(harRepos);
    }

    private Map<String, String> reposStatusInternal(List<HarRepo> harRepos) {
        Map<String, String> result = new HashMap<String, String>();
        if (harRepos == null) {
            result.put("message", "no Repositories Exist");
        } else {
            for (HarRepo repo : harRepos) {
                result.put(repo.getRepoUID(), repo.getRepoStatusId().getRepoStatusName());
            }
        }

        return result;
    }


}
