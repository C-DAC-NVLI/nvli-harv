/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.utilities;

import in.gov.nvli.harvester.OAIPMH_beans.DescriptionType;
import in.gov.nvli.harvester.OAIPMH_beans.IdentifyType;
import in.gov.nvli.harvester.OAIPMH_beans.toolkit.ToolkitType;
import in.gov.nvli.harvester.beans.HarMetadataTypeRepository;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarRepoMetadata;
import in.gov.nvli.harvester.beans.HarRepoStatus;
import in.gov.nvli.harvester.beans.HarRepoType;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.custom.harvester_enum.HarRecordMetadataType;
import in.gov.nvli.harvester.custom.harvester_enum.RepoStatusEnum;
import in.gov.nvli.harvester.customised.HarRepoCustomised;
import in.gov.nvli.harvester.customised.IdentifyTypeCustomised;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author vootla
 */
public class CustomBeansGenerator {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomBeansGenerator.class);
    
    public static HarRepoCustomised convertHarRepoToHarRepoCustomised(HarRepo harRepo) {
        HarRepoCustomised repositoryObject = new HarRepoCustomised();
        repositoryObject.setRepoName(harRepo.getRepoName());
        repositoryObject.setRepoBaseUrl(harRepo.getRepoBaseUrl());
        repositoryObject.setRepoProtocolVersion(harRepo.getRepoProtocolVersion());
        repositoryObject.setRepoEarliestTimestamp(harRepo.getRepoEarliestTimestamp());
        repositoryObject.setRepoGranularityDate(harRepo.getRepoGranularityDate());
        repositoryObject.setRepoDeletionMode(harRepo.getRepoDeletionMode());
        repositoryObject.setRepoEmail(harRepo.getRepoEmail());
        repositoryObject.setRepoDesc(harRepo.getRepoDesc());
        repositoryObject.setRepoCompression(harRepo.getRepoCompression());
        repositoryObject.setRepoRegistrationDate(harRepo.getRepoRegistrationDate());
        repositoryObject.setRepoLink(harRepo.getRepoLink());
        repositoryObject.setRepoTypeId(harRepo.getRepoTypeId().getRepoTypeId());
        repositoryObject.setRepoSiteUrl(harRepo.getRepoSiteUrl());
        repositoryObject.setRepoPermanentLink(harRepo.getRepoPermanentLink());
        repositoryObject.setRepoLatitude(harRepo.getRepoLatitude());
        repositoryObject.setRepoLongitude(harRepo.getRepoLongitude());
        repositoryObject.setRepoStatusId(harRepo.getRepoStatusId().getRepoStatusId());
        repositoryObject.setRepoLastSyncDate(harRepo.getRepoLastSyncDate());
        repositoryObject.setRepoActivationDate(harRepo.getRepoActivationDate());
        repositoryObject.setRepoUID(harRepo.getRepoUID());
        
        
        repositoryObject.setRecordCount(harRepo.getRecordCount());
        repositoryObject.setHarvestStartTime(harRepo.getRepoLastSyncDate());
        repositoryObject.setHarvestEndTime(harRepo.getRepoLastSyncEndDate());
        
        return repositoryObject;
    }
    
    public static HarRepoCustomised convertHarRepoToHarRepoCustomised(HarRepo harRepo, List<HarRepoMetadata> harRepoMetadataList) {
        HarRepoCustomised repositoryObject = convertHarRepoToHarRepoCustomised(harRepo);
        
        Map<HarRecordMetadataType, Boolean> supportedMetadataTypesMap = new HashMap<>();
        HarRecordMetadataType harRecordMetadataTypeObj;
        for(HarRepoMetadata tempHarRepoMetadataObj : harRepoMetadataList){
            harRecordMetadataTypeObj = HarRecordMetadataType.valueOf(tempHarRepoMetadataObj.getMetadataTypeId().getMetadataPrefix().toUpperCase());
            if(tempHarRepoMetadataObj.getHarvestStatus().getRepoStatusId() == RepoStatusEnum.NOT_ACTIVE.getId()
                    || tempHarRepoMetadataObj.getHarvestStatus().getRepoStatusId() == RepoStatusEnum.INVALID_URL.getId()){
                supportedMetadataTypesMap.put(harRecordMetadataTypeObj, Boolean.FALSE);
            }else{
                supportedMetadataTypesMap.put(harRecordMetadataTypeObj, Boolean.TRUE);
            }
            
        }
        
        repositoryObject.setSupportedMetadataTypes(supportedMetadataTypesMap);
        return repositoryObject;
    }
    
    public static HarRepoCustomised convertHarRepoToHarRepoCustomisedByHarMetadataTypeRepository(HarRepo harRepo, List<HarMetadataTypeRepository> harMetadataTypeRepositoryList) {
        HarRepoCustomised repositoryObject = convertHarRepoToHarRepoCustomised(harRepo);
        
        Map<HarRecordMetadataType, Boolean> supportedMetadataTypesMap = new HashMap<>();
        HarRecordMetadataType harRecordMetadataTypeObj;
        for(HarMetadataTypeRepository tempHarMetadataTypeRepository : harMetadataTypeRepositoryList){
            try{
                harRecordMetadataTypeObj = HarRecordMetadataType.valueOf(tempHarMetadataTypeRepository.getMetadataTypeId().getMetadataPrefix().toUpperCase());
                supportedMetadataTypesMap.put(harRecordMetadataTypeObj, Boolean.FALSE);
            }catch(IllegalArgumentException ex){
                LOGGER.error(CommonConstants.WEB_SERVICES_LOG_MESSAGES + tempHarMetadataTypeRepository.getMetadataTypeId().getMetadataPrefix() +" is not supported by System");
            }
            
            
        }
        
        repositoryObject.setSupportedMetadataTypes(supportedMetadataTypesMap);
        return repositoryObject;
    }

    public static HarRepo convertHarRepoCustomisedToHarRepo(HarRepoCustomised harRepoCustomised) {
        HarRepo repositoryObject = new HarRepo();

        repositoryObject.setRepoName(harRepoCustomised.getRepoName());
        repositoryObject.setRepoBaseUrl(harRepoCustomised.getRepoBaseUrl());
        repositoryObject.setRepoProtocolVersion(harRepoCustomised.getRepoProtocolVersion());
        repositoryObject.setRepoEarliestTimestamp(harRepoCustomised.getRepoEarliestTimestamp());
        repositoryObject.setRepoGranularityDate(harRepoCustomised.getRepoGranularityDate());
        repositoryObject.setRepoDeletionMode(harRepoCustomised.getRepoDeletionMode());
        repositoryObject.setRepoEmail(harRepoCustomised.getRepoEmail());
        repositoryObject.setRepoDesc(harRepoCustomised.getRepoDesc());
        repositoryObject.setRepoCompression(harRepoCustomised.getRepoCompression());
        repositoryObject.setRepoRegistrationDate(harRepoCustomised.getRepoRegistrationDate());
        repositoryObject.setRepoLink(harRepoCustomised.getRepoLink());
        repositoryObject.setRepoTypeId(new HarRepoType(harRepoCustomised.getRepoTypeId()));
        repositoryObject.setRepoSiteUrl(harRepoCustomised.getRepoSiteUrl());
        repositoryObject.setRepoPermanentLink(harRepoCustomised.getRepoPermanentLink());
        repositoryObject.setRepoLatitude(harRepoCustomised.getRepoLatitude());
        repositoryObject.setRepoLongitude(harRepoCustomised.getRepoLongitude());
        repositoryObject.setRepoStatusId(new HarRepoStatus(harRepoCustomised.getRepoStatusId()));
        repositoryObject.setRepoLastSyncDate(harRepoCustomised.getRepoLastSyncDate());
        repositoryObject.setRepoActivationDate(harRepoCustomised.getRepoActivationDate());
        repositoryObject.setRepoUID(harRepoCustomised.getRepoUID());
        
        
       

        return repositoryObject;
    }

    public static IdentifyTypeCustomised convertIdentifyTypeToIdentifyTypeCustomised(IdentifyType identifyType) {
        IdentifyTypeCustomised obj = new IdentifyTypeCustomised();

        obj.setRepositoryName(identifyType.getRepositoryName());
        obj.setBaseURL(identifyType.getBaseURL());
        obj.setProtocolVersion(identifyType.getProtocolVersion());
        obj.setAdminEmail(identifyType.getAdminEmail());
        obj.setEarliestDatestamp(identifyType.getEarliestDatestamp());
        obj.setDeletedRecord(identifyType.getDeletedRecord().value());
        obj.setGranularity(identifyType.getGranularity().value());
        obj.setCompression(identifyType.getCompression());
        List<Map<String, String>> descList = new ArrayList<Map<String, String>>();
        try {

            for (DescriptionType desc : identifyType.getDescription()) {
                Map<String, String> subElements = new HashMap<>();
                ToolkitType toolKit = desc.getToolkit();
                if (toolKit != null) {
                    Map<String, Object> toolKitMap = new HashMap<>();
                    Map<String, String> authorMap = new HashMap<>();
                    toolKitMap.put("title", toolKit.getTitle());

                    authorMap.put("name", toolKit.getAuthor().getName());
                    authorMap.put("email", toolKit.getAuthor().getEmail());
                    authorMap.put("institution", toolKit.getAuthor().getInstitution());

                    toolKitMap.put("author", authorMap);

                    toolKitMap.put("version", toolKit.getVersion());
                    toolKitMap.put("toolkitIcon", toolKit.getToolkitIcon());
                    toolKitMap.put("URL", toolKit.getToolkitIcon());

                    subElements.put("toolkit ", toolKitMap.toString());
                }
                descList.add(subElements);
            }
        } catch (Exception e) {

        }

        System.out.println("descList" + descList);

        obj.setDescription(descList.toString());
        return obj;

    }
    
}
