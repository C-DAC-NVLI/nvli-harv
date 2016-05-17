/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.controllers;

import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.customised.MethodEnum;
import in.gov.nvli.harvester.dao.RepositoryDao;
import in.gov.nvli.harvester.services.ListMetadataFormatsService;
import in.gov.nvli.harvester.services.RepositoryService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author vootla
 */
@Controller
public class ListMetadataFormatsController {

    @Autowired
    private ListMetadataFormatsService service;

    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping("/listMetadataFormats")
    public ModelAndView listSets(String baseURL) {
        HarRepo repo = repositoryService.getRepository(2);

        baseURL = baseURL + CommonConstants.VERB + VerbType.LIST_METADATA_FORMATS.value();
        ModelAndView mv = new ModelAndView("ListMetadataFormats");
        try {
            service.saveHarMetadataTypes(repo, MethodEnum.GET, "");

        } catch (MalformedURLException ex) {
            Logger.getLogger(IdentifyController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException | IOException ex) {
            Logger.getLogger(IdentifyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mv;
    }

}
