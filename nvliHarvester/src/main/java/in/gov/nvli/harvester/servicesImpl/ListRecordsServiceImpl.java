/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import com.sun.syndication.io.FeedException;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHerrorType;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.RecordType;
import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.beans.HarRecord;
import in.gov.nvli.harvester.beans.HarRecordData;
import in.gov.nvli.harvester.beans.HarRecordMetadataDc;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarSetRecord;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.custom.exception.OAIPMHerrorTypeException;
import in.gov.nvli.harvester.customised.MethodEnum;
import in.gov.nvli.harvester.dao.HarRecordDao;
import in.gov.nvli.harvester.dao.HarRecordDataDao;
import in.gov.nvli.harvester.dao.HarRecordMetadataDcDao;
import in.gov.nvli.harvester.dao.HarSetRecordDao;
import in.gov.nvli.harvester.dao.RepositoryDao;
import in.gov.nvli.harvester.services.GetRecordService;
import in.gov.nvli.harvester.services.ListRecordsService;
import in.gov.nvli.harvester.utilities.HarvesterLogUtils;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author richa
 */
@Service
public class ListRecordsServiceImpl implements ListRecordsService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ListRecordsServiceImpl.class);

    @Value("${repository.data.path}")
    private String repositoryDataPath;

    @Autowired
    private HarRecordDao harRecordDao;

    @Autowired
    private HarSetRecordDao harSetRecordDao;

    @Autowired
    private HarRecordMetadataDcDao harRecordMetadataDcDao;

    @Autowired
    private HarRecordDataDao harRecordDataDao;

    @Autowired
    private GetRecordService getRecordServiceObject;

    @Autowired
    private RepositoryDao repositoryDao;

    public ListRecordsServiceImpl() {
    }

    @Override
    public boolean saveListRecords(HarRepo harRepoObj, String metadataPrefix, MethodEnum method, String adminEmail) throws OAIPMHerrorTypeException, ParseException, JAXBException, IOException {
        String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.METADATA_PREFIX + metadataPrefix;
        return saveListRecordsRecursive(harRepoObj, desiredURL, metadataPrefix, method, adminEmail, false);
    }

    @Override
    public boolean saveListRecords(String baseURL, String metadataPrefix, MethodEnum method, String adminEmail) throws OAIPMHerrorTypeException, ParseException, JAXBException, IOException {
        HarRepo harRepoObj = repositoryDao.getRepository(baseURL);
        return saveListRecords(harRepoObj, metadataPrefix, method, adminEmail);
    }

    @Override
    public boolean saveOrUpdateListRecords(HarRepo harRepoObj, String metadataPrefix, MethodEnum method, String adminEmail) throws OAIPMHerrorTypeException, ParseException, JAXBException, IOException {
        String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.METADATA_PREFIX + metadataPrefix;
        return saveListRecordsRecursive(harRepoObj, desiredURL, metadataPrefix, method, adminEmail, true);
    }

    @Override
    public boolean saveOrUpdateListRecords(String baseURL, String metadataPrefix, MethodEnum method, String adminEmail) throws OAIPMHerrorTypeException, ParseException, JAXBException, IOException {
        HarRepo harRepoObj = repositoryDao.getRepository(baseURL);
        return saveOrUpdateListRecords(harRepoObj, metadataPrefix, method, adminEmail);
    }

    private boolean saveListRecordsRecursive(HarRepo harRepoObj, String desiredURL, String metadataPrefix, MethodEnum method, String adminEmail, boolean incrementalFlag) throws OAIPMHerrorTypeException, ParseException, JAXBException, IOException {
        try {
            HttpURLConnection connection = HttpURLConnectionUtil.getConnection(desiredURL, method, adminEmail);

            if (HttpURLConnectionUtil.isConnectionAlive(connection)) {
                String resumptionToken;
                List<HarRecord> harRecordList = new ArrayList<>();
                List<HarSetRecord> harSetRecordList = new ArrayList<>();
                List<HarRecordMetadataDc> harRecordMetadataDcList = new ArrayList<>();
                HarRecord harRecordObj;
                List<HarSetRecord> tempHarSetRecordList;
                HarRecordMetadataDc harRecordMetadataDcObj;

                String response = OAIResponseUtil.createResponseFromXML(connection);
                connection.disconnect();
                OAIPMHtype oAIPMHtypeObject = UnmarshalUtils.xmlToOaipmh(response);
                if (oAIPMHtypeObject != null) {
                    if (oAIPMHtypeObject.getError().isEmpty()) {
                        if (oAIPMHtypeObject.getListRecords() != null) {
                            List<RecordType> recordTypeList = oAIPMHtypeObject.getListRecords().getRecord();
                            if (recordTypeList != null) {
                                for (RecordType recordTypeObj : recordTypeList) {
                                    harRecordObj = getRecordServiceObject.getHarRecordByRecordType(recordTypeObj, metadataPrefix, harRepoObj);
                                    tempHarSetRecordList = getRecordServiceObject.getHarSetRecordListByRecordType(recordTypeObj, harRecordObj);
                                    harRecordMetadataDcObj = getRecordServiceObject.getHarRecordMetadataDcByRecordType(recordTypeObj, harRecordObj);

                                    harRecordList.add(harRecordObj);
                                    harSetRecordList.addAll(tempHarSetRecordList);
                                    if (harRecordMetadataDcObj != null) {
                                        harRecordMetadataDcList.add(harRecordMetadataDcObj);
                                    }

                                }

                                if (incrementalFlag) {
                                    if (!harRecordList.isEmpty()) {
                                        harRecordDao.saveOrUpdateHarRecordList(harRecordList);
                                    }
                                    if (!harSetRecordList.isEmpty()) {
                                        harSetRecordDao.saveOrUpdateHarSetRecords(harSetRecordList);
                                    }
                                    if (!harRecordMetadataDcList.isEmpty()) {
                                        harRecordMetadataDcDao.saveOrUpdateHarRecordMetadataDcList(harRecordMetadataDcList);
                                    }
                                    LOGGER.info(harRepoObj.getRepoUID() + ":" + harRecordList.size() + " Records updated");
                                } else {
                                    if (!harRecordList.isEmpty()) {
                                        harRecordDao.saveList(harRecordList);
                                    }
                                    if (!harSetRecordList.isEmpty()) {
                                        harSetRecordDao.saveList(harSetRecordList);
                                    }
                                    if (!harRecordMetadataDcList.isEmpty()) {
                                        harRecordMetadataDcDao.saveList(harRecordMetadataDcList);
                                    }
                                    LOGGER.info(harRepoObj.getRepoUID() + ":" + harRecordList.size() + " Records saved");
                                }

                                if (oAIPMHtypeObject.getListRecords().getResumptionToken() != null) {
                                    resumptionToken = oAIPMHtypeObject.getListRecords().getResumptionToken().getValue();
                                    if (resumptionToken != null && !resumptionToken.equals("") && !resumptionToken.isEmpty()) {
                                        desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.RESUMPTION_TOKEN + resumptionToken;
                                        saveListRecordsRecursive(harRepoObj, desiredURL, metadataPrefix, method, adminEmail, incrementalFlag);
                                    }
                                }
                            } else {
                                throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                                        + "\nActivity --> " + VerbType.LIST_RECORDS.value()
                                        + "\nCallingURL --> " + desiredURL
                                        + "\nErrorCode --> " + HarvesterLogUtils.NO_RECORDS_FOUND);
                            }
                        } else {
                            throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                                    + "\nActivity --> " + VerbType.LIST_RECORDS.value()
                                    + "\nCallingURL --> " + desiredURL
                                    + "\nErrorCode --> " + HarvesterLogUtils.NO_LIST_RECORDS_FOUND);
                        }
                    } else {
                        String errorMessages = "";
                        for (OAIPMHerrorType tempOAIPMHerrorType : oAIPMHtypeObject.getError()) {
                            errorMessages += tempOAIPMHerrorType.getValue() + "(" + tempOAIPMHerrorType.getCode() + "),";
                        }
                        throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                                + "\nActivity --> " + VerbType.LIST_RECORDS.value()
                                + "\nCallingURL --> " + desiredURL
                                + "\nErrorCode --> " + errorMessages);
                    }
                } else {
                    throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                            + "\nActivity --> " + VerbType.LIST_RECORDS.value()
                            + "\nCallingURL --> " + desiredURL
                            + "\nErrorCode --> " + HarvesterLogUtils.NOT_OAI_PMH_COMPATIBLE_RESPONSE);
                }

            } else {
                if(connection != null){
                    connection.disconnect();
                }
                throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                        + "\nActivity --> " + VerbType.LIST_RECORDS.value()
                        + "\nCallingURL --> " + desiredURL
                        + "\nErrorCode --> " + "Connection response code is not 200");
            }

        } catch (ParseException | JAXBException | IOException ex) {
            LOGGER.error("RepositoryUID --> " + harRepoObj.getRepoUID()
                    + "\nActivity --> " + VerbType.LIST_RECORDS.value()
                    + "\nCallingURL --> " + desiredURL
                    + ex.getMessage(), ex);
            throw ex;
        }

        return true;

    }

    @Override
    public boolean saveListHarRecordData(String baseURL, MethodEnum method, String adminEmail) throws OAIPMHerrorTypeException, ParseException, JAXBException, IOException, TransformerException, TransformerConfigurationException, IllegalArgumentException, FeedException {
        HarRepo harRepoObj = repositoryDao.getRepository(baseURL);
        return saveListHarRecordData(harRepoObj, method, adminEmail);
    }

    @Override
    public boolean saveListHarRecordData(HarRepo harRepoObj, MethodEnum method, String adminEmail) throws OAIPMHerrorTypeException, ParseException, JAXBException, IOException, TransformerException, TransformerConfigurationException, IllegalArgumentException, FeedException {
        String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.METADATA_PREFIX + "ore";
        if (saveListHarRecordDataRecursive(harRepoObj, desiredURL, method, adminEmail, false)) {
            return saveHarRecordDataIntoFileSystem(harRepoObj);
        }
        return false;
    }

    @Override
    public boolean saveOrUpdateListHarRecordData(String baseURL, MethodEnum method, String adminEmail) throws OAIPMHerrorTypeException, ParseException, JAXBException, IOException, TransformerException, TransformerConfigurationException, IllegalArgumentException, FeedException {
        HarRepo harRepoObj = repositoryDao.getRepository(baseURL);
        return saveOrUpdateListHarRecordData(harRepoObj, method, adminEmail);
    }

    @Override
    public boolean saveOrUpdateListHarRecordData(HarRepo harRepoObj, MethodEnum method, String adminEmail) throws OAIPMHerrorTypeException, ParseException, JAXBException, IOException, TransformerException, TransformerConfigurationException, IllegalArgumentException, FeedException {
        String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.METADATA_PREFIX + "ore";
        if (saveListHarRecordDataRecursive(harRepoObj, desiredURL, method, adminEmail, true)) {
            return saveHarRecordDataIntoFileSystem(harRepoObj);
        }
        return false;
    }

    private boolean saveListHarRecordDataRecursive(HarRepo harRepoObj, String desiredURL, MethodEnum method, String adminEmail, boolean incrementalFlag) throws OAIPMHerrorTypeException, ParseException, JAXBException, IOException, TransformerException, TransformerConfigurationException, IllegalArgumentException, FeedException {
        try {
            HttpURLConnection connection = HttpURLConnectionUtil.getConnection(desiredURL, method, adminEmail);

            if (HttpURLConnectionUtil.isConnectionAlive(connection)) {
                String resumptionToken;
                HarRecordData harRecordDataObject;
                List<HarRecordData> harRecordDataList = new ArrayList<>();

                String response = OAIResponseUtil.createResponseFromXML(connection);
                OAIPMHtype oAIPMHtypeObject = UnmarshalUtils.xmlToOaipmh(response);
                if (oAIPMHtypeObject != null) {
                    if (oAIPMHtypeObject.getError().isEmpty()) {
                        if (oAIPMHtypeObject.getListRecords() != null) {
                            List<RecordType> recordTypeList = oAIPMHtypeObject.getListRecords().getRecord();
                            if (recordTypeList != null) {
                                for (RecordType recordTypeObj : recordTypeList) {
                                    harRecordDataObject = getRecordServiceObject.getHarRecordDataByRecordType(recordTypeObj);
                                    if (harRecordDataObject != null) {
                                        harRecordDataList.add(harRecordDataObject);
                                    }
                                }
                                if (incrementalFlag) {
                                    if (!harRecordDataList.isEmpty()) {
                                        harRecordDataDao.saveOrUpdateHarRecordDataList(harRecordDataList);
                                    }
                                } else if (!harRecordDataList.isEmpty()) {
                                    harRecordDataDao.saveList(harRecordDataList);
                                }

                                resumptionToken = oAIPMHtypeObject.getListRecords().getResumptionToken().getValue();
                                if (resumptionToken != null && !resumptionToken.equals("") && !resumptionToken.isEmpty()) {
                                    desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.RESUMPTION_TOKEN + resumptionToken;
                                    saveListHarRecordDataRecursive(harRepoObj, desiredURL, method, adminEmail, incrementalFlag);
                                }
                            } else {
                                throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                                        + "\nActivity --> " + VerbType.LIST_RECORDS.value()
                                        + "\nCallingURL --> " + desiredURL
                                        + "\nErrorCode --> " + HarvesterLogUtils.NO_RECORDS_FOUND);
                            }
                        } else {
                            throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                                    + "\nActivity --> " + VerbType.LIST_RECORDS.value()
                                    + "\nCallingURL --> " + desiredURL
                                    + "\nErrorCode --> " + HarvesterLogUtils.NO_LIST_RECORDS_FOUND);
                        }
                    } else {
                        String errorMessages = "";
                        for (OAIPMHerrorType tempOAIPMHerrorType : oAIPMHtypeObject.getError()) {
                            errorMessages += tempOAIPMHerrorType.getValue() + "(" + tempOAIPMHerrorType.getCode() + "),";
                        }
                        throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                                + "\nActivity --> " + VerbType.LIST_RECORDS.value()
                                + "\nCallingURL --> " + desiredURL
                                + "\nErrorCode --> " + errorMessages);
                    }
                } else {
                    throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                            + "\nActivity --> " + VerbType.LIST_RECORDS.value()
                            + "\nCallingURL --> " + desiredURL
                            + "\nErrorCode --> " + HarvesterLogUtils.NOT_OAI_PMH_COMPATIBLE_RESPONSE);
                }

            } else {
                connection.disconnect();
                throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                        + "\nActivity --> ListRecords-ORE"
                        + "\nCallingURL --> " + desiredURL
                        + "\nErrorCode --> " + "Connection response code is not 200");
            }

        } catch (ParseException | JAXBException | IOException ex) {
            LOGGER.error("RepositoryUID --> " + harRepoObj.getRepoUID()
                    + "\nActivity --> " + VerbType.LIST_RECORDS.value()
                    + "\nCallingURL --> " + desiredURL
                    + ex.getMessage(), ex);
            throw ex;
        }

        return true;

    }

    private boolean saveHarRecordDataIntoFileSystem(HarRepo harRepoObject) throws IOException {
        File recordDirectory;
        List<HarRecordData> harRecordDataList = harRecordDataDao.list(harRepoObject);
        if (harRecordDataList != null) {
            String currentRepositoryDataPath = repositoryDataPath + File.separator + harRepoObject.getRepoUID();
            for (HarRecordData tempHarRecordDataObj : harRecordDataList) {
                recordDirectory = new File(currentRepositoryDataPath + File.separator + tempHarRecordDataObj.getRecordId().getRecordId());
                if (!recordDirectory.exists()) {
                    recordDirectory.mkdirs();
                    getRecordServiceObject.saveHarRecordDataInFileSystem(tempHarRecordDataObj, recordDirectory.getPath());
                    LOGGER.info("record saved " + tempHarRecordDataObj.getRecordId().getRecordId());
                }
            }
        }
        for(File file : new File(repositoryDataPath + File.separator + harRepoObject.getRepoUID()).listFiles()){
            file.delete();
        }
        return true;
    }

}
