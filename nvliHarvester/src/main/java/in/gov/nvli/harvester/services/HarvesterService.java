/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import in.gov.nvli.harvester.beans.HarRepo;
import java.util.List;
import java.util.concurrent.Future;
import javax.servlet.ServletContext;

/**
 *
 * @author richa
 */
public interface HarvesterService {

    public boolean harvestRepository(String baseURL);

    public Future<String> harvestRepositoryByUID(String repoUID);

    public void harvestAllRepositories();

    public void harvestRepositoryIncremental(String baseURL);

    public void harvestAllRepositoriesIncremental();

    public void harvestRepositories(List<HarRepo> harRepos);
    
    public void harvestRepositoriesIncremental(List<HarRepo> harRepos);
    
    public void harvestRepositoryIncrementalBYUID(String repoUID);
    
    public void harvestAllActiveRepositories( List<HarRepo> harRepos);

}
