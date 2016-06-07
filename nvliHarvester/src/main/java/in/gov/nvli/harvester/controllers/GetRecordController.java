/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.controllers;

import in.gov.nvli.harvester.custom.harvester_enum.MethodEnum;
import in.gov.nvli.harvester.services.GetRecordService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import javax.xml.bind.JAXBException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author richa
 */
@Controller
public class GetRecordController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GetRecordController.class);

    @Autowired
    public GetRecordService getRecordService;

    @RequestMapping("/getrecord")
    public String getRecord(@RequestParam("baseURL") String baseURL, @RequestParam("identifier") String identifier, @RequestParam("metadataPrefix") String metadataPrefix) {

        try {

            getRecordService.saveGetRecord(baseURL, MethodEnum.GET, "", identifier, metadataPrefix);
        } catch (MalformedURLException | JAXBException | ParseException ex) {
            LOGGER.error(ex.getMessage(), ex);

        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return "example";
    }
}
