/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.controllers;

import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarRepoStatus;
import in.gov.nvli.harvester.beans.HarRepoType;
import in.gov.nvli.harvester.services.RepositoryService;
import in.gov.nvli.harvester.servicesImpl.RepositoryServiceImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Bhumika
 */
@Controller
public class RepositoryController {

    @Autowired
    RepositoryService repositoryService;

    @RequestMapping("/addrepository")
    public ModelAndView addRepository(HarRepo repositoryObject) {
        System.out.println("in.gov.nvli.harvester.controllers.RepositoryController.addRepository()");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String earliestTimeString = "2000-12-03 14:55:56";
        String regDateString = "2000-05-04 13:22:11";
        String lastSynchString = "2000-12-30 14:55:06";
        String activationString = "2016-05-04 03:22:11";
        short repositoryTypeId = 1;
        short statusid = 1;
        ModelAndView mv = new ModelAndView("added_repository");
        try {
            //repositoryService = new RepositoryServiceImpl();
            HarRepo repObject = new HarRepo();
            repositoryObject.setRepoName("Name");
            repositoryObject.setRepoBaseUrl("baseurl");
            repositoryObject.setRepoProtocolVersion("protocol version");

            Date earliestTimeDate = formatter.parse(earliestTimeString);
            //System.out.println(earliestTimeDate);
            //System.out.println(formatter.format(earliestTimeDate));
            repositoryObject.setRepoEarliestTimestamp(earliestTimeDate);
            repositoryObject.setRepoGranularityDate("granularitydate");
            repositoryObject.setRepoDeletionMode("deletionmode");
            repositoryObject.setRepoEmail("email");
            repositoryObject.setRepoDesc("description");
            repositoryObject.setRepoCompression("compression");

            Date regDateDate = formatter.parse(regDateString);
            repositoryObject.setRepoRegistrationDate(regDateDate);
            repositoryObject.setRepoLink("repolink");

            //get repository type object based on id
            repositoryObject.setRepoTypeId(new HarRepoType(repositoryTypeId));
            repositoryObject.setRepoSiteUrl("siteurl");
            repositoryObject.setRepoPermanentLink("permanentlink");
            repositoryObject.setRepoLatitude("latitude");
            repositoryObject.setRepoLongitude("longitude");

            //get status object based on id
            repositoryObject.setRepoStatusId(new HarRepoStatus(statusid));
            Date lastSynchDate = formatter.parse(lastSynchString);
            repositoryObject.setRepoLastSyncDate(lastSynchDate);
            Date activationDate = formatter.parse(activationString);
            repositoryObject.setRepoActivationDate(activationDate);

            if (repositoryService.addRepository(repositoryObject) != null) {
                return mv;
            }
        } catch (ParseException e) {
            Logger.getLogger(RepositoryController.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception e) {
            Logger.getLogger(RepositoryController.class.getName()).log(Level.SEVERE, null, e);
        }
        return mv;
    }

    public static void main(String args[]){
        System.out.println("--->"+new RepositoryController().addRepository(new HarRepo()));
    }
}
