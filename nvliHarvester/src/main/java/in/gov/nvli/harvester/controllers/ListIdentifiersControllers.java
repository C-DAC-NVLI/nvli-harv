/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.controllers;

import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.customised.MethodEnum;
import in.gov.nvli.harvester.services.ListIdentifiersService;
import in.gov.nvli.harvester.servicesImpl.ListIdentifiersServiceImpl;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
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
public class ListIdentifiersControllers {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListIdentifiersControllers.class);

    @Autowired
    private ListIdentifiersService listIdentifiersService;

    @RequestMapping("/listidentifiers")
    public String listIdentifiers(@RequestParam("baseURL") String baseURL, @RequestParam("metadataPrefix") String metadataPrefix) {

        String requestURL = baseURL + CommonConstants.VERB + VerbType.LIST_IDENTIFIERS.value() + CommonConstants.METADATA_PREFIX + metadataPrefix;

        try {
            listIdentifiersService.getListIdentifiers(requestURL, MethodEnum.GET, "", "oai_dc");
        } catch (MalformedURLException ex) {

            LOGGER.error(ex.getMessage(), ex);
        } catch (IOException | JAXBException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return "example";
    }

}
