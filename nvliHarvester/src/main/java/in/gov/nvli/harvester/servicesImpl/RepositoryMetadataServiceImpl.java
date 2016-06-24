/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.beans.HarMetadataType;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarRepoMetadata;
import in.gov.nvli.harvester.beans.HarRepoStatus;
import in.gov.nvli.harvester.custom.exception.OAIPMHerrorTypeException;
import in.gov.nvli.harvester.custom.harvester_enum.HarRecordMetadataType;
import in.gov.nvli.harvester.custom.harvester_enum.MethodEnum;
import in.gov.nvli.harvester.custom.harvester_enum.RepoStatusEnum;
import in.gov.nvli.harvester.customised.HarRepoCustomised;
import in.gov.nvli.harvester.dao.HarMetadataTypeDao;
import in.gov.nvli.harvester.dao.HarRepoMetadataDao;
import in.gov.nvli.harvester.dao.RepositoryDao;
import in.gov.nvli.harvester.services.ListMetadataFormatsService;
import in.gov.nvli.harvester.services.RepositoryMetadataService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ankit
 */
@Service
public class RepositoryMetadataServiceImpl implements RepositoryMetadataService{

    @Autowired
    private ListMetadataFormatsService listMetadataFormatsServiceObj;
    
    @Autowired
    private RepositoryDao repositoryDaoObj;
    
    @Autowired
    private HarMetadataTypeDao harMetadataTypeDaoObj;
    
    @Autowired
    private HarRepoMetadataDao harRepoMetadataDaoObj;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryMetadataServiceImpl.class);
    
    @Override
    public boolean saveRepositoryMetadata(HarRepoCustomised harRepoCustomisedObj, MethodEnum method, String adminEmail) {
        boolean success = false;
        try {
            if (listMetadataFormatsServiceObj.saveHarMetadataTypes(harRepoCustomisedObj.getRepoBaseUrl(), method, adminEmail)) {
                createNew(harRepoCustomisedObj);
                success = true;
            }
        } catch (IOException | JAXBException | OAIPMHerrorTypeException ex) {
            LOGGER.error("RepositoryUID --> " + harRepoCustomisedObj.getRepoUID()
                    + "\nActivity --> Saving repository metadata" 
                    + "\nErrorCode --> " + ex.getMessage()
                    , ex);
            success = false;
        } catch (Exception ex) {
            LOGGER.error("RepositoryUID --> " + harRepoCustomisedObj.getRepoUID()
                    + "\nActivity --> Saving repository metadata" 
                    + "\nErrorCode --> " + ex.getMessage()
                    , ex);
            success = false;
        } 
        return success;
    }
    
    private void createNew(HarRepoCustomised harRepoCustomisedObj){
        HarRepoMetadata harRepoMetadataObj;
        HarRepoStatus harRepoStatusObj;
        HarMetadataType harMetadataTypeObj;
        
        HarRepo harRepoObj = repositoryDaoObj.getRepositoryByUID(harRepoCustomisedObj.getRepoUID());
        harRepoStatusObj = harRepoObj.getRepoStatusId();
        
        Map<HarRecordMetadataType, Boolean> supportedMetadataTypes = harRepoCustomisedObj.getSupportedMetadataTypes();
        for(Map.Entry<HarRecordMetadataType, Boolean> tempHarRecordMetadataTypeObj : supportedMetadataTypes.entrySet()){
            if(Objects.equals(tempHarRecordMetadataTypeObj.getValue(), Boolean.TRUE)){
                harRepoMetadataObj = new HarRepoMetadata();
                harMetadataTypeObj = harMetadataTypeDaoObj.getMetadataTypeByMetadatPrefix(tempHarRecordMetadataTypeObj.getKey().value());
                
                harRepoMetadataObj.setHarvestStatus(harRepoStatusObj);
                harRepoMetadataObj.setRepoId(harRepoObj);
                harRepoMetadataObj.setMetadataTypeId(harMetadataTypeObj);
                
                harRepoMetadataDaoObj.createNew(harRepoMetadataObj);
            }
        }
    }
    
    @Override
    public boolean changeRepositoryMetadataStatus(HarRepo harRepoObj, short statusId){
        return harRepoMetadataDaoObj.changeRepositoryMetadataStatus(harRepoObj, statusId);
    }
    
    @Override
    public void saveOrUpdateRepositoryMetadata(HarRepoCustomised harRepoCustomisedObj, HarRepo harRepoObj){
        HarRepoMetadata harRepoMetadataObj;
        Map<HarRecordMetadataType, Boolean> supportedMetadataTypes = harRepoCustomisedObj.getSupportedMetadataTypes();
        short statusId;
        
        for(Map.Entry<HarRecordMetadataType, Boolean> tempEntryObj : supportedMetadataTypes.entrySet()){
            
            harRepoMetadataObj = harRepoMetadataDaoObj.get(harRepoCustomisedObj.getRepoUID(), tempEntryObj.getKey());
            
            if(harRepoMetadataObj == null){
                harRepoMetadataObj = new HarRepoMetadata();
                harRepoMetadataObj.setRepoId(harRepoObj);
                harRepoMetadataObj.setHarvestStatus(new HarRepoStatus(harRepoCustomisedObj.getRepoStatusId()));
                harRepoMetadataObj.setMetadataTypeId(harMetadataTypeDaoObj.getMetadataTypeByMetadatPrefix(tempEntryObj.getKey().value()));
                harRepoMetadataDaoObj.createNew(harRepoMetadataObj);
            }
            
            statusId = harRepoMetadataObj.getHarvestStatus().getRepoStatusId();
            
            if(Objects.equals(tempEntryObj.getValue(), Boolean.TRUE)){
                if(Objects.equals(statusId, RepoStatusEnum.NOT_ACTIVE.getId())){
                    harRepoMetadataDaoObj.updateStatus(harRepoMetadataObj, RepoStatusEnum.ACTIVE.getId());
                }
            }else{
                if(Objects.equals(statusId, RepoStatusEnum.ACTIVE.getId())){
                    harRepoMetadataDaoObj.updateStatus(harRepoMetadataObj, RepoStatusEnum.NOT_ACTIVE.getId());
                }
            }
            
        }
    }
    
    @Override
    public List<HarRepoMetadata> list(HarRepo harRepoObj){
        return harRepoMetadataDaoObj.list(harRepoObj);
    }
}
