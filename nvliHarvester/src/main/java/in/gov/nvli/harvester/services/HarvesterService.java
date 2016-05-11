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

    public boolean harvestRepository(String baseURL, ServletContext servletContext);

    public Future<String> harvestRepositoryByUID(String repoUID, ServletContext servletContext);

    public void harvestAllRepositories(ServletContext servletContext);

    public void harvestRepositoryIncremental(String baseURL, ServletContext servletContext);

    public void harvestAllRepositoriesIncremental(ServletContext servletContext);

    public void harvestRepositories(List<HarRepo> harRepos, ServletContext servletContext);
    
    public void harvestRepositoriesIncremental(List<HarRepo> harRepos,ServletContext servletContext);
    
    public void harvestRepositoryIncrementalBYUID(String repoUID, ServletContext servletContext);

}
