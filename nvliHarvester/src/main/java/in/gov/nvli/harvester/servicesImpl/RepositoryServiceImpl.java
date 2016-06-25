/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.beans.HarMetadataTypeRepository;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.custom.exception.OAIPMHerrorTypeException;
import in.gov.nvli.harvester.custom.harvester_enum.MethodEnum;
import in.gov.nvli.harvester.dao.HarMetadataTypeRepositoryDao;
import in.gov.nvli.harvester.dao.RepositoryDao;
import in.gov.nvli.harvester.services.ListMetadataFormatsService;
import in.gov.nvli.harvester.services.RepositoryService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ankit
 */
@Service
public class RepositoryServiceImpl implements RepositoryService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RepositoryServiceImpl.class);
    
    @Autowired
    private RepositoryDao repositoryDaoObject;
    
    @Autowired
    private HarMetadataTypeRepositoryDao harMetadataTypeRepositoryDaoObj;
    
    @Autowired
    private ListMetadataFormatsService listMetadataFormatsServiceObj;

    @Override
    public HarRepo addRepository(HarRepo repositoryObject) {
        return repositoryDaoObject.addRepository(repositoryObject);

    }

    @Override
    public List<HarRepo> getAllRepositories() {
        return repositoryDaoObject.list();
    }

    @Override
    public HarRepo getRepository(int repositoryId) {
        return repositoryDaoObject.get(repositoryId);
    }

    @Override
    public List<HarRepo> getRepositoriesByStatus(List<Integer> statusIds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editRepository(HarRepo repositoryObject) {
       repositoryDaoObject.saveOrUpdate(repositoryObject);
    }

    @Override
    public boolean deleteRepository(int repositoryId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteRepositories(List<Integer> repositoryIds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void activateOrDeactivateRepositories(List<Integer> repositoryIds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void activateOrDeactivateRepository(int repositoryId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void publishOrWithdrawRepositories(List<Integer> repositoryIds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void publishOrWithdrawRepository(int repositoryId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void blockRepositories(List<Integer> repositoryIds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void blockRepository(int repositoryId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void synchroniseRepositories(List<Integer> repositoryIds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void synchroniseRepository(int repositoryId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void scheduleSynchronisationOfRepositories(List<Integer> repositoryIds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void scheduleSynchronisationOfRepository(int repositoryId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validateRepositories(List<Integer> repositoryIds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isRepositoryValid(int repositoryId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HarRepo getRepositoryByUID(String repoUID) {
       return repositoryDaoObject.getRepositoryByUID(repoUID);
    }

    @Override
    public List<HarRepo> getRepositoriesByUIDS(List<String> repoUIDS) {
        return repositoryDaoObject.getRepositories(repoUIDS);
    }

    @Override
    public boolean changeRepoStatus(List<String> repositoryUIDs, short status) {
        return repositoryDaoObject.changeRepoStatus(repositoryUIDs, status);
    }

    @Override
    public boolean changeRepoStatus(String repositoryUID, short status) {
         return repositoryDaoObject.changeRepoStatus(repositoryUID, status);
    }

   @Override
    public boolean changeRepoStatus(short status) {
        List<HarRepo> repos=repositoryDaoObject.getRepositories();
        return repositoryDaoObject.changeRepoStatusByHarRepo(repos, status);
    }

    @Override
    public List<HarRepo> getActiveRepositories() {
        return repositoryDaoObject.getActiveRepositories();
    }

    @Override
    public List<HarRepo> getRepositoriesByStaus(short repoStatusId) {
        return repositoryDaoObject.getRepositoriesByStaus(repoStatusId);
        
    }
    
    @Override
    public List<HarMetadataTypeRepository> list(HarRepo harRepoObj){
        return harMetadataTypeRepositoryDaoObj.list(harRepoObj);
    }
    
    @Override
    public void saveHarMetadataTypes(HarRepo harRepoObj){
        try {
            listMetadataFormatsServiceObj.saveHarMetadataTypes(harRepoObj.getRepoBaseUrl(), MethodEnum.GET, "");
        } catch (IOException| JAXBException | OAIPMHerrorTypeException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

}
