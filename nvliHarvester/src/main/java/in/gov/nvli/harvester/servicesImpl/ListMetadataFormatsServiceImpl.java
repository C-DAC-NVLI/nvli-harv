/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.MetadataFormatType;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHerrorType;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.beans.HarMetadataType;
import in.gov.nvli.harvester.beans.HarMetadataTypeRepository;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.custom.exception.OAIPMHerrorTypeException;
import in.gov.nvli.harvester.custom.harvester_enum.MethodEnum;
import in.gov.nvli.harvester.dao.HarMetadataTypeDao;
import in.gov.nvli.harvester.dao.HarMetadataTypeRepositoryDao;
import in.gov.nvli.harvester.dao.RepositoryDao;
import in.gov.nvli.harvester.services.ListMetadataFormatsService;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIBeanConverter;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vootla
 */
@Service
public class ListMetadataFormatsServiceImpl implements ListMetadataFormatsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListMetadataFormatsServiceImpl.class);
            
    @Autowired
    private HarMetadataTypeDao harMetadataTypeDao;

    @Autowired
    private HarMetadataTypeRepositoryDao harMetadataTypeRepositoryDao;
    
    @Autowired
    private RepositoryDao repositoryDaoObj;

    @Override
    public List<MetadataFormatType> getMetadataFormatTypeList(HttpURLConnection connection, String baseURL) throws IOException, JAXBException, OAIPMHerrorTypeException {
        String response = OAIResponseUtil.createResponseFromXML(connection);
        OAIPMHtype oAIPMHtypeObj = (OAIPMHtype) UnmarshalUtils.xmlToOaipmh(response);
        
        if (oAIPMHtypeObj.getError().isEmpty()) {
            return oAIPMHtypeObj.getListMetadataFormats().getMetadataFormat();
        } else {
            String errorMessages = "";
            for (OAIPMHerrorType tempOAIPMHerrorType : oAIPMHtypeObj.getError()) {
                errorMessages += tempOAIPMHerrorType.getValue() + "(" + tempOAIPMHerrorType.getCode() + "),";
            }
            HarRepo harRepoObj = repositoryDaoObj.getRepository(baseURL);
            String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_METADATA_FORMATS.value();
            throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                    + "\nActivity --> "+VerbType.LIST_METADATA_FORMATS.value()
                    + "\nCallingURL --> " + desiredURL
                    + "\nErrorCode --> " + errorMessages);
        }
    }
    

    @Override
    public boolean saveHarMetadataTypes(HarRepo harRepoObj, MethodEnum method, String adminEmail) throws MalformedURLException, IOException, JAXBException, OAIPMHerrorTypeException {
        String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_METADATA_FORMATS.value();
        return saveOrUpdateRecursive(harRepoObj,desiredURL, method, adminEmail);
    }

    private boolean saveOrUpdateRecursive(HarRepo harRepoObj, String desiredURL, MethodEnum method, String adminEmail) throws MalformedURLException, IOException, JAXBException, OAIPMHerrorTypeException {
        HttpURLConnection connection = HttpURLConnectionUtil.getConnection(desiredURL, method, adminEmail);
        if (HttpURLConnectionUtil.isConnectionAlive(connection)) {
            List<HarMetadataType> harMetadataTypeList = OAIBeanConverter.convertMetadataFormatTypeToHarMetadataType(getMetadataFormatTypeList(connection, harRepoObj.getRepoBaseUrl()));
            connection.disconnect();
            
            if (harMetadataTypeDao.saveHarMetadataTypes(harMetadataTypeList)) {
                LOGGER.info(harRepoObj.getRepoUID()+":"+harMetadataTypeList.size()+" Metadata Formats saved/updated");
                return saveHarMetadataTypeRepositoryList(harMetadataTypeList, harRepoObj);
            } else {
                throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                    + "\nActivity --> "+VerbType.LIST_METADATA_FORMATS.value()
                    + "\nCallingURL --> " + desiredURL
                    + "\nErrorCode --> " + "Database Problem");
            }
        }else{
            connection.disconnect();
            throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                    + "\nActivity --> "+VerbType.LIST_METADATA_FORMATS.value()
                    + "\nCallingURL --> " + desiredURL
                    + "\nErrorCode --> " + "Connection response code is not 200");
        }
    }

    private boolean saveHarMetadataTypeRepositoryList(List<HarMetadataType> harMetadataTypeList, HarRepo repository) {
        HarMetadataTypeRepository obj = null;
        List<HarMetadataTypeRepository> metadatOfRepo = new ArrayList<>();
        for (HarMetadataType meataData : harMetadataTypeList) {
            obj = new HarMetadataTypeRepository();
            obj.setRepoId(repository);
            obj.setMetadataTypeId(harMetadataTypeDao.getMetadataTypeByMetadatPrefix(meataData.getMetadataPrefix()));
            metadatOfRepo.add(obj);
        }
        return harMetadataTypeRepositoryDao.saveHarMetadataTypesOfRepository(metadatOfRepo);
    }

}
