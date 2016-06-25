/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.controllers;

import in.gov.nvli.harvester.custom.harvester_enum.HarRecordMetadataTypeEnum;
import in.gov.nvli.harvester.custom.harvester_enum.MethodEnum;
import in.gov.nvli.harvester.services.ListRecordsService;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author richa
 */
@Controller
@EnableAsync
public class ListRecordsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListRecordsController.class);

    @Autowired
    public ListRecordsService listRecordsService;

    @RequestMapping("/listrecords")
    public String listRecord(@RequestParam("baseURL") String baseURL, @RequestParam("metadataPrefix") String metadataPrefix, HttpServletRequest servletRequest) {
        listRecordsService.saveListRecords(baseURL, HarRecordMetadataTypeEnum.OAI_DC, MethodEnum.GET, "");
        return "example";
    }

}
