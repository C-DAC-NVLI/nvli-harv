/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.controllers;

import in.gov.nvli.harvester.OAIPMH_beans.ListSetsType;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.SetType;
import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.services.ListSetsService;
import in.gov.nvli.harvester.servicesImpl.ListSetsServiceImpl;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
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
public class ListSetsController {
    
    
    @Autowired
    private ListSetsService service;
    @RequestMapping("/listSets")
    public ModelAndView listSets(String baseURL)
    {
       //service=new ListSetsServiceImpl();
       baseURL=baseURL+"?verb="+VerbType.LIST_SETS.value();
        ModelAndView mv=new ModelAndView("listsets");
        try {
           int status= service.getConnectionStatus(baseURL, "", "","");
           mv.addObject("status",status);
            if(!(status<0))
            {
             //   List<SetType> Sets=service.getListSets();
                service.saveListSets();
               // mv.addObject("sets",Sets);
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(IdentifyController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException | IOException ex) {
            Logger.getLogger(IdentifyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mv;
    }
    
    
}
