/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import com.sun.syndication.io.FeedException;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHerrorType;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHerrorcodeType;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.RecordType;
import in.gov.nvli.harvester.OAIPMH_beans.StatusType;
import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.beans.HarRecord;
import in.gov.nvli.harvester.beans.HarRecordData;
import in.gov.nvli.harvester.beans.HarRecordMetadataDc;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarRepoMetadata;
import in.gov.nvli.harvester.beans.HarSetRecord;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.constants.HarvesterLogConstants;
import in.gov.nvli.harvester.custom.exception.OAIPMHerrorTypeException;
import in.gov.nvli.harvester.custom.harvester_enum.HarRecordMetadataType;
import in.gov.nvli.harvester.custom.harvester_enum.MethodEnum;
import in.gov.nvli.harvester.custom.harvester_enum.RepoStatusEnum;
import in.gov.nvli.harvester.dao.HarRecordDao;
import in.gov.nvli.harvester.dao.HarRecordDataDao;
import in.gov.nvli.harvester.dao.HarRecordMetadataDcDao;
import in.gov.nvli.harvester.dao.HarRepoMetadataDao;
import in.gov.nvli.harvester.dao.HarSetRecordDao;
import in.gov.nvli.harvester.dao.RepositoryDao;
import in.gov.nvli.harvester.services.GetRecordService;
import in.gov.nvli.harvester.services.ListRecordsService;
import in.gov.nvli.harvester.utilities.DatesRelatedUtil;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.MarshalUtils;
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

    @Autowired
    private HarRepoMetadataDao harRepoMetadataDaoObj;

    public ListRecordsServiceImpl() {
    }

    @Override
    public boolean saveListRecords(HarRepoMetadata harRepoMetadataObj, MethodEnum method, String adminEmail) {
        if (listRecordsConstraintChecker(harRepoMetadataObj, false)) {
            HarRepo harRepoObj = harRepoMetadataObj.getRepoId();
            String metadataPrefix = harRepoMetadataObj.getMetadataTypeId().getMetadataPrefix();
            String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.METADATA_PREFIX + metadataPrefix;
            boolean result = true;
            try {
                listRecordsBeforeStart(harRepoMetadataObj, false);

                if (isListRecordsAvailable(harRepoObj, method, adminEmail, metadataPrefix)) {
                    result = saveListRecordsRecursive(harRepoMetadataObj, desiredURL, method, adminEmail, false);
                } else {
                    result = false;
                }

                if (result) {
                    listRecordsAfterEndOnSuccess(harRepoMetadataObj);
                } else {
                    listRecordsAfterEndOnError(harRepoMetadataObj, false);
                }
            } catch (ParseException ex) {
                listRecordsAfterEndOnError(harRepoMetadataObj, false);
                LOGGER.error("RepositoryUID --> " + harRepoMetadataObj.getRepoId().getRepoUID()
                        + "\nActivity --> " + VerbType.LIST_RECORDS.value() + ":" + harRepoMetadataObj.getMetadataTypeId().getMetadataPrefix()
                        + "\nCallingURL --> " + desiredURL
                        + "\nErrorCode --> Date Parsing"
                        + ex.getMessage(), ex);
                result = false;
            } catch (Exception ex) {
                listRecordsAfterEndOnError(harRepoMetadataObj, false);
                LOGGER.error("RepositoryUID --> " + harRepoMetadataObj.getRepoId().getRepoUID()
                        + "\nActivity --> " + VerbType.LIST_RECORDS.value() + ":" + harRepoMetadataObj.getMetadataTypeId().getMetadataPrefix()
                        + "\nCallingURL --> " + desiredURL
                        + "\nErrorCode --> " + ex.getMessage(), ex);
                result = false;
            }
            return result;
        } else {
            LOGGER.error("RepositoryUID --> " + harRepoMetadataObj.getRepoId().getRepoUID()
                    + "\nActivity --> " + VerbType.LIST_RECORDS.value() + ":" + harRepoMetadataObj.getMetadataTypeId().getMetadataPrefix().toUpperCase()
                    + "\nErrorCode --> Invalid Status : " + harRepoMetadataObj.getRepoId().getRepoStatusId().getRepoStatusName()
            );
            return false;
        }

    }

    @Override
    public boolean saveListRecords(String baseURL, HarRecordMetadataType harRecordMetadataTypeObj, MethodEnum method, String adminEmail) {
        HarRepo harRepoObj = repositoryDao.getRepository(baseURL);
        HarRepoMetadata harRepoMetadataObj = harRepoMetadataDaoObj.get(harRepoObj.getRepoId(), harRecordMetadataTypeObj);
        return saveListRecords(harRepoMetadataObj, method, adminEmail);
    }

    @Override
    public boolean saveOrUpdateListRecords(HarRepoMetadata harRepoMetadataObj, MethodEnum method, String adminEmail) throws OAIPMHerrorTypeException, ParseException, JAXBException, IOException {
        if (listRecordsConstraintChecker(harRepoMetadataObj, true)) {
            HarRepo harRepoObj = harRepoMetadataObj.getRepoId();
            String metadataPrefix = harRepoMetadataObj.getMetadataTypeId().getMetadataPrefix();
            String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.METADATA_PREFIX + metadataPrefix;
            boolean result = true;
            short listRecordsPreviousStatus;

            try {
                listRecordsPreviousStatus = harRepoMetadataObj.getHarvestStatus().getRepoStatusId();

                listRecordsBeforeStart(harRepoMetadataObj, true);

                if (isListRecordsAvailable(harRepoObj, method, adminEmail, metadataPrefix)) {
                    if (listRecordsPreviousStatus == RepoStatusEnum.HARVEST_COMPLETE.getId()) {
                        result = saveOrUpdateListRecordsViaFromTime(harRepoMetadataObj, MethodEnum.GET, adminEmail, true);
                    } else if (listRecordsPreviousStatus == RepoStatusEnum.HARVEST_PROCESSING_ERROR.getId() || listRecordsPreviousStatus == RepoStatusEnum.INCREMENT_HARVEST_PROCESSING_ERROR.getId()) {
                        result = saveOrUpdateListRecordsViaResumptionToken(harRepoMetadataObj, MethodEnum.GET, adminEmail, true);
                    } else {
                        result = saveListRecordsRecursive(harRepoMetadataObj, desiredURL, method, adminEmail, true);
                    }
                } else {
                    result = false;
                }

                if (result) {
                    listRecordsAfterEndOnSuccess(harRepoMetadataObj);
                } else {
                    listRecordsAfterEndOnError(harRepoMetadataObj, true);
                }
            } catch (ParseException ex) {
                listRecordsAfterEndOnError(harRepoMetadataObj, false);
                LOGGER.error("RepositoryUID --> " + harRepoMetadataObj.getRepoId().getRepoUID()
                        + "\nActivity --> " + VerbType.LIST_RECORDS.value() + ":" + harRepoMetadataObj.getMetadataTypeId().getMetadataPrefix()
                        + "\nCallingURL --> " + desiredURL
                        + "\nErrorCode --> Date Parsing"
                        + ex.getMessage(), ex);
                result = false;
            } catch (Exception ex) {
                listRecordsAfterEndOnError(harRepoMetadataObj, false);
                LOGGER.error("RepositoryUID --> " + harRepoMetadataObj.getRepoId().getRepoUID()
                        + "\nActivity --> " + VerbType.LIST_RECORDS.value() + ":" + harRepoMetadataObj.getMetadataTypeId().getMetadataPrefix()
                        + "\nCallingURL --> " + desiredURL
                        + "\nErrorCode --> " + ex.getMessage(), ex);
                result = false;
            }
            return result;
        } else {
            LOGGER.error("RepositoryUID --> " + harRepoMetadataObj.getRepoId().getRepoUID()
                    + "\nActivity --> " + VerbType.LIST_RECORDS.value() + ":" + harRepoMetadataObj.getMetadataTypeId().getMetadataPrefix().toUpperCase()
                    + "\nErrorCode --> Invalid Status : " + harRepoMetadataObj.getRepoId().getRepoStatusId().getRepoStatusName()
            );
            return false;
        }

    }

    @Override
    public boolean saveOrUpdateListRecords(String baseURL, HarRecordMetadataType harRecordMetadataTypeObj, MethodEnum method, String adminEmail) throws OAIPMHerrorTypeException, ParseException, JAXBException, IOException {
        HarRepo harRepoObj = repositoryDao.getRepository(baseURL);
        HarRepoMetadata harRepoMetadataObj = harRepoMetadataDaoObj.get(harRepoObj.getRepoId(), harRecordMetadataTypeObj);
        return saveOrUpdateListRecords(harRepoMetadataObj, method, adminEmail);
    }

    private boolean saveListRecordsRecursive(HarRepoMetadata harRepoMetadataObj, String desiredURL, MethodEnum method, String adminEmail, boolean incrementalFlag) {
        HarRepo harRepoObj = null;
        String metadataPrefix = null;
        HttpURLConnection connection = null;

        try {
            harRepoObj = harRepoMetadataObj.getRepoId();
            metadataPrefix = harRepoMetadataObj.getMetadataTypeId().getMetadataPrefix();

            connection = HttpURLConnectionUtil.getConnection(desiredURL, method, adminEmail);

            if (HttpURLConnectionUtil.isConnectionAlive(connection)) {
                String resumptionToken;
                List<HarRecord> harRecordList = new ArrayList<>();
                List<HarSetRecord> harSetRecordList = new ArrayList<>();
                List<HarRecordMetadataDc> harRecordMetadataDcList = new ArrayList<>();
                HarRecord harRecordObj;
                List<HarSetRecord> tempHarSetRecordList;
                HarRecordMetadataDc harRecordMetadataDcObj;

                String response = OAIResponseUtil.createResponseFromXML(connection);
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
                                        harRepoMetadataObj.setResumptionTokenListRecords(resumptionToken);
                                        harRepoMetadataDaoObj.merge(harRepoMetadataObj);
                                        desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.RESUMPTION_TOKEN + resumptionToken;
                                        return saveListRecordsRecursive(harRepoMetadataObj, desiredURL, method, adminEmail, incrementalFlag);
                                    }
                                }
                            } else {
                                throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                                        + "\nActivity --> " + VerbType.LIST_RECORDS.value()
                                        + "\nCallingURL --> " + desiredURL
                                        + "\nErrorCode --> " + HarvesterLogConstants.NO_RECORDS_FOUND);
                            }
                        } else {
                            throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                                    + "\nActivity --> " + VerbType.LIST_RECORDS.value()
                                    + "\nCallingURL --> " + desiredURL
                                    + "\nErrorCode --> " + HarvesterLogConstants.NO_LIST_RECORDS_FOUND);
                        }
                    } else {
                        StringBuilder errorMessages = new StringBuilder();
                        for (OAIPMHerrorType tempOAIPMHerrorType : oAIPMHtypeObject.getError()) {
                            errorMessages.append(tempOAIPMHerrorType.getValue())
                                    .append("[")
                                    .append(tempOAIPMHerrorType.getCode())
                                    .append("]");
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
                            + "\nErrorCode --> " + HarvesterLogConstants.NOT_OAI_PMH_COMPATIBLE_RESPONSE);
                }

            } else {
                throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                        + "\nActivity --> " + VerbType.LIST_RECORDS.value()
                        + "\nCallingURL --> " + desiredURL
                        + "\nErrorCode --> " + HarvesterLogConstants.RESPONSE_CODE_IS_NOT_200);
            }

        } catch (ParseException | JAXBException | IOException | OAIPMHerrorTypeException ex) {
            LOGGER.error("RepositoryUID --> " + harRepoObj.getRepoUID()
                    + "\nActivity --> " + VerbType.LIST_RECORDS.value()
                    + "\nCallingURL --> " + desiredURL
                    + ex.getMessage(), ex);
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
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
        if (isListRecordsAvailable(harRepoObj, method, adminEmail, HarRecordMetadataType.ORE.value())) {
            if (saveListHarRecordDataRecursive(harRepoObj, desiredURL, method, adminEmail, false)) {
                return saveHarRecordDataIntoFileSystem(harRepoObj);
            }
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
        String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.METADATA_PREFIX + HarRecordMetadataType.ORE.value();
        if (isListRecordsAvailable(harRepoObj, method, adminEmail, HarRecordMetadataType.ORE.value())) {
            if (saveListHarRecordDataRecursive(harRepoObj, desiredURL, method, adminEmail, true)) {
                return saveHarRecordDataIntoFileSystem(harRepoObj);
            }
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
                                    harRecordDataObject = getRecordServiceObject.getHarRecordDataByRecordType(recordTypeObj, HarRecordMetadataType.OAI_DC.value(), harRepoObj, incrementalFlag);
                                    if (harRecordDataObject != null) {
                                        harRecordDataList.add(harRecordDataObject);
                                    }
                                }
                                if (incrementalFlag) {
                                    if (!harRecordDataList.isEmpty()) {
                                        harRecordDataDao.saveOrUpdateHarRecordDataList(harRecordDataList);
                                        LOGGER.info(harRepoObj.getRepoUID() + ":" + harRecordDataList.size() + " Records updated");
                                    }
                                } else if (!harRecordDataList.isEmpty()) {
                                    harRecordDataDao.saveList(harRecordDataList);
                                    LOGGER.info(harRepoObj.getRepoUID() + ":" + harRecordDataList.size() + " Records saved");
                                }

                                resumptionToken = oAIPMHtypeObject.getListRecords().getResumptionToken().getValue();
                                if (resumptionToken != null && !resumptionToken.equals("") && !resumptionToken.isEmpty()) {
                                    desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.RESUMPTION_TOKEN + resumptionToken;
                                    saveListHarRecordDataRecursive(harRepoObj, desiredURL, method, adminEmail, incrementalFlag);
                                }
                            } else {
                                throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                                        + "\nActivity --> " + HarvesterLogConstants.LIST_RECORDS_ORE
                                        + "\nCallingURL --> " + desiredURL
                                        + "\nErrorCode --> " + HarvesterLogConstants.NO_RECORDS_FOUND);
                            }
                        } else {
                            throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                                    + "\nActivity --> " + HarvesterLogConstants.LIST_RECORDS_ORE
                                    + "\nCallingURL --> " + desiredURL
                                    + "\nErrorCode --> " + HarvesterLogConstants.NO_LIST_RECORDS_FOUND);
                        }
                    } else {
                        StringBuilder errorMessages = new StringBuilder();
                        for (OAIPMHerrorType tempOAIPMHerrorType : oAIPMHtypeObject.getError()) {
                            errorMessages.append(tempOAIPMHerrorType.getValue())
                                    .append("[")
                                    .append(tempOAIPMHerrorType.getCode())
                                    .append("]");
                        }
                        throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                                + "\nActivity --> " + HarvesterLogConstants.LIST_RECORDS_ORE
                                + "\nCallingURL --> " + desiredURL
                                + "\nErrorCode --> " + errorMessages);
                    }
                } else {
                    throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                            + "\nActivity --> " + HarvesterLogConstants.LIST_RECORDS_ORE
                            + "\nCallingURL --> " + desiredURL
                            + "\nErrorCode --> " + HarvesterLogConstants.NOT_OAI_PMH_COMPATIBLE_RESPONSE);
                }

            } else {
                if (connection != null) {
                    connection.disconnect();
                }
                throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                        + "\nActivity --> ListRecords-ORE"
                        + "\nCallingURL --> " + desiredURL
                        + "\nErrorCode --> " + HarvesterLogConstants.RESPONSE_CODE_IS_NOT_200);
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
                recordDirectory = new File(currentRepositoryDataPath + File.separator + tempHarRecordDataObj.getRecordId().getRecordUid());
                if (!recordDirectory.exists()) {
                    recordDirectory.mkdirs();
                    getRecordServiceObject.saveHarRecordDataInFileSystem(tempHarRecordDataObj, recordDirectory.getPath());
                    LOGGER.info("RepositoryUID --> " + harRepoObject.getRepoUID() + "\nrecord saved " + tempHarRecordDataObj.getRecordId().getRecordUid());
                }
            }
        }
        for (File file : new File(repositoryDataPath + File.separator + harRepoObject.getRepoUID()).listFiles()) {
            file.delete();
        }
        return true;
    }

    private boolean saveOrUpdateListRecordsViaResumptionToken(HarRepoMetadata harRepoMetadataObj, MethodEnum method, String adminEmail, boolean saveInDBFlag) {
        String desiredURL;
        HarRepo harRepoObj = harRepoMetadataObj.getRepoId();
        String metadataPrefix = harRepoMetadataObj.getMetadataTypeId().getMetadataPrefix();

        if (isListRecordsResumptionTokenValid(harRepoMetadataObj, method, adminEmail)) {
            LOGGER.info(harRepoObj.getRepoUID() + " --> Saved Resumption token is valid, List Records - Resuming");
            desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.RESUMPTION_TOKEN + harRepoMetadataObj.getResumptionTokenListRecords();
        } else {
            LOGGER.error(harRepoObj.getRepoUID() + " --> Saved Resumption token is invalid, List Records - Staring from beginning");
            desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.METADATA_PREFIX + metadataPrefix;
        }

        if (saveInDBFlag) {
            return saveListRecordsRecursive(harRepoMetadataObj, desiredURL, method, adminEmail, true);
        } else {
            return saveListRecordsXMLRecursive(harRepoMetadataObj, desiredURL, method, adminEmail, true);
        }

    }

    private boolean isListRecordsResumptionTokenValid(HarRepoMetadata harRepoMetadataObj, MethodEnum method, String adminEmail) {
        HarRepo harRepoObj = harRepoMetadataObj.getRepoId();
        String resumptionToken = harRepoMetadataObj.getResumptionTokenListRecords();

        if (resumptionToken != null) {
            String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.RESUMPTION_TOKEN + resumptionToken;
            return isURLValid(harRepoObj, desiredURL, method, adminEmail);
        } else {
            return false;
        }

    }

    @Override
    public boolean isListRecordsAvailable(HarRepo harRepoObj, MethodEnum method, String adminEmail, String metadataPrefix) {
        String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.METADATA_PREFIX + metadataPrefix;
        return isURLValid(harRepoObj, desiredURL, method, adminEmail);

    }

    private boolean isURLValid(HarRepo harRepoObj, String desiredURL, MethodEnum method, String adminEmail) {
        try {
            HttpURLConnection connection = HttpURLConnectionUtil.getConnection(desiredURL, method, adminEmail);

            if (HttpURLConnectionUtil.isConnectionAlive(connection)) {
                String response = OAIResponseUtil.createResponseFromXML(connection);
                OAIPMHtype oAIPMHtypeObject = UnmarshalUtils.xmlToOaipmh(response);
                if (oAIPMHtypeObject != null) {
                    if (oAIPMHtypeObject.getError().isEmpty()) {
                        return true;
                    } else {
                        StringBuilder errorMessages = new StringBuilder();
                        for (OAIPMHerrorType tempOAIPMHerrorType : oAIPMHtypeObject.getError()) {
                            errorMessages.append(tempOAIPMHerrorType.getValue())
                                    .append("[")
                                    .append(tempOAIPMHerrorType.getCode())
                                    .append("]");
                        }
                        LOGGER.error("RepositoryUID --> " + harRepoObj.getRepoUID()
                                + "\nActivity --> " + HarvesterLogConstants.URL_CHECK
                                + "\nCallingURL --> " + desiredURL
                                + "\nErrorCode --> " + errorMessages);
                        return false;
                    }
                } else {
                    LOGGER.error("RepositoryUID --> " + harRepoObj.getRepoUID()
                            + "\nActivity --> " + HarvesterLogConstants.URL_CHECK
                            + "\nCallingURL --> " + desiredURL
                            + "\nErrorCode --> " + HarvesterLogConstants.NOT_OAI_PMH_COMPATIBLE_RESPONSE);
                    return false;
                }

            } else {
                if (connection != null) {
                    connection.disconnect();
                }
                LOGGER.error("RepositoryUID --> " + harRepoObj.getRepoUID()
                        + "\nActivity --> " + HarvesterLogConstants.LIST_RECORDS_RESUMPTION_TOKEN_CHECK
                        + "\nCallingURL --> " + desiredURL
                        + "\nErrorCode --> " + HarvesterLogConstants.RESPONSE_CODE_IS_NOT_200);
                return false;
            }

        } catch (JAXBException | IOException ex) {
            LOGGER.error("RepositoryUID --> " + harRepoObj.getRepoUID()
                    + "\nActivity --> " + VerbType.LIST_RECORDS.value()
                    + "\nCallingURL --> " + desiredURL
                    + ex.getMessage(), ex);
            return false;
        }

    }

    private boolean saveOrUpdateListRecordsViaFromTime(HarRepoMetadata harRepoMetadataObj, MethodEnum method, String adminEmail, boolean saveInDBFlag) {
        HarRepo harRepoObj = harRepoMetadataObj.getRepoId();
        String metadataPrefix = harRepoMetadataObj.getMetadataTypeId().getMetadataPrefix();
        HttpURLConnection connection = null;
        String desiredURL = null;

        try {
            LOGGER.info(harRepoObj.getRepoUID() + " --> List Records : " + metadataPrefix + " - Resuming - From time - " + DatesRelatedUtil.getISOFormat(harRepoObj.getRepoLastSyncDate()));

            desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.METADATA_PREFIX + metadataPrefix + CommonConstants.FROM + DatesRelatedUtil.getISOFormat(harRepoObj.getRepoLastSyncDate());
            connection = HttpURLConnectionUtil.getConnection(desiredURL, method, adminEmail);

            if (HttpURLConnectionUtil.isConnectionAlive(connection)) {
                String response = OAIResponseUtil.createResponseFromXML(connection);
                OAIPMHtype oAIPMHtypeObject = UnmarshalUtils.xmlToOaipmh(response);
                if (oAIPMHtypeObject != null) {
                    if (oAIPMHtypeObject.getError().isEmpty()) {
                        if (saveInDBFlag) {
                            return saveListRecordsRecursive(harRepoMetadataObj, desiredURL, method, adminEmail, true);
                        } else {
                            return saveListRecordsXMLRecursive(harRepoMetadataObj, desiredURL, method, adminEmail, true);
                        }
                    } else if (oAIPMHtypeObject.getError().size() == 1) {
                        OAIPMHerrorType tempOAIPMHerrorType = oAIPMHtypeObject.getError().get(0);
                        if (!(tempOAIPMHerrorType.getCode() == OAIPMHerrorcodeType.NO_RECORDS_MATCH)) {
                            throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                                    + "\nActivity --> " + HarvesterLogConstants.LIST_RECORDS_FROM_TIME_CHECK
                                    + "\nCallingURL --> " + desiredURL
                                    + "\nErrorCode --> " + tempOAIPMHerrorType.getValue() + "(" + tempOAIPMHerrorType.getCode() + "),");
                        } else {
                            LOGGER.info(harRepoObj.getRepoUID() + " --> List Records " + metadataPrefix + " - No Records have been added From time " + DatesRelatedUtil.getISOFormat(harRepoObj.getRepoLastSyncDate()));
                            return true;
                        }
                    } else {
                        StringBuilder errorMessages = new StringBuilder();
                        for (OAIPMHerrorType tempOAIPMHerrorType : oAIPMHtypeObject.getError()) {
                            errorMessages.append(tempOAIPMHerrorType.getValue())
                                    .append("[")
                                    .append(tempOAIPMHerrorType.getCode())
                                    .append("]");
                        }
                        throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                                + "\nActivity --> " + HarvesterLogConstants.LIST_RECORDS_FROM_TIME_CHECK
                                + "\nCallingURL --> " + desiredURL
                                + "\nErrorCode --> " + errorMessages.toString());
                    }

                } else {
                    throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                            + "\nActivity --> " + HarvesterLogConstants.LIST_RECORDS_FROM_TIME_CHECK
                            + "\nCallingURL --> " + desiredURL
                            + "\nErrorCode --> " + HarvesterLogConstants.NOT_OAI_PMH_COMPATIBLE_RESPONSE);
                }

            } else {

                throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                        + "\nActivity --> " + HarvesterLogConstants.LIST_RECORDS_FROM_TIME_CHECK
                        + "\nCallingURL --> " + desiredURL
                        + "\nErrorCode --> " + HarvesterLogConstants.RESPONSE_CODE_IS_NOT_200);
            }

        } catch (JAXBException | IOException | OAIPMHerrorTypeException | ParseException ex) {
            LOGGER.error("RepositoryUID --> " + harRepoObj.getRepoUID()
                    + "\nActivity --> " + VerbType.LIST_RECORDS.value()
                    + "\nCallingURL --> " + desiredURL
                    + ex.getMessage(), ex);
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @Override
    public boolean saveListRecordsXML(HarRepoMetadata harRepoMetadataObj, MethodEnum method, String adminEmail, boolean saveDataInFileSystem) {
        if (listRecordsConstraintChecker(harRepoMetadataObj, false)) {
            HarRepo harRepoObj = harRepoMetadataObj.getRepoId();
            HarRecordMetadataType harRecordMetadataType = HarRecordMetadataType.valueOf(harRepoMetadataObj.getMetadataTypeId().getMetadataPrefix().toUpperCase());
            String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.METADATA_PREFIX + harRecordMetadataType.value();
            boolean result;

            try {
                listRecordsBeforeStart(harRepoMetadataObj, false);

                if (isListRecordsAvailable(harRepoObj, method, adminEmail, harRecordMetadataType.value())) {
                    if (saveListRecordsXMLRecursive(harRepoMetadataObj, desiredURL, method, adminEmail, false)) {
                        result = true;
                        if (saveDataInFileSystem
                                && (harRecordMetadataType == HarRecordMetadataType.METS || harRecordMetadataType == HarRecordMetadataType.ORE)) {
                            if (saveHarRecordDataInFileSystem(harRepoObj, harRecordMetadataType)) {
                                result = true;
                            } else {
                                result = false;
                            }
                        }
                    } else {
                        result = false;
                    }
                } else {
                    result = false;
                }

                if (result) {
                    listRecordsAfterEndOnSuccess(harRepoMetadataObj);
                } else {
                    listRecordsAfterEndOnError(harRepoMetadataObj, false);
                }

            } catch (ParseException ex) {
                listRecordsAfterEndOnError(harRepoMetadataObj, false);
                LOGGER.error("RepositoryUID --> " + harRepoMetadataObj.getRepoId().getRepoUID()
                        + "\nActivity --> " + VerbType.LIST_RECORDS.value() + ":" + harRepoMetadataObj.getMetadataTypeId().getMetadataPrefix()
                        + "\nCallingURL --> " + desiredURL
                        + "\nErrorCode --> Date Parsing"
                        + ex.getMessage(), ex);
                result = false;
            } catch (Exception ex) {
                listRecordsAfterEndOnError(harRepoMetadataObj, false);
                LOGGER.error("RepositoryUID --> " + harRepoMetadataObj.getRepoId().getRepoUID()
                        + "\nActivity --> " + VerbType.LIST_RECORDS.value() + ":" + harRepoMetadataObj.getMetadataTypeId().getMetadataPrefix()
                        + "\nCallingURL --> " + desiredURL
                        + "\nErrorCode --> " + ex.getMessage(), ex);
                result = false;
            }
            return result;
        } else {
            LOGGER.error("RepositoryUID --> " + harRepoMetadataObj.getRepoId().getRepoUID()
                    + "\nActivity --> " + VerbType.LIST_RECORDS.value() + ":" + harRepoMetadataObj.getMetadataTypeId().getMetadataPrefix().toUpperCase()
                    + "\nErrorCode --> Invalid Status : " + harRepoMetadataObj.getRepoId().getRepoStatusId().getRepoStatusName()
            );
            return false;
        }

    }

    @Override
    public boolean saveOrUpdateListRecordsXML(HarRepoMetadata harRepoMetadataObj, MethodEnum method, String adminEmail, boolean saveDataInFileSystem) {
        if (listRecordsConstraintChecker(harRepoMetadataObj, true)) {
            HarRepo harRepoObj = harRepoMetadataObj.getRepoId();
            HarRecordMetadataType harRecordMetadataType = HarRecordMetadataType.valueOf(harRepoMetadataObj.getMetadataTypeId().getMetadataPrefix().toUpperCase());
            String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.METADATA_PREFIX + harRecordMetadataType.value();
            boolean result;
            short listRecordsPreviousStatus;

            try {
                listRecordsPreviousStatus = harRepoMetadataObj.getHarvestStatus().getRepoStatusId();

                listRecordsBeforeStart(harRepoMetadataObj, true);

                if (isListRecordsAvailable(harRepoObj, method, adminEmail, harRecordMetadataType.value())) {
                    if (listRecordsPreviousStatus == RepoStatusEnum.HARVEST_COMPLETE.getId()) {
                        result = saveOrUpdateListRecordsViaFromTime(harRepoMetadataObj, MethodEnum.GET, adminEmail, false);
                    } else if (listRecordsPreviousStatus == RepoStatusEnum.HARVEST_PROCESSING_ERROR.getId() || listRecordsPreviousStatus == RepoStatusEnum.INCREMENT_HARVEST_PROCESSING_ERROR.getId()) {
                        result = saveOrUpdateListRecordsViaResumptionToken(harRepoMetadataObj, MethodEnum.GET, adminEmail, false);
                    } else if (saveListRecordsXMLRecursive(harRepoMetadataObj, desiredURL, method, adminEmail, true)) {
                        result = true;
                        if (saveDataInFileSystem) {
                            if (saveHarRecordDataInFileSystem(harRepoObj, harRecordMetadataType)) {
                                result = true;
                            } else {
                                result = false;
                            }
                        }
                    } else {
                        result = false;
                    }
                } else {
                    result = false;
                }

                if (result) {
                    listRecordsAfterEndOnSuccess(harRepoMetadataObj);
                } else {
                    listRecordsAfterEndOnError(harRepoMetadataObj, false);
                }

            } catch (ParseException ex) {
                listRecordsAfterEndOnError(harRepoMetadataObj, false);
                LOGGER.error("RepositoryUID --> " + harRepoMetadataObj.getRepoId().getRepoUID()
                        + "\nActivity --> " + VerbType.LIST_RECORDS.value() + ":" + harRepoMetadataObj.getMetadataTypeId().getMetadataPrefix()
                        + "\nCallingURL --> " + desiredURL
                        + "\nErrorCode --> Date Parsing"
                        + ex.getMessage(), ex);
                result = false;
            } catch (Exception ex) {
                listRecordsAfterEndOnError(harRepoMetadataObj, false);
                LOGGER.error("RepositoryUID --> " + harRepoMetadataObj.getRepoId().getRepoUID()
                        + "\nActivity --> " + VerbType.LIST_RECORDS.value() + ":" + harRepoMetadataObj.getMetadataTypeId().getMetadataPrefix()
                        + "\nCallingURL --> " + desiredURL
                        + "\nErrorCode --> " + ex.getMessage(), ex);
                result = false;
            }
            return result;
        } else {
            LOGGER.error("RepositoryUID --> " + harRepoMetadataObj.getRepoId().getRepoUID()
                    + "\nActivity --> " + VerbType.LIST_RECORDS.value() + ":" + harRepoMetadataObj.getMetadataTypeId().getMetadataPrefix().toUpperCase()
                    + "\nErrorCode --> Invalid Status : " + harRepoMetadataObj.getRepoId().getRepoStatusId().getRepoStatusName()
            );
            return false;
        }
    }

    private boolean saveListRecordsXMLRecursive(HarRepoMetadata harRepoMetadataObj, String desiredURL, MethodEnum method, String adminEmail, boolean incrementalFlag) {
        HttpURLConnection connection = null;
        HarRepo harRepoObj = null;
        HarRecordMetadataType harRecordMetadataType = null;

        try {
            connection = HttpURLConnectionUtil.getConnection(desiredURL, method, adminEmail);
            harRepoObj = harRepoMetadataObj.getRepoId();
            harRecordMetadataType = HarRecordMetadataType.valueOf(harRepoMetadataObj.getMetadataTypeId().getMetadataPrefix().toUpperCase());

            if (HttpURLConnectionUtil.isConnectionAlive(connection)) {
                String resumptionToken;
                HarRecord harRecordObj;

                String response = OAIResponseUtil.createResponseFromXML(connection);
                OAIPMHtype oAIPMHtypeObject = UnmarshalUtils.xmlToOaipmh(response);
                if (oAIPMHtypeObject != null) {
                    if (oAIPMHtypeObject.getError().isEmpty()) {
                        if (oAIPMHtypeObject.getListRecords() != null) {
                            List<RecordType> recordTypeList = oAIPMHtypeObject.getListRecords().getRecord();
                            if (recordTypeList != null) {
                                for (RecordType recordTypeObj : recordTypeList) {
                                    if (incrementalFlag) {
                                        harRecordObj = getRecordServiceObject.getHarRecordByRecordType(recordTypeObj, harRepoObj, true);
                                    } else {
                                        harRecordObj = getRecordServiceObject.getHarRecordByRecordType(recordTypeObj, harRepoObj, false);
                                    }

                                    if (harRecordObj != null && recordTypeObj.getHeader().getStatus() != StatusType.DELETED && recordTypeObj.getMetadata() != null) {
                                        MarshalUtils.oaipmhToXML(recordTypeObj.getMetadata(), harRecordObj, harRecordMetadataType, repositoryDataPath);
                                    }
                                }
                                LOGGER.info(harRepoObj.getRepoUID() + ":" + recordTypeList.size() + " " + harRecordMetadataType.value() + " XML saved");
                                resumptionToken = oAIPMHtypeObject.getListRecords().getResumptionToken().getValue();
                                if (resumptionToken != null && !resumptionToken.equals("") && !resumptionToken.isEmpty()) {
                                    desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.RESUMPTION_TOKEN + resumptionToken;

                                    harRepoMetadataObj.setResumptionTokenListRecords(resumptionToken);
                                    harRepoMetadataDaoObj.merge(harRepoMetadataObj);

                                    return saveListRecordsXMLRecursive(harRepoMetadataObj, desiredURL, method, adminEmail, incrementalFlag);
                                }
                            } else {
                                throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                                        + "\nActivity --> " + VerbType.LIST_RECORDS.value() + ":" + harRecordMetadataType.value()
                                        + "\nCallingURL --> " + desiredURL
                                        + "\nErrorCode --> " + HarvesterLogConstants.NO_RECORDS_FOUND);
                            }
                        } else {
                            throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                                    + "\nActivity --> " + VerbType.LIST_RECORDS.value() + ":" + harRecordMetadataType.value()
                                    + "\nCallingURL --> " + desiredURL
                                    + "\nErrorCode --> " + HarvesterLogConstants.NO_LIST_RECORDS_FOUND);
                        }
                    } else {
                        StringBuilder errorMessages = new StringBuilder();
                        for (OAIPMHerrorType tempOAIPMHerrorType : oAIPMHtypeObject.getError()) {
                            errorMessages.append(tempOAIPMHerrorType.getValue())
                                    .append("[")
                                    .append(tempOAIPMHerrorType.getCode())
                                    .append("]");
                        }
                        throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                                + "\nActivity --> " + VerbType.LIST_RECORDS.value() + ":" + harRecordMetadataType.value()
                                + "\nCallingURL --> " + desiredURL
                                + "\nErrorCode --> " + errorMessages);
                    }
                } else {
                    throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                            + "\nActivity --> " + VerbType.LIST_RECORDS.value() + ":" + harRecordMetadataType.value()
                            + "\nCallingURL --> " + desiredURL
                            + "\nErrorCode --> " + HarvesterLogConstants.NOT_OAI_PMH_COMPATIBLE_RESPONSE);
                }

            } else {

                throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                        + "\nActivity --> " + VerbType.LIST_RECORDS.value() + ":" + harRecordMetadataType.value()
                        + "\nCallingURL --> " + desiredURL
                        + "\nErrorCode --> " + HarvesterLogConstants.RESPONSE_CODE_IS_NOT_200);
            }

        } catch (OAIPMHerrorTypeException | ParseException | JAXBException | IOException | TransformerException | IllegalArgumentException ex) {
            LOGGER.error("RepositoryUID --> " + harRepoObj.getRepoUID()
                    + "\nActivity --> " + VerbType.LIST_RECORDS.value()
                    + "\nCallingURL --> " + desiredURL
                    + ex.getMessage(), ex);
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return true;

    }

    @Override
    public boolean saveHarRecordDataInFileSystem(HarRepo harRepoObj, HarRecordMetadataType harRecordMetadataTypeObj) {
        return getRecordServiceObject.saveHarRecordDataInFileSystem(harRepoObj, harRecordMetadataTypeObj);
    }

    private void listRecordsBeforeStart(HarRepoMetadata harRepoMetadataObj, boolean incrementalFlag) throws ParseException {
        harRepoMetadataDaoObj.updateStartTime(harRepoMetadataObj, DatesRelatedUtil.getCurrentDateTimeInUTCFormat());
        if (incrementalFlag) {
            harRepoMetadataDaoObj.updateStatus(harRepoMetadataObj, RepoStatusEnum.INCREMENT_HARVEST_PROCESSING.getId());
        } else {
            harRepoMetadataDaoObj.updateStatus(harRepoMetadataObj, RepoStatusEnum.HARVEST_PROCESSING.getId());
        }

    }

    private void listRecordsAfterEndOnSuccess(HarRepoMetadata harRepoMetadataObj) throws ParseException {
        harRepoMetadataDaoObj.updateEndTime(harRepoMetadataObj, DatesRelatedUtil.getCurrentDateTimeInUTCFormat());
        harRepoMetadataDaoObj.updateStatus(harRepoMetadataObj, RepoStatusEnum.HARVEST_COMPLETE.getId());
        harRepoMetadataObj.setResumptionTokenListRecords(null);
        harRepoMetadataDaoObj.merge(harRepoMetadataObj);
    }

    private void listRecordsAfterEndOnError(HarRepoMetadata harRepoMetadataObj, boolean incrementalFlag) {
        if (incrementalFlag) {
            harRepoMetadataDaoObj.updateStatus(harRepoMetadataObj, RepoStatusEnum.INCREMENT_HARVEST_PROCESSING_ERROR.getId());
        } else {
            harRepoMetadataDaoObj.updateStatus(harRepoMetadataObj, RepoStatusEnum.HARVEST_PROCESSING_ERROR.getId());
        }

    }

    private boolean listRecordsConstraintChecker(HarRepoMetadata harRepoMetadataObj, boolean incrementalFlag)//0-firstTime,1-Incremental
    {
        switch (harRepoMetadataObj.getHarvestStatus().getRepoStatusId()) {
            case 1://not_active
                return false;
            case 2://active
                if (incrementalFlag) {
                    return false;
                } else {
                    return true;
                }
            case 3://harvest_processing
                return false;
            case 4://harvest_processing_error
                if (incrementalFlag) {
                    return true;
                } else {
                    return false;
                }
            case 5://harvest_complete
                if (incrementalFlag) {
                    return true;
                } else {
                    return false;
                }
            case 6://increment_harvest_processing
                return false;
            case 7://increment_harvest_processing_error
                if (incrementalFlag) {
                    return true;
                } else {
                    return false;
                }
            case 8://invalid_url
                return false;
            default:
                return false;

        }

    }
}
