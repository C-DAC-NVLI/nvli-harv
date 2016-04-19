/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.services.HarvesterService;
import in.gov.nvli.harvester.services.IdentifyService;
import in.gov.nvli.harvester.services.ListMetadataFormatsService;
import in.gov.nvli.harvester.services.ListRecordsService;
import in.gov.nvli.harvester.services.ListSetsService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import javax.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author richa
 */
@Service
public class HarvesterServiceImpl implements HarvesterService{
  
  
  @Autowired
  ListSetsService listSetsService;
  
  @Autowired
  ListMetadataFormatsService  listMetadataFormatsService;
  
  @Autowired
  ListRecordsService listRecordsService;

  @Override
  public void harvestReposiotires(String baseURL) throws MalformedURLException, IOException, JAXBException, ParseException{
    listRecordsService.getListRecord(baseURL);
    
   
  }
          
  
  
  
}
