/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import com.sun.syndication.io.FeedException;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.RecordType;
import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.beans.HarRecord;
import in.gov.nvli.harvester.beans.HarRecordData;
import in.gov.nvli.harvester.beans.HarRecordMetadataDc;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarSetRecord;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.customised.MethodEnum;
import in.gov.nvli.harvester.dao.HarRecordDao;
import in.gov.nvli.harvester.dao.HarRecordDataDao;
import in.gov.nvli.harvester.dao.HarRecordMetadataDcDao;
import in.gov.nvli.harvester.dao.HarSetRecordDao;
import in.gov.nvli.harvester.dao.RepositoryDao;
import in.gov.nvli.harvester.services.GetRecordService;
import in.gov.nvli.harvester.services.ListRecordsService;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
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

    private static final int INTERVAL = 60000;
    
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
    private ServletContext servletContext;

    public ListRecordsServiceImpl() {
    }

    @Override
    public boolean saveListRecords(HarRepo harRepoObj, String metadataPrefix, MethodEnum method, String adminEmail) {
        String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.METADATA_PREFIX + metadataPrefix;
        return saveListRecordsRecursive(harRepoObj, desiredURL, metadataPrefix, method, adminEmail, false);
    }

    @Override
    public boolean saveListRecords(String baseURL, String metadataPrefix, MethodEnum method, String adminEmail) {
        HarRepo harRepoObj = repositoryDao.getRepository(baseURL);
        return saveListRecords(harRepoObj, metadataPrefix, method, adminEmail);
    }

    @Override
    public boolean saveOrUpdateListRecords(HarRepo harRepoObj, String metadataPrefix, MethodEnum method, String adminEmail) {
        String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.METADATA_PREFIX + metadataPrefix;
        return saveListRecordsRecursive(harRepoObj, desiredURL, metadataPrefix, method, adminEmail, true);
    }

    @Override
    public boolean saveOrUpdateListRecords(String baseURL, String metadataPrefix, MethodEnum method, String adminEmail) {
        HarRepo harRepoObj = repositoryDao.getRepository(baseURL);
        return saveOrUpdateListRecords(harRepoObj, metadataPrefix, method, adminEmail);
    }

    private boolean saveListRecordsRecursive(HarRepo harRepoObj, String desiredURL, String metadataPrefix, MethodEnum method, String adminEmail, boolean incrementalFlag) {
        try {
            LOGGER.info("desired URL " + desiredURL);
            if (servletContext.getAttribute(desiredURL) != null) {
                Thread.sleep(INTERVAL);

            }
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
                OAIPMHtype oAIPMHtypeObject = UnmarshalUtils.xmlToOaipmh(response);
                if (oAIPMHtypeObject != null && oAIPMHtypeObject.getListRecords() != null) {
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
                        }
                        servletContext.removeAttribute(desiredURL);
                        
                        if (oAIPMHtypeObject.getListRecords().getResumptionToken() != null) {
                            resumptionToken = oAIPMHtypeObject.getListRecords().getResumptionToken().getValue();
                            if (resumptionToken != null && !resumptionToken.equals("") && !resumptionToken.isEmpty()) {
                                desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.RESUMPTION_TOKEN + resumptionToken;
                                saveListRecordsRecursive(harRepoObj, desiredURL, metadataPrefix, method, adminEmail, incrementalFlag);
                            }
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }

            } else {
                int tmpCnt = 0;
                if (servletContext.getAttribute(desiredURL) == null) {
                    servletContext.setAttribute(desiredURL, 1);
                } else {
                    tmpCnt = (int) servletContext.getAttribute(desiredURL);
                    servletContext.setAttribute(desiredURL, (++tmpCnt));
                }
                if (((int) servletContext.getAttribute(desiredURL)) <= 3) {
                    saveListRecordsRecursive(harRepoObj, desiredURL, metadataPrefix, method, adminEmail, incrementalFlag);
                }
            }

        } catch (InterruptedException | ParseException | JAXBException | IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return false;
        }

        return true;

    }

    @Override
    public boolean saveListHarRecordData(String baseURL, MethodEnum method, String adminEmail) throws TransformerException, TransformerConfigurationException, IllegalArgumentException, FeedException, IOException {
        HarRepo harRepoObj = repositoryDao.getRepository(baseURL);
        return saveListHarRecordData(harRepoObj, method, adminEmail);
    }

    @Override
    public boolean saveListHarRecordData(HarRepo harRepoObj, MethodEnum method, String adminEmail) throws TransformerException, TransformerConfigurationException, IllegalArgumentException, FeedException, IOException {
        String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.METADATA_PREFIX + "ore";
        if (saveListHarRecordDataRecursive(harRepoObj, desiredURL, method, adminEmail, false)) {
            return saveHarRecordDataIntoFileSystem(harRepoObj);
        }
        return false;
    }

    private boolean saveListHarRecordDataRecursive(HarRepo harRepoObj, String desiredURL, MethodEnum method, String adminEmail, boolean incrementalFlag) throws TransformerException, TransformerConfigurationException, IllegalArgumentException, FeedException {
        try {
            LOGGER.info("desired URL " + desiredURL);
            if (servletContext.getAttribute(desiredURL) != null) {
                Thread.sleep(INTERVAL);

            }
            HttpURLConnection connection = HttpURLConnectionUtil.getConnection(desiredURL, method, adminEmail);

            if (HttpURLConnectionUtil.isConnectionAlive(connection)) {
                String resumptionToken;
                HarRecordData harRecordDataObject;

                String response = OAIResponseUtil.createResponseFromXML(connection);
                OAIPMHtype oAIPMHtypeObject = UnmarshalUtils.xmlToOaipmh(response);
                if (oAIPMHtypeObject != null && oAIPMHtypeObject.getListRecords() != null) {
                    List<RecordType> recordTypeList = oAIPMHtypeObject.getListRecords().getRecord();
                    if (recordTypeList != null) {
                        for (RecordType recordTypeObj : recordTypeList) {
                            harRecordDataObject = getRecordServiceObject.getHarRecordDataByRecordType(recordTypeObj);
                            if (harRecordDataObject != null) {
                                harRecordDataDao.createNew(harRecordDataObject);
                            }
                        }

                        servletContext.removeAttribute(desiredURL);

                        resumptionToken = oAIPMHtypeObject.getListRecords().getResumptionToken().getValue();
                        if (resumptionToken != null && !resumptionToken.equals("") && !resumptionToken.isEmpty()) {
                            desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.RESUMPTION_TOKEN + resumptionToken;
                            saveListHarRecordDataRecursive(harRepoObj, desiredURL, method, adminEmail, incrementalFlag);
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }

            } else {
                int tmpCnt = 0;
                if (servletContext.getAttribute(desiredURL) == null) {
                    servletContext.setAttribute(desiredURL, 1);
                } else {
                    tmpCnt = (int) servletContext.getAttribute(desiredURL);
                    servletContext.setAttribute(desiredURL, (++tmpCnt));
                }
                if (((int) servletContext.getAttribute(desiredURL)) <= 3) {
                    saveListHarRecordDataRecursive(harRepoObj, desiredURL, method, adminEmail, incrementalFlag);
                }
            }

        } catch (InterruptedException | ParseException | JAXBException | IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return false;
        }

        return true;

    }

    private boolean saveHarRecordDataIntoFileSystem(HarRepo harRepoObject) throws IOException {
        File recordDirectory;
        List<HarRecordData> harRecordDataList = harRecordDataDao.list(harRepoObject);
        if (harRecordDataList != null) {
            repositoryDataPath += File.separator+harRepoObject.getRepoUID();
            for (HarRecordData tempHarRecordDataObj : harRecordDataList) {
                recordDirectory = new File(repositoryDataPath + File.separator + tempHarRecordDataObj.getRecordId().getRecordId());
                if (!recordDirectory.exists()) {
                    recordDirectory.mkdirs();
                    getRecordServiceObject.saveHarRecordDataInFileSystem(tempHarRecordDataObj, recordDirectory.getPath());
                    LOGGER.info("record saved " + tempHarRecordDataObj.getRecordId().getRecordId());
                }
            }
        }
        return true;
    }

}
