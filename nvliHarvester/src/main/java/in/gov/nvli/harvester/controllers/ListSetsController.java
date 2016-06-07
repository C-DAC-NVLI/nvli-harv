/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.controllers;

import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.custom.exception.OAIPMHerrorTypeException;
import in.gov.nvli.harvester.custom.harvester_enum.MethodEnum;
import in.gov.nvli.harvester.services.ListSetsService;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.xml.bind.JAXBException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author vootla
 */
@Controller
public class ListSetsController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ListSetsController.class);
            
    @Autowired
    private ListSetsService service;

    @RequestMapping("/listSets")
    public ModelAndView listSets(String baseURL) {
        //service=new ListSetsServiceImpl();
        baseURL = baseURL + "?verb=" + VerbType.LIST_SETS.value();
        ModelAndView mv = new ModelAndView("listsets");
        try {

            boolean status = service.saveHarSets(baseURL, MethodEnum.GET, "");

            mv.addObject("status", status);

        } catch (MalformedURLException ex) {
            LOGGER.error(ex.getMessage(), ex);
        } catch (JAXBException | IOException | OAIPMHerrorTypeException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return mv;
    }

}
