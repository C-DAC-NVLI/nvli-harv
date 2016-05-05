/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.RecordType;
import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.beans.HarRecord;
import in.gov.nvli.harvester.beans.HarRecordMetadataDc;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarSetRecord;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.customised.MethodEnum;
import in.gov.nvli.harvester.dao.HarRecordDao;
import in.gov.nvli.harvester.dao.HarRecordMetadataDcDao;
import in.gov.nvli.harvester.dao.HarSetRecordDao;
import in.gov.nvli.harvester.dao.RepositoryDao;
import in.gov.nvli.harvester.services.GetRecordService;
import in.gov.nvli.harvester.services.ListRecordsService;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author richa
 */
@Service
public class ListRecordsServiceImpl implements ListRecordsService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ListRecordsServiceImpl.class);

    private static final int INTERVAL = 60000;

    @Autowired
    HarRecordDao harRecordDao;

    @Autowired
    HarSetRecordDao harSetRecordDao;

    @Autowired
    HarRecordMetadataDcDao harRecordMetadataDcDao;

    @Autowired
    GetRecordService getRecordServiceObject;

    @Autowired
    RepositoryDao repositoryDao;

    ServletContext servletContext;

    private HarRepo harRepoObj;
    private HttpURLConnection connection;

    List<HarRecord> harRecordList;
    List<HarSetRecord> harSetRecordList;
    List<HarRecordMetadataDc> harRecordMetadataDcList;

    public ListRecordsServiceImpl() {
    }

    @Override
    public boolean saveListRecords(HarRepo harRepoObj, String metadataPrefix, MethodEnum method, String adminEmail) {
        this.harRepoObj = harRepoObj;
        String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.METADATA_PREFIX + metadataPrefix;
        return saveListRecordsRecursive(desiredURL, metadataPrefix, method, adminEmail, false);
    }

    @Override
    public boolean saveListRecords(String baseURL, String metadataPrefix, MethodEnum method, String adminEmail) {
        this.harRepoObj = repositoryDao.getRepository(baseURL);
        String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.METADATA_PREFIX + metadataPrefix;
        return saveListRecordsRecursive(desiredURL, metadataPrefix, method, adminEmail, false);
    }

    @Override
    public boolean saveOrUpdateListRecords(HarRepo harRepoObj, String metadataPrefix, MethodEnum method, String adminEmail) {
        this.harRepoObj = harRepoObj;
        String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.METADATA_PREFIX + metadataPrefix;
        return saveListRecordsRecursive(desiredURL, metadataPrefix, method, adminEmail, true);
    }

    @Override
    public boolean saveOrUpdateListRecords(String baseURL, String metadataPrefix, MethodEnum method, String adminEmail) {
        this.harRepoObj = repositoryDao.getRepository(baseURL);
        String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.METADATA_PREFIX + metadataPrefix;
        return saveListRecordsRecursive(desiredURL, metadataPrefix, method, adminEmail, true);
    }

    private boolean saveListRecordsRecursive(String desiredURL, String metadataPrefix, MethodEnum method, String adminEmail, boolean incrementalFlag) {
        try {
            LOGGER.info("desired URL " + desiredURL);
            if (servletContext.getAttribute(desiredURL) != null) {
                Thread.sleep(INTERVAL);

            }
            connection = HttpURLConnectionUtil.getConnection(desiredURL, method, adminEmail);

            if (HttpURLConnectionUtil.isConnectionAlive(connection)) {
                String resumptionToken;
                harRecordList = new ArrayList<>();
                harSetRecordList = new ArrayList<>();
                harRecordMetadataDcList = new ArrayList<>();
                HarRecord harRecordObj;
                List<HarSetRecord> tempHarSetRecordList;
                HarRecordMetadataDc harRecordMetadataDcObj;

                String response = OAIResponseUtil.createResponseFromXML(connection);
                OAIPMHtype oAIPMHtypeObject = UnmarshalUtils.xmlToOaipmh(response);
                List<RecordType> recordTypeList = oAIPMHtypeObject.getListRecords().getRecord();
                for (RecordType recordTypeObj : recordTypeList) {
                    harRecordObj = getRecordServiceObject.getHarRecordByRecordType(recordTypeObj, metadataPrefix, harRepoObj);
                    tempHarSetRecordList = getRecordServiceObject.getHarSetRecordListByRecordType(recordTypeObj, harRecordObj);
                    harRecordMetadataDcObj = getRecordServiceObject.getHarRecordMetadataDcByRecordType(recordTypeObj, harRecordObj);

                    this.harRecordList.add(harRecordObj);
                    this.harSetRecordList.addAll(tempHarSetRecordList);
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
                        harRecordMetadataDcDao.saveOrUpdateList(harRecordMetadataDcList);
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

                resumptionToken = oAIPMHtypeObject.getListRecords().getResumptionToken().getValue();
                if (resumptionToken != null && !resumptionToken.equals("") && !resumptionToken.isEmpty()) {
                    desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS.value() + CommonConstants.RESUMPTION_TOKEN + resumptionToken;
                    saveListRecordsRecursive(desiredURL, metadataPrefix, method, adminEmail, incrementalFlag);
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
                    saveListRecordsRecursive(desiredURL, metadataPrefix, method, adminEmail, incrementalFlag);
                }
            }

        } catch (InterruptedException | ParseException | JAXBException | IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return false;
        }

        return true;

    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

}
