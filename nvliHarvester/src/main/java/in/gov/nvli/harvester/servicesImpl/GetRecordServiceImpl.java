/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.AboutType;

import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.RecordType;
import in.gov.nvli.harvester.OAIPMH_beans.StatusType;
import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.beans.HarRecord;
import in.gov.nvli.harvester.beans.HarRecordMetadataDc;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarSet;
import in.gov.nvli.harvester.beans.HarSetRecord;
import in.gov.nvli.harvester.beans.OAIDC;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.customised.MethodEnum;
import in.gov.nvli.harvester.dao.HarMetadataTypeDao;
import in.gov.nvli.harvester.dao.HarRecordDao;
import in.gov.nvli.harvester.dao.HarRecordMetadataDcDao;
import in.gov.nvli.harvester.dao.HarSetDao;
import in.gov.nvli.harvester.dao.HarSetRecordDao;
import in.gov.nvli.harvester.dao.RepositoryDao;
import in.gov.nvli.harvester.services.GetRecordService;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author richa
 */
@Service
public class GetRecordServiceImpl implements GetRecordService {

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

    @Autowired
    RepositoryDao repositoryDao;

    @Override
    public boolean saveGetRecord(String baseURL, MethodEnum method, String adminEmail, String identifier, String metadataPrefix) throws MalformedURLException, IOException, JAXBException, ParseException {
        HarRepo harRepoObj = repositoryDao.getRepository(baseURL);
        return saveGetRecord(harRepoObj, method, adminEmail, identifier, metadataPrefix);
    }

    @Override
    public boolean saveGetRecord(HarRepo harRepoObj, MethodEnum method, String adminEmail, String identifier, String metadataPrefix) throws MalformedURLException, IOException, JAXBException, ParseException {
        HarRecord record;
        String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.GET_RECORD.value() + CommonConstants.IDENTIFIER + identifier + CommonConstants.METADATA_PREFIX + metadataPrefix;
        HttpURLConnection connection = HttpURLConnectionUtil.getConnection(desiredURL, method, adminEmail);

        if (HttpURLConnectionUtil.isConnectionAlive(connection)) {
            OAIPMHtype oAIPMHtypeObject = getOAIPMHtypeObject(connection);
            RecordType recordTypeObject = oAIPMHtypeObject.getGetRecord().getRecord();
            record = saveHarRecord(recordTypeObject, metadataPrefix, harRepoObj);
            if (null != record) {
                if (saveHarSetRecord(recordTypeObject, record)) {
                    saveHarRecordMetadataDc(recordTypeObject, record);
                }
            }
            return false;
        } else {
            connection.disconnect();
            return false;
        }
    }

    /**
     *
     * @param recordTypeList
     * @param repository
     * @param metadataPrefix
     * @return
     * @throws MalformedURLException
     * @throws IOException
     * @throws JAXBException
     * @throws ParseException
     */
    @Override
    public boolean saveGetRecordList(List<RecordType> recordTypeList, HarRepo repository, String metadataPrefix) throws MalformedURLException, IOException, JAXBException, ParseException {
        HarRecord harRecordObject;
        for (RecordType recordTypeObject : recordTypeList) {
            harRecordObject = saveHarRecord(recordTypeObject, metadataPrefix, repository);
            if (null != harRecordObject) {
                if (saveHarSetRecord(recordTypeObject, harRecordObject)) {
                    saveHarRecordMetadataDc(recordTypeObject, harRecordObject);
                }
            }
        }

        return false;
    }

    private OAIPMHtype getOAIPMHtypeObject(HttpURLConnection connection) throws IOException, JAXBException {
        String response = OAIResponseUtil.createResponseFromXML(connection);

        return UnmarshalUtils.xmlToOaipmh(response);
    }

    private HarRecord saveHarRecord(RecordType recordTypeObject, String metadataPrefix, HarRepo harRepoObject) throws ParseException {
        HarRecord harRecordObject = getHarRecordByRecordType(recordTypeObject, metadataPrefix, harRepoObject);

        recordDao.createNew(harRecordObject);

        return harRecordObject;

    }

    @Override
    public HarRecord getHarRecordByRecordType(RecordType recordTypeObject, String metadataPrefix, HarRepo harRepoObject) throws ParseException {
        HarRecord harRecordObject = new HarRecord();
        harRecordObject.setRecordIdentifier(recordTypeObject.getHeader().getIdentifier());
        DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        Date sourceDate = formatter.parse(recordTypeObject.getHeader().getDatestamp());
        harRecordObject.setRecordSoureDatestamp(sourceDate);
        harRecordObject.setMetadataTypeId(metadataTypeDao.getMetadataTypeByMetadatPrefix(metadataPrefix));
        harRecordObject.setRepoId(harRepoObject);
        List<AboutType> aboutTypes = recordTypeObject.getAbout();
        String temp = "";

        if (aboutTypes != null) {
            for (AboutType about : aboutTypes) {
                temp += about;
            }
        }
        harRecordObject.setRecordAbout(temp);

        if (recordTypeObject.getHeader().getStatus() != StatusType.DELETED) {
            harRecordObject.setRecordStatus(CommonConstants.RECORD_NOT_DELETED);
        } else {
            harRecordObject.setRecordStatus(CommonConstants.RECORD_DELETED);
        }

        return harRecordObject;

    }

    private boolean saveHarSetRecord(RecordType recordTypeObject, HarRecord harRecordObject) {
        List<HarSetRecord> harSetRecords = getHarSetRecordListByRecordType(recordTypeObject, harRecordObject);
        return harSetRecordDao.saveList(harSetRecords);

    }

    /**
     *
     * @param recordTypeObject
     * @param harRecordObject
     * @return
     */
    @Override
    public List<HarSetRecord> getHarSetRecordListByRecordType(RecordType recordTypeObject, HarRecord harRecordObject) {
        HarSetRecord harSetRecord;
        List<HarSetRecord> harSetRecords = new ArrayList<>();
        HarSet harSetObject;

        List<String> setSpecs = recordTypeObject.getHeader().getSetSpec();

        for (String setSpec : setSpecs) {
            harSetObject = harSetDao.getHarSet(setSpec);

            if (harSetObject != null) {
                harSetRecord = new HarSetRecord();
                harSetRecord.setSetId(harSetObject);
                harSetRecord.setRecordId(harRecordObject);
                harSetRecords.add(harSetRecord);
            }
        }
        return harSetRecords;

    }

    private boolean saveHarRecordMetadataDc(RecordType recordTypeObject, HarRecord harRecordObject) {
        HarRecordMetadataDc recordMetadataDc = getHarRecordMetadataDcByRecordType(recordTypeObject, harRecordObject);
        if (recordMetadataDc != null) {
            return metadataDcDao.createNew(recordMetadataDc);
        } else {
            return false;
        }
    }

    /**
     *
     * @param recordTypeObject
     * @param harRecordObject
     * @return
     */
    @Override
    public HarRecordMetadataDc getHarRecordMetadataDcByRecordType(RecordType recordTypeObject, HarRecord harRecordObject) {
        HarRecordMetadataDc recordMetadataDc = null;
        if (recordTypeObject.getHeader().getStatus() != StatusType.DELETED) {
            recordMetadataDc = convertOAIDCToHarRecordMetadataDc(recordTypeObject.getMetadata().getOaidc());
            recordMetadataDc.setRecordId(harRecordObject);
            return recordMetadataDc;
        }
        return recordMetadataDc;

    }

    @Override
    public HarRecordMetadataDc convertOAIDCToHarRecordMetadataDc(OAIDC oaiDC) {
        HarRecordMetadataDc recordMetadataDc = new HarRecordMetadataDc();
        List<String> titles = oaiDC.getTitle();
        List<String> creators = oaiDC.getCreator();
        List<String> subjects = oaiDC.getSubject();
        List<String> descriptions = oaiDC.getDescription();
        List<String> dates = oaiDC.getDate();
        List<String> types = oaiDC.getType();
        List<String> identifiers = oaiDC.getIdentifier();
        List<String> contributors = oaiDC.getContributor();
        List<String> coverages = oaiDC.getCoverage();
        List<String> languages = oaiDC.getLanguage();
        List<String> publishers = oaiDC.getPublisher();
        List<String> relations = oaiDC.getRelation();
        List<String> rights = oaiDC.getRights();
        List<String> sources = oaiDC.getSource();
        List<String> formats = oaiDC.getFormat();

        if (titles != null) {
            recordMetadataDc.setDcTitle(getMetadataTagValueSeparatedBySpecialChar(titles));
        }

        if (creators != null) {
            recordMetadataDc.setDcCreator(getMetadataTagValueSeparatedBySpecialChar(creators));
        }

        if (subjects != null) {
            recordMetadataDc.setDcSubject(getMetadataTagValueSeparatedBySpecialChar(subjects));
        }

        if (descriptions != null) {
            recordMetadataDc.setDcDescription(getMetadataTagValueSeparatedBySpecialChar(descriptions));
        }

        if (dates != null) {
            recordMetadataDc.setDcDate(getMetadataTagValueSeparatedBySpecialChar(dates));
        }

        if (types != null) {
            recordMetadataDc.setDcType(getMetadataTagValueSeparatedBySpecialChar(types));
        }

        if (identifiers != null) {
            recordMetadataDc.setDcIdentifier(getMetadataTagValueSeparatedBySpecialChar(identifiers));
        }

        if (contributors != null) {
            recordMetadataDc.setDcContributor(getMetadataTagValueSeparatedBySpecialChar(contributors));
        }

        if (coverages != null) {
            recordMetadataDc.setDcCoverage(getMetadataTagValueSeparatedBySpecialChar(coverages));
        }

        if (languages != null) {
            recordMetadataDc.setDcLanguage(getMetadataTagValueSeparatedBySpecialChar(languages));
        }

        if (publishers != null) {
            recordMetadataDc.setDcPublisher(getMetadataTagValueSeparatedBySpecialChar(publishers));
        }

        if (relations != null) {
            recordMetadataDc.setDcRelation(getMetadataTagValueSeparatedBySpecialChar(relations));
        }

        if (rights != null) {
            recordMetadataDc.setDcRights(getMetadataTagValueSeparatedBySpecialChar(rights));
        }

        if (sources != null) {
            recordMetadataDc.setDcSource(getMetadataTagValueSeparatedBySpecialChar(sources));
        }

        if (formats != null) {
            recordMetadataDc.setDcFormat(getMetadataTagValueSeparatedBySpecialChar(formats));
        }
        return recordMetadataDc;
    }

    public String getMetadataTagValueSeparatedBySpecialChar(List<String> tagValues) {

        String columnValue = "";
        for (String tagValue : tagValues) {
            columnValue += tagValue + CommonConstants.COLUMN_VALUE_SEPARATOR;
        }

        return columnValue;
    }

}
