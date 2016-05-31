/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.controllers;

import in.gov.nvli.harvester.customised.HarRepoCustomised;
import in.gov.nvli.harvester.services.HarvesterService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author vootla
 */
@Controller
@EnableAsync
public class HarvesterController {

  @Autowired
  HarvesterService harvesterService;

  @RequestMapping("/")
  public String exampleMethod() {
    return "example";
  }

  @RequestMapping("/harvester")
  public String harvestRepository(@RequestParam("baseURL") String baseURL) {
    harvesterService.harvestRepository(baseURL);

    return "example";
  }

  @RequestMapping("/harvestall")
  public String harvestAll() {
    harvesterService.harvestAllRepositories();
    return "example";
  }
  
  @RequestMapping("/harvester_incremental")
  public String harvestRepositoryIncremental(@RequestParam("baseURL") String baseURL) {
    harvesterService.harvestRepositoryIncremental(baseURL);
    return "example";
  }
  
  @RequestMapping("/harvest_all_incremental")
  public String harvestAllRepositoriesIncremental() {
        harvesterService.harvestAllRepositoriesIncremental();
        return "example";
  }
  @RequestMapping("/test")
public void test() throws URISyntaxException
{
     URI webServiceURL = new URI("http://localhost:8080/nvliHarvester/rest/repositories");//"http://nvli-vm.pune.cdac.in:8080/harvester/rest/repositories"
            RestTemplate restTemplate = new RestTemplate();
            HarRepoCustomised harRepoCustomised = new HarRepoCustomised();
            harRepoCustomised.setRepoName("arxiv");
            harRepoCustomised.setRepoBaseUrl("http://export.arxiv.org/oai2");
            harRepoCustomised.setRepoRegistrationDate(new Date());
            harRepoCustomised.setRepoStatusId((short) 1);
            harRepoCustomised.setRepoTypeId(1);
            harRepoCustomised.setRepoUID("1");
            harRepoCustomised.setOreEnableFlag((byte)1);
            ResponseEntity<HarRepoCustomised> responseObj = restTemplate.postForEntity(webServiceURL, harRepoCustomised, HarRepoCustomised.class);
            System.out.println("Has done " + responseObj.getStatusCode());
    
}


}