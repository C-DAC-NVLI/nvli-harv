/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.restResources;

import in.gov.nvli.harvester.beans.HarMetadataTypeRepository;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarRepoMetadata;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.custom.harvester_enum.MethodEnum;
import in.gov.nvli.harvester.customised.HarRepoCustomised;
import in.gov.nvli.harvester.custom.harvester_enum.RepoStatusEnum;
import in.gov.nvli.harvester.services.ListMetadataFormatsService;
import in.gov.nvli.harvester.services.RepositoryMetadataService;
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
public class RepositoryResource {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RepositoryMetadataService repositoryMetadataServiceObj;

    @Autowired
    private ListMetadataFormatsService listMetadataFormatsServiceObj;

    @javax.ws.rs.core.Context
    ServletContext context;

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<HarRepoCustomised> repositoriesXML() {
        return repositories();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<HarRepoCustomised> repositoriesJSON() {
        return repositories();
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    public HarRepoCustomised saveRepositoryXML(HarRepoCustomised harRepoCustomised) {
        return saveRepository(harRepoCustomised);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public HarRepoCustomised saveRepositoryJSON(HarRepoCustomised harRepoCustomised) {
        return saveRepository(harRepoCustomised);
    }

//    @GET
//    @Path("/activate")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String repositoriesActivate()
//    {
//
//       return changeRepoStatus(RepoStatusEnum.ACTIVE.getId());
//    }
//
//    @GET
//    @Path("/deactivate")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String repositoriesDeActivate()
//    {
//      return changeRepoStatus(RepoStatusEnum.NOT_ACTIVE.getId());
//    }
    @GET
    @Path("/activate/{repoUID}")
    @Produces(MediaType.TEXT_PLAIN)
    public String repositoriesActivateParticular(@PathParam("repoUID") String repoUID) {
        return changeRepoStatus(repoUID, RepoStatusEnum.ACTIVE.getId());
    }

    @GET
    @Path("/deactivate/{repoUID}")
    @Produces(MediaType.TEXT_PLAIN)
    public String repositoriesDeActivateParticular(@PathParam("repoUID") String repoUID) {
        return changeRepoStatus(repoUID, RepoStatusEnum.NOT_ACTIVE.getId());
    }

//    @GET
//    @Path("/activate/list/{repoUIDS}")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String   repositoriesActivateList(@PathParam("repoUIDS") String repoUIDS)
//    {
//        List<String> repoUIDSList=splitRepoUIDS(repoUIDS);
//       return  changeRepoStatus(repoUIDSList,RepoStatusEnum.ACTIVE.getId());
//    }
//
//    @GET
//    @Path("/deactivate/list/{repoUIDS}")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String   repositoriesDeActivateList(@PathParam("repoUIDS") String repoUIDS)
//    {
//        List<String> repoUIDSList=splitRepoUIDS(repoUIDS);
//      return  changeRepoStatus(repoUIDSList,RepoStatusEnum.NOT_ACTIVE.getId());
//    }
    @PUT
    @Path("/{repoUID}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateRepositoryJSON(@PathParam("repoUID") String repoUID, HarRepoCustomised custObj) {
        return updateRepository(repoUID, custObj);
    }

    @PUT
    @Path("/{repoUID}")
    @Produces({MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_XML})
    public Response updateRepositoryXML(@PathParam("repoUID") String repoUID, HarRepoCustomised custObj) {
        return updateRepository(repoUID, custObj);
    }

    @GET
    @Path("/{repoUID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRepositoryJSON(@PathParam("repoUID") String repoUID) {
        return getRepository(repoUID);
    }

    @GET
    @Path("/{repoUID}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getRepositoryXML(@PathParam("repoUID") String repoUID) {
        return getRepository(repoUID);
    }

    private String changeRepoStatus(short status) {
        return RepoStatusMsg(repositoryService.changeRepoStatus(status), status);
    }

    private List<String> splitRepoUIDS(String repoUIDS) {
        List<String> repoUIDSList = new ArrayList<>(Arrays.asList(repoUIDS.split(CommonConstants.PATH_PARAM_SEPERATOR)));
        return repoUIDSList;
    }

    private String changeRepoStatus(String repositoryUID, short status) {
        HarRepo harRepoObj = repositoryService.getRepositoryByUID(repositoryUID);
        if (status == RepoStatusEnum.ACTIVE.getId()) {
            if (harRepoObj.getRepoStatusId().getRepoStatusId() == RepoStatusEnum.NOT_ACTIVE.getId()
                    || harRepoObj.getRepoStatusId().getRepoStatusId() == RepoStatusEnum.INVALID_URL.getId()) {
                return RepoStatusMsg(repositoryService.changeRepoStatus(repositoryUID, status), status);
            } else if (harRepoObj.getRepoStatusId().getRepoStatusId() == RepoStatusEnum.ACTIVE.getId()) {
                return RepoStatusEnum.ACTIVE.getName();
            }
        } else if (status == RepoStatusEnum.NOT_ACTIVE.getId()) {
            if (harRepoObj.getRepoStatusId().getRepoStatusId() == RepoStatusEnum.ACTIVE.getId()) {
                return RepoStatusMsg(repositoryService.changeRepoStatus(repositoryUID, status), status);
            } else if (harRepoObj.getRepoStatusId().getRepoStatusId() == RepoStatusEnum.NOT_ACTIVE.getId()) {
                return RepoStatusEnum.NOT_ACTIVE.getName();
            }
        }
        return "Invalid Request";
    }

    private String changeRepoStatus(List<String> repoUIDSList, short status) {
        return RepoStatusMsg(repositoryService.changeRepoStatus(repoUIDSList, status), status);
    }

    private String RepoStatusMsg(boolean result, int status) {
        if (result) {
            if (status == RepoStatusEnum.ACTIVE.getId()) {
                return RepoStatusEnum.ACTIVE.getName();

            }
            if (status == RepoStatusEnum.NOT_ACTIVE.getId()) {
                return RepoStatusEnum.NOT_ACTIVE.getName();
            }
            return "ERROR";

        } else {
            return "ERROR";
        }
    }

    private HarRepoCustomised saveRepository(HarRepoCustomised harRepoCustomised) {
        HarRepoCustomised harRepoCustomisedObj;
        HarRepo repo = CustomBeansGenerator.convertHarRepoCustomisedToHarRepo(harRepoCustomised);
        repo = repositoryService.addRepository(repo);
        if (repo != null) {
            if (repositoryMetadataServiceObj.saveRepositoryMetadata(harRepoCustomised, MethodEnum.GET, "")) {
                List<HarRepoMetadata> harRepoMetadataList = repositoryMetadataServiceObj.list(repo);
                return CustomBeansGenerator.convertHarRepoToHarRepoCustomised(repo, harRepoMetadataList);
            } else {
                harRepoCustomisedObj = new HarRepoCustomised();
                harRepoCustomisedObj.setErrorMessage("Repository Metadata can't be saved");
            }
        } else {
            harRepoCustomisedObj = new HarRepoCustomised();
            harRepoCustomisedObj.setErrorMessage("Repository can't be saved");

        }
        return harRepoCustomisedObj;
    }

    private List<HarRepoCustomised> repositories() {
        List<HarRepoCustomised> harRepoCustomisedList = new ArrayList<>();
        List<HarRepo> harRepos = repositoryService.getAllRepositories();
        List<HarRepoMetadata> harRepoMetadataList;
        for (HarRepo repo : harRepos) {
            harRepoMetadataList = repositoryMetadataServiceObj.list(repo);
            harRepoCustomisedList.add(CustomBeansGenerator.convertHarRepoToHarRepoCustomised(repo, harRepoMetadataList));

        }
        return harRepoCustomisedList;
    }

    private Response updateRepository(String repoUID, HarRepoCustomised custObj) {
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
        repo.setRepoActivationDate(repoOrginal.getRepoActivationDate());
        repo.setRepoLastSyncDate(repoOrginal.getRepoLastSyncDate());
        repo.setRepoRegistrationDate(repoOrginal.getRepoRegistrationDate());
        repo.setRepoRowUpdateTime(repoOrginal.getRepoRowUpdateTime());
        repo.setRecordCount(repoOrginal.getRecordCount());
        repo.setRepoLastSyncEndDate(repoOrginal.getRepoLastSyncEndDate());

        repositoryService.editRepository(repo);
        repositoryMetadataServiceObj.saveOrUpdateRepositoryMetadata(custObj, repo);

        List<HarRepoMetadata> harRepoMetadataList = repositoryMetadataServiceObj.list(repo);
        custObj = CustomBeansGenerator.convertHarRepoToHarRepoCustomised(repo, harRepoMetadataList);

        return Response.ok().entity(custObj).build();
    }

    private Response getRepository(String repoUID) {
        HarRepo repo = repositoryService.getRepositoryByUID(repoUID);
        HarRepoCustomised custObj;
        if (repo == null) {
            return Response.status(400).entity("Repository with repoUID" + repoUID + "not Exist !!").build();
        }
        repositoryService.saveHarMetadataTypes(repo);
        List<HarRepoMetadata> harRepoMetadataList = repositoryMetadataServiceObj.list(repo);
        if (harRepoMetadataList.isEmpty()) {
            List<HarMetadataTypeRepository> harMetadataTypeRepositoryList = repositoryService.list(repo);
            custObj = CustomBeansGenerator.convertHarRepoToHarRepoCustomisedByHarMetadataTypeRepository(repo, harMetadataTypeRepositoryList);
        } else {
            custObj = CustomBeansGenerator.convertHarRepoToHarRepoCustomised(repo, harRepoMetadataList);
        }

        return Response.ok().entity(custObj).build();
    }
}
