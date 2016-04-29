/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.AboutType;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.RecordType;
import in.gov.nvli.harvester.OAIPMH_beans.ResumptionTokenType;
import in.gov.nvli.harvester.OAIPMH_beans.StatusType;
import in.gov.nvli.harvester.beans.HarMetadataType;
import in.gov.nvli.harvester.beans.HarMetadataTypeRepository;
import in.gov.nvli.harvester.beans.HarRecord;
import in.gov.nvli.harvester.beans.HarRecordMetadataDc;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarSet;
import in.gov.nvli.harvester.beans.HarSetRecord;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.dao.HarMetadataTypeDao;
import in.gov.nvli.harvester.dao.HarRecordDao;
import in.gov.nvli.harvester.dao.HarRecordMetadataDcDao;
import in.gov.nvli.harvester.dao.HarSetDao;
import in.gov.nvli.harvester.dao.HarSetRecordDao;
import in.gov.nvli.harvester.services.GetRecordService;
import in.gov.nvli.harvester.services.ListRecordsService;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


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
  private static int interval = 60000;

  private HttpURLConnection connection;
  @Autowired
  private GetRecordService getRecordService;

  @Autowired
  private HarRecordMetadataDcDao metadataDcDao;

  @Autowired
  private HarRecordDao recordDao;

  @Autowired
  private HarMetadataTypeDao metadataTypeDao;

  @Autowired
  private HarSetDao harSetDao;

  @Autowired
  HarSetRecordDao harSetRecordDao;

  ResumptionTokenType resumptionToken;

  HarRecord harRecord;
  HarRecordMetadataDc recordMetadataDc;
  List<HarRecord> harRecords;
  List<HarRecordMetadataDc> recordMetadataDcs;
  HarSetRecord harSetRecord;
  HarSet harSet;
  HarMetadataTypeRepository harMetadataTypeRepository;
  HarRepo harRepo;
  String metadataPrefix;
  List<HarSetRecord> harSetRecords;
  ServletContext servletContext;
  private boolean incrementalUpdateFlag;

  public ListRecordsServiceImpl() {
  }

  @Override
  public void getListRecord(String baseUrl) {
    System.out.println("url================" + baseUrl);
    System.setProperty("http.keepAlive", "true");

    try {
      if (servletContext.getAttribute(baseUrl) != null) {
        Thread.sleep(interval);
      }
      connection = HttpURLConnectionUtil.getConnection(baseUrl, "GET", "", "");

      int responseCode = connection.getResponseCode();
      System.out.println("response code " + responseCode);
      if (responseCode == 200) {

        String response = OAIResponseUtil.createResponseFromXML(connection);

        OAIPMHtype getRecordObj = UnmarshalUtils.xmlToOaipmh(response);

        List<RecordType> records = getRecordObj.getListRecords().getRecord();

        harRecords = new ArrayList<>();
        recordMetadataDcs = new ArrayList<>();
        harSetRecords = new ArrayList<>();
        HarMetadataType metadataType = metadataTypeDao.getMetadataTypeByMetadatPrefix(metadataPrefix);
        for (RecordType record : records) {

          harRecord = new HarRecord();
          harRecord.setRecordIdentifier(record.getHeader().getIdentifier());
          DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
          Date sourceDate = formatter.parse(record.getHeader().getDatestamp());
          harRecord.setRecordSoureDatestamp(sourceDate);
          harRecord.setMetadataTypeId(metadataType);
          List<AboutType> aboutTypes = record.getAbout();
          String temp = "";

          if (aboutTypes != null) {
            for (AboutType about : aboutTypes) {
              temp += about;
            }
          }
          harRecord.setRecordAbout(temp);
          harRecord.setRepoId(harRepo);

          if (record.getHeader().getStatus() != StatusType.DELETED) {
            harRecord.setRecordStatus(CommonConstants.RECORDNOTDELETED);
          } else {
            harRecord.setRecordStatus(CommonConstants.RECORDDELETED);
          }

          harRecords.add(harRecord);

          List<String> setSpecs = record.getHeader().getSetSpec();
          for (String setSpec : setSpecs) {
            harSet = harSetDao.getHarSet(setSpec);
            if (harSet != null) {
              harSetRecord = new HarSetRecord();
              harSetRecord.setSetId(harSet);
              harSetRecord.setRecordId(harRecord);
              harSetRecords.add(harSetRecord);
            }
          }

          recordMetadataDc = new HarRecordMetadataDc();
          recordMetadataDc.setRecordId(harRecord);
          if (record.getHeader().getStatus() != StatusType.DELETED) {
            getRecordService.getMetadataFromObj(record.getMetadata().getOaidc(), recordMetadataDc);
            recordMetadataDcs.add(recordMetadataDc);
          }

        }
        if (incrementalUpdateFlag) {
          if (!harRecords.isEmpty()) {
            recordDao.saveOrUpdateHarRecordList(harRecords);
          }
          if (!harSetRecords.isEmpty()) {
            harSetRecordDao.saveOrUpdateHarSetRecords(harSetRecords);
          }
          if (!recordMetadataDcs.isEmpty()) {
            metadataDcDao.saveOrUpdateList(recordMetadataDcs);
          }
        } else {
          if (!harRecords.isEmpty()) {
            recordDao.saveList(harRecords);
          }
          if (!harSetRecords.isEmpty()) {
            harSetRecordDao.saveList(harSetRecords);
          }
          if (!recordMetadataDcs.isEmpty()) {
            metadataDcDao.saveList(recordMetadataDcs);
          }
        }

        servletContext.removeAttribute(baseUrl);

        System.out.println("Saved======================== " + harRecords.size() + " metadata " + recordMetadataDcs.size());
        resumptionToken = getRecordObj.getListRecords().getResumptionToken();
        if (resumptionToken != null && resumptionToken.getValue() != null && resumptionToken.getValue() != "" && !resumptionToken.getValue().isEmpty()) {
          String urlSubtr[] = baseUrl.split("&");
          String requestUrl = urlSubtr[0] + "&resumptionToken=" + resumptionToken.getValue();
          getListRecord(requestUrl);
        }

      } else {
        int tmpCnt = 0;
        if (servletContext.getAttribute(baseUrl) == null) {
          servletContext.setAttribute(baseUrl, 1);
        } else {
          tmpCnt = (int) servletContext.getAttribute(baseUrl);
          servletContext.setAttribute(baseUrl, (++tmpCnt));
        }
        if (((int) servletContext.getAttribute(baseUrl)) <= 3) {
          getListRecord(baseUrl);
        }
      }
    } catch (Exception ex) {
      LOGGER.error(ex.getMessage(), ex);
      ex.printStackTrace();
    }

  }

  public void setHarRepo(HarRepo harRepo) {
    this.harRepo = harRepo;
  }

  public void setMetadataPrefix(String metadataPrefix) {
    this.metadataPrefix = metadataPrefix;
  }

  public void setServletContext(ServletContext servletContext) {
    this.servletContext = servletContext;
  }

  public void setIncrementalUpdateFlag(boolean incrementalUpdateFlag) {
    this.incrementalUpdateFlag = incrementalUpdateFlag;
  }
}
