/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.RecordType;
import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarSetRecord;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.customised.MethodEnum;
import in.gov.nvli.harvester.dao.HarSetRecordDao;
import in.gov.nvli.harvester.dao.RepositoryDao;
import in.gov.nvli.harvester.services.GetRecordService;
import in.gov.nvli.harvester.services.ListRecordsService;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.net.HttpURLConnection;
import java.util.List;
import javax.servlet.ServletContext;
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

    private static int i = 0;
    private static final int INTERVAL = 60000;

    @Autowired
    HarSetRecordDao harSetRecordDao;

    @Autowired
    GetRecordService getRecordServiceObject;

    @Autowired
    RepositoryDao repositoryDao;

    List<HarSetRecord> harSetRecords;
    ServletContext servletContext;

    private HarRepo harRepoObj;
    private HttpURLConnection connection;

    public ListRecordsServiceImpl() {
    }

    @Override
    public boolean saveListRecords(HarRepo harRepoObj, String metadataPrefix, MethodEnum method, String adminEmail, boolean incrementalFlag) {
        this.harRepoObj = harRepoObj;
        String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS + CommonConstants.METADATA_PREFIX + metadataPrefix;
        return saveListRecordsRecusrsive(desiredURL, metadataPrefix, method, adminEmail, incrementalFlag);
    }

    @Override
    public boolean saveListRecords(String baseURL, String metadataPrefix, MethodEnum method, String adminEmail, boolean incrementalFlag) {
        this.harRepoObj = repositoryDao.getRepository(baseURL);
        String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS + CommonConstants.METADATA_PREFIX + metadataPrefix;
        return saveListRecordsRecusrsive(desiredURL, metadataPrefix, method, adminEmail, incrementalFlag);
    }

    private boolean saveListRecordsRecusrsive(String desiredURL, String metadataPrefix, MethodEnum method, String adminEmail, boolean incrementalFlag) {
        try {
            if (servletContext.getAttribute(desiredURL) != null) {
                Thread.sleep(INTERVAL);
            }
            connection = HttpURLConnectionUtil.getConnection(desiredURL, method, adminEmail);

            if (HttpURLConnectionUtil.isConnectionAlive(connection)) {
                String resumptionToken;
                String response = OAIResponseUtil.createResponseFromXML(connection);
                OAIPMHtype oAIPMHtypeObject = UnmarshalUtils.xmlToOaipmh(response);
                List<RecordType> recordTypeList = oAIPMHtypeObject.getListRecords().getRecord();
                getRecordServiceObject.saveGetRecordList(recordTypeList, harRepoObj, metadataPrefix);
                servletContext.removeAttribute(desiredURL);

                resumptionToken = oAIPMHtypeObject.getListRecords().getResumptionToken().getValue();
                if (resumptionToken != null && !resumptionToken.equals("") && !resumptionToken.isEmpty()) {
                    desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_RECORDS + CommonConstants.RESUMPTION_TOKEN + resumptionToken;
                    saveListRecordsRecusrsive(desiredURL, metadataPrefix, method, adminEmail, incrementalFlag);
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
                    saveListRecordsRecusrsive(desiredURL, metadataPrefix, method, adminEmail, incrementalFlag);
                }
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return true;

    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

}
