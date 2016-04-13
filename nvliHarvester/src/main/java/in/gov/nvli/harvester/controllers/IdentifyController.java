/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.controllers;

import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.services.IdentifyService;
import in.gov.nvli.harvester.servicesImpl.IdentifyServiceImpl;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author vootla
 */
@Controller
public class IdentifyController {
 
  // @Autowired
    public IdentifyService identifyService;
 
   @RequestMapping("/identify")
   public ModelAndView identify(@RequestParam("baseURL") String baseURL)
   {
       identifyService=new IdentifyServiceImpl();
       baseURL=baseURL+"?verb="+VerbType.IDENTIFY.value();
        ModelAndView mv=new ModelAndView("identify");
        try {
           int status= identifyService.getConnectionStatus(baseURL, "", "","");
           mv.addObject("status",status);
            if(!(status<0))
            {
                OAIPMHtype OAIPMHObj=identifyService.getRepositoryInformation();
                mv.addObject("OAIPMHObj",OAIPMHObj);
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(IdentifyController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException | IOException ex) {
            Logger.getLogger(IdentifyController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
      
return mv;
   }
    
}
