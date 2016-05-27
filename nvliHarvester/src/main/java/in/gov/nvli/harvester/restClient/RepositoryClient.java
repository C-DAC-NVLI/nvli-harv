/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.restClient;

import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.customised.HarRepoCustomised;
import in.gov.nvli.harvester.services.RepositoryService;
import in.gov.nvli.harvester.utilities.CustomBeansGenerator;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author vootla
 */
@Component
 @RequestMapping("/client")
public class RepositoryClient {
   
   @Value("${nvli.client.url}")
   private String nvliClientURL;
   
   @Autowired
   private RepositoryService  repositoryService;

    public String getNvliClientURL() {
        return nvliClientURL;
    }

    public void setNvliClientURL(String nvliClientURL) {
        this.nvliClientURL = nvliClientURL;
    }
   
    private final static String updateRepositoryStatusURL = "update/resource/status/";
    private final static String updateRepositoryRecordCountURL = "update/record/count/";
    private final static String updateHrvestStartTimesURL = "update/resource/harvest/starttime/";
    private final static String updateHrvestEndTimesURL = "update/resource/harvest/endtime/";
   
   
    @RequestMapping("/update/{repoUID}")
    public void test(@PathVariable("repoUID") String repoUID) throws URISyntaxException
    {
       HarRepo repo = repositoryService.getRepositoryByUID(repoUID);
        System.out.println("in update"+repo.getRecordCount());
      synRepoWithClient(repo);
        
    }
    
    public void updateRepositoryStatus(HarRepo repo) throws URISyntaxException
    {
        updateRepository(repo,updateRepositoryStatusURL);
    }
    
    
    public void updateRepositoryRecordCount(HarRepo repo) throws URISyntaxException
    {
        updateRepository(repo,updateRepositoryRecordCountURL);
    }
    
    
    public void updateHarvestStartTime(HarRepo repo) throws URISyntaxException
    {
        updateRepository(repo,updateHrvestStartTimesURL);
    }
    
    public void updateHarvestEndTime(HarRepo repo) throws URISyntaxException
    {
        updateRepository(repo,updateHrvestEndTimesURL);
    }
    
    public void synRepoWithClient(HarRepo repo) throws URISyntaxException
    {
       updateRepositoryStatus(repo);
     //  updateRepositoryRecordCount(repo);
//        updateHarvestStartTime(repo);
//        updateHarvestEndTime(repo);
        
    }
    
    
    private void updateRepository(HarRepo repo,String serviceURL) throws URISyntaxException
    {
        HarRepoCustomised harRepoCustomised = null;
        String repoUID = null;
        if (repo != null) {
            repoUID = repo.getRepoUID();
            harRepoCustomised = CustomBeansGenerator.convertHarRepoToHarRepoCustomised(repo);
        }
        String url = getNvliClientURL() + serviceURL + repoUID;
        System.out.println("url"+url);
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<HarRepoCustomised> entity = new HttpEntity<>(harRepoCustomised, headers);
       // restTemplate.put(url, entity);
       ResponseEntity<HarRepoCustomised> response = restTemplate.exchange(url, HttpMethod.PUT, entity, HarRepoCustomised.class);
        System.out.println("re"+response.getStatusCode());
        System.out.println("res"+response.getBody().getRecordCount());

    }
    
    
    
}
