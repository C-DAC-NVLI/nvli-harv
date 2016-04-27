/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.restResources;

import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.customised.HarRepoCustomised;
import in.gov.nvli.harvester.services.HarvesterService;
import in.gov.nvli.harvester.services.RepositoryService;
import in.gov.nvli.harvester.utilities.CustomBeansGenerator;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author vootla
 */
@Path("/repositories")
@Component
public class RepositoryResource 
{
    @Autowired
    private RepositoryService repositoryService;
    
    @Autowired
    private HarvesterService harvesterService;
    
    @javax.ws.rs.core.Context 
    ServletContext context;
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<HarRepoCustomised> repositoriesXML()
    {
       return repositories(); 
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<HarRepoCustomised> repositoriesJSON()
    {
        return repositories();
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    public HarRepo saveRepositoryXML(HarRepoCustomised harRepoCustomised)
    {
       return saveRepository(harRepoCustomised);
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public HarRepo saveRepositoryJSON(HarRepoCustomised harRepoCustomised)
    {
         return saveRepository(harRepoCustomised);
    }
    
    @GET
    @Path("/start/{repoID}")
    @Produces(MediaType.APPLICATION_XML)
    public String  startHarvesting(@PathParam("repoID") int repoID,HttpServletRequest request)
    {
         //harvesterService.harvestReposiotires(repoID, request.getServletContext());
         return "started";
    }
    @GET
    @Path("/start")
    @Produces(MediaType.APPLICATION_XML)
    public String  startHarvesting(@QueryParam("baseURL") String baseURL ,HttpServletRequest request)
    {
        harvesterService.harvestReposiotires(baseURL,context);
         return "started";
    }
    
    
    private HarRepo saveRepository(HarRepoCustomised harRepoCustomised)
    {
        HarRepo repo=CustomBeansGenerator.convertHarRepoCustomisedToHarRepo(harRepoCustomised);
         repo = repositoryService.addRepository(repo);
         return repo;
    }
    
    
    private List<HarRepoCustomised> repositories()
    {
        List<HarRepoCustomised> harRepoCustomisedList=new ArrayList<>();
        List<HarRepo> harRepos=repositoryService.getAllRepositories();
        for(HarRepo repo:harRepos)
        {
            harRepoCustomisedList.add(CustomBeansGenerator.convertHarRepoToHarRepoCustomised(repo));
        }
       return harRepoCustomisedList;
    }
}
