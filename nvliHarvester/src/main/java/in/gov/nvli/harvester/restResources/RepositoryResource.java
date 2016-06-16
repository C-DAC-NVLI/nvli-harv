/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.restResources;

import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.customised.HarRepoCustomised;
import in.gov.nvli.harvester.custom.harvester_enum.RepoStatusEnum;
import in.gov.nvli.harvester.services.RepositoryService;
import in.gov.nvli.harvester.utilities.CustomBeansGenerator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public HarRepoCustomised saveRepositoryXML(HarRepoCustomised harRepoCustomised)
    {
        System.out.println("in xl"+harRepoCustomised.getRepoName());
       HarRepo repo = saveRepository(harRepoCustomised);
       if(repo!=null)
       {
           return CustomBeansGenerator.convertHarRepoToHarRepoCustomised(repo);
       }
        
        return null;
    }
  
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public HarRepoCustomised saveRepositoryJSON(HarRepoCustomised harRepoCustomised)
    {
        System.out.println("in json"+harRepoCustomised.getRepoName());
        HarRepo repo=saveRepository(harRepoCustomised);
        if(repo!=null)
       {
           return CustomBeansGenerator.convertHarRepoToHarRepoCustomised(repo);
       }
        
        return null;
    }
    
    
    @GET
    @Path("/activate")
    @Produces(MediaType.TEXT_PLAIN)
    public String repositoriesActivate()
    {
       
       return changeRepoStatus(RepoStatusEnum.ACTIVE.getId());
    }
    
    @GET
    @Path("/deactivate")
    @Produces(MediaType.TEXT_PLAIN)
    public String repositoriesDeActivate()
    {
      return changeRepoStatus(RepoStatusEnum.NOT_ACTIVE.getId());
    }
    
    @GET
    @Path("/activate/{repoUID}")
    @Produces(MediaType.TEXT_PLAIN)
    public String  repositoriesActivateParticular(@PathParam("repoUID") String repoUID)
    {
          return changeRepoStatus(repoUID,RepoStatusEnum.ACTIVE.getId());
    }
    
    
    @GET
    @Path("/deactivate/{repoUID}")
    @Produces(MediaType.TEXT_PLAIN)
    public String  repositoriesDeActivateParticular(@PathParam("repoUID") String repoUID)
    {
         return changeRepoStatus(repoUID,RepoStatusEnum.NOT_ACTIVE.getId());
    }
    
    
    
    
    @GET
    @Path("/activate/list/{repoUIDS}")
    @Produces(MediaType.TEXT_PLAIN)
    public String   repositoriesActivateList(@PathParam("repoUIDS") String repoUIDS)
    {
        List<String> repoUIDSList=splitRepoUIDS(repoUIDS);
       return  changeRepoStatus(repoUIDSList,RepoStatusEnum.ACTIVE.getId());
    } 
    
    @GET
    @Path("/deactivate/list/{repoUIDS}")
    @Produces(MediaType.TEXT_PLAIN)
    public String   repositoriesDeActivateList(@PathParam("repoUIDS") String repoUIDS)
    { 
        List<String> repoUIDSList=splitRepoUIDS(repoUIDS);
      return  changeRepoStatus(repoUIDSList,RepoStatusEnum.NOT_ACTIVE.getId());
    } 
    
    
    
    @PUT
    @Path("/{repoUID}")
    @Produces({ MediaType.APPLICATION_JSON})
    public Response updateRepository(@PathParam("repoUID") String repoUID, HarRepoCustomised custObj) {
        HarRepo repoOrginal = repositoryService.getRepositoryByUID(repoUID);
       
        if (repoOrginal == null) {
            System.out.println("in null");
            return Response.status(400).entity("Repository with repoUID" + repoUID + "not Exist !!").build();
        }
        if (custObj == null) {
            System.out.println("in null");
            return Response.status(400).entity("Please provide the  HarRepoCustomised object !!").build();
        }
        HarRepo repo = CustomBeansGenerator.convertHarRepoCustomisedToHarRepo(custObj);
        
        repo.setRepoUID(repoOrginal.getRepoUID());
        repo.setRepoBaseUrl(repoOrginal.getRepoBaseUrl());
        repo.setRepoId(repoOrginal.getRepoId());
       
        repositoryService.editRepository(repo);
        custObj = CustomBeansGenerator.convertHarRepoToHarRepoCustomised(repo);
        return Response.ok().entity(custObj).build();
    }

    @GET
    @Path("/{repoUID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRepository(@PathParam("repoUID") String repoUID) {
        HarRepo repo = repositoryService.getRepositoryByUID(repoUID);
        if (repo == null) {
            System.out.println("in null");
            return Response.status(400).entity("Repository with repoUID" + repoUID + "not Exist !!").build();
        }
        HarRepoCustomised custObj = CustomBeansGenerator.convertHarRepoToHarRepoCustomised(repo);
        
        return Response.ok().entity(custObj).build();
    }


    
    
   private String changeRepoStatus(short status)
   {
       return RepoStatusMsg(repositoryService.changeRepoStatus(status),status);
   }
    
   private List<String> splitRepoUIDS(String repoUIDS)
   {
       List<String> repoUIDSList = new ArrayList<>(Arrays.asList(repoUIDS.split(CommonConstants.PATH_PARAM_SEPERATOR)));
        return  repoUIDSList;
   }
    
    
    
    
    private String changeRepoStatus(String repositoryUID, short status) {
          return  RepoStatusMsg(repositoryService.changeRepoStatus(repositoryUID, status),status);
    }
    
    private String changeRepoStatus( List<String> repoUIDSList, short status) {
         return  RepoStatusMsg(repositoryService.changeRepoStatus(repoUIDSList, status),status);
    }
    

    private String RepoStatusMsg(boolean result,int status)
    {
        if(result)
          {
              if (status == RepoStatusEnum.ACTIVE.getId()) {
                  return RepoStatusEnum.ACTIVE.getName();
                           
              }
              if (status == RepoStatusEnum.NOT_ACTIVE.getId())
                  return RepoStatusEnum.NOT_ACTIVE.getName();
              return "ERROR";
                        
          }else
          {
             return "ERROR";  
          }
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
