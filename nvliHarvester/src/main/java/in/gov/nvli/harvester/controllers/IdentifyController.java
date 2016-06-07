/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.controllers;

import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.custom.harvester_enum.MethodEnum;
import in.gov.nvli.harvester.services.IdentifyService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public IdentifyService identifyService;

    @RequestMapping("/identify")
    public ModelAndView identify(@RequestParam("baseURL") String baseURL) {

        ModelAndView mv = new ModelAndView("identify");
        try {
            HarRepo repo = identifyService.getIdentify(baseURL, MethodEnum.GET, "");
            mv.addObject("repo", repo);

        } catch (MalformedURLException ex) {
            Logger.getLogger(IdentifyController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException | IOException ex) {
            Logger.getLogger(IdentifyController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mv;
    }

}
