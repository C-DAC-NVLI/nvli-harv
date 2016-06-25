/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import com.sun.syndication.feed.atom.Feed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.WireFeedInput;
import com.sun.syndication.io.XmlReader;
import com.sun.syndication.feed.atom.Link;
import in.gov.nvli.harvester.OAIPMH_beans.AboutType;

import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.RecordType;
import in.gov.nvli.harvester.OAIPMH_beans.StatusType;
import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.beans.HarRecord;
import in.gov.nvli.harvester.beans.HarRecordData;
import in.gov.nvli.harvester.beans.HarRecordMetadataDc;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarSet;
import in.gov.nvli.harvester.beans.HarSetRecord;
import in.gov.nvli.harvester.beans.OAIDC;
import in.gov.nvli.harvester.beans.mets.FileType;
import in.gov.nvli.harvester.beans.mets.Mets;
import in.gov.nvli.harvester.beans.mets.MetsType;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.custom.harvester_enum.HarRecordMetadataTypeEnum;
import in.gov.nvli.harvester.custom.harvester_enum.MethodEnum;
import in.gov.nvli.harvester.customised.HarRecordDataCustomised;
import in.gov.nvli.harvester.dao.HarMetadataTypeDao;
import in.gov.nvli.harvester.dao.HarRecordDao;
import in.gov.nvli.harvester.dao.HarRecordMetadataDcDao;
import in.gov.nvli.harvester.dao.HarSetDao;
import in.gov.nvli.harvester.dao.HarSetRecordDao;
import in.gov.nvli.harvester.dao.RepositoryDao;
import in.gov.nvli.harvester.services.GetRecordService;
import in.gov.nvli.harvester.utilities.FileUtils;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jdom.Element;
import org.jdom.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author richa
 */
@Service
public class GetRecordServiceImpl implements GetRecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetRecordServiceImpl.class);

    @Value("${repository.data.path}")
    private String repositoryDataPath;

    @Autowired
    private HarRecordMetadataDcDao metadataDcDao;

    @Autowired
    private HarRecordDao recordDao;

    @Autowired
    private HarMetadataTypeDao metadataTypeDao;

    @Autowired
    private HarSetDao harSetDao;

    @Autowired
    private HarSetRecordDao harSetRecordDao;

    @Autowired
    private RepositoryDao repositoryDao;

    private static final String LICENSE = "LICENSE";
    private static final String ORIGINAL = "ORIGINAL";
    private static final String TEXT = "TEXT";
    private static final int DISPLAY_LENGTH = 100;
    private static final String LOCALHOST = "localhost";
    private static final String PORT_8080 = "8080";

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
                    if (metadataPrefix.equals(HarRecordMetadataTypeEnum.OAI_DC.value())) {
                        saveHarRecordMetadataDc(recordTypeObject, record);
                        return true;
                    }
                }
            }
            return false;
        } else {
            if (connection != null) {
                connection.disconnect();
            }
            return false;
        }
    }

    @Override
    public boolean saveOrUpdateGetRecord(HarRepo harRepoObj, MethodEnum method, String adminEmail, String identifier, String metadataPrefix) throws MalformedURLException, IOException, JAXBException, ParseException {
        HarRecord record;
        String desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.GET_RECORD.value() + CommonConstants.IDENTIFIER + identifier + CommonConstants.METADATA_PREFIX + metadataPrefix;
        HttpURLConnection connection = HttpURLConnectionUtil.getConnection(desiredURL, method, adminEmail);
        if (HttpURLConnectionUtil.isConnectionAlive(connection)) {
            OAIPMHtype oAIPMHtypeObject = getOAIPMHtypeObject(connection);
            RecordType recordTypeObject = oAIPMHtypeObject.getGetRecord().getRecord();
            record = saveOrUpdateHarRecord(recordTypeObject, metadataPrefix, harRepoObj);
            if (null != record.getRecordId()) {
                if (saveOrUpdateHarSetRecord(recordTypeObject, record)) {
                    if (metadataPrefix.equals(HarRecordMetadataTypeEnum.OAI_DC.value())) {
                        saveOrUpdateHarRecordMetadataDc(recordTypeObject, record);
                        return true;
                    }
                }
            }
            return false;
        } else {
            if (connection != null) {
                connection.disconnect();
            }
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

    private HarRecord saveOrUpdateHarRecord(RecordType recordTypeObject, String metadataPrefix, HarRepo harRepoObject) throws ParseException {
        HarRecord harRecordObject = getHarRecordByRecordType(recordTypeObject, metadataPrefix, harRepoObject);

        recordDao.saveOrUpdateHarRecord(harRecordObject);

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

        if (recordTypeObject.getHeader().getStatus() != StatusType.DELETED && recordTypeObject.getMetadata().getOaidc() != null) {
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

    private boolean saveOrUpdateHarSetRecord(RecordType recordTypeObject, HarRecord harRecordObject) {
        List<HarSetRecord> harSetRecords = getHarSetRecordListByRecordType(recordTypeObject, harRecordObject);
        return harSetRecordDao.saveOrUpdateHarSetRecords(harSetRecords);

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

    private boolean saveOrUpdateHarRecordMetadataDc(RecordType recordTypeObject, HarRecord harRecordObject) {
        HarRecordMetadataDc recordMetadataDc = getHarRecordMetadataDcByRecordType(recordTypeObject, harRecordObject);
        if (recordMetadataDc != null) {
            return metadataDcDao.saveOrUpdateHarRecordMetadataDc(recordMetadataDc);
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
        if (recordTypeObject.getHeader().getStatus() != StatusType.DELETED && recordTypeObject.getMetadata().getOaidc() != null) {
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

        StringBuilder columnValue = new StringBuilder();
        for (String tagValue : tagValues) {
            if (columnValue.length() > 0) {
                columnValue.append(CommonConstants.COLUMN_VALUE_SEPARATOR);
            }
            columnValue.append(tagValue);
        }
        return columnValue.toString();
    }

    @Override
    public HarRecordData getHarRecordDataByRecordType(RecordType recordTypeObject, String metadataPrefix, HarRepo harRepoObject, boolean incrementalFlag) throws ParseException, TransformerConfigurationException, TransformerException, IOException, IllegalArgumentException, FeedException, MalformedURLException, JAXBException {
        HarRecordData harRecordDataObject;
        org.w3c.dom.Element elementObj;
        HarRecord harRecordObject;

        harRecordObject = recordDao.getHarRecordByRecordIdentifier(recordTypeObject.getHeader().getIdentifier());
        if (incrementalFlag) {
            saveOrUpdateGetRecord(harRepoObject, MethodEnum.POST, "", recordTypeObject.getHeader().getIdentifier(), metadataPrefix);
        } else if (harRecordObject == null) {
            saveGetRecord(harRepoObject, MethodEnum.POST, "", recordTypeObject.getHeader().getIdentifier(), metadataPrefix);
            harRecordObject = recordDao.getHarRecordByRecordIdentifier(recordTypeObject.getHeader().getIdentifier());
        }

        if (harRecordObject != null && recordTypeObject.getHeader().getStatus() != StatusType.DELETED && recordTypeObject.getMetadata().getAny() != null) {
            harRecordDataObject = new HarRecordData();
            harRecordDataObject.setRecordId(harRecordObject);

            elementObj = (org.w3c.dom.Element) recordTypeObject.getMetadata().getAny();
            Document document = elementObj.getOwnerDocument();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Source xmlSource = new DOMSource(document);
            Result outputTarget = new StreamResult(outputStream);
            TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
            InputStream is = new ByteArrayInputStream(outputStream.toByteArray());

            XmlReader reader = new XmlReader(is);
            WireFeedInput input = new WireFeedInput();
            Feed atom = (Feed) input.build(reader);

            Namespace oreNs = Namespace.getNamespace("ore", "http://www.openarchives.org/ore/terms/");
            Namespace rdfNs = Namespace.getNamespace("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
            Namespace dcNs = Namespace.getNamespace("dc", "http://purl.org/dc/terms/");
            List<Element> rdf = (List<Element>) atom.getForeignMarkup();
            for (Element e : rdf) {
                List<Element> children = e.getChildren("Description", rdfNs);
                for (Element childElement : children) {
                    Element descriptionElement = childElement.getChild("description", dcNs);
                    String recordAbout = childElement.getAttributeValue("about", rdfNs);
                    if (descriptionElement != null) {
                        switch (descriptionElement.getText()) {
                            case LICENSE:
                                harRecordDataObject.setRecordDataLicense(recordAbout);
                                break;
                            case ORIGINAL:
                                harRecordDataObject.setRecordData(recordAbout);
                                break;
                            case TEXT:
                                harRecordDataObject.setRecordDataText(recordAbout);
                                break;
                        }
                    }
                }
            }
            return harRecordDataObject;
        }
        return null;
    }

    @Override
    public HarRecord getHarRecordByRecordType(RecordType recordTypeObject, HarRepo harRepoObject, boolean incrementalFlag) throws IOException, MalformedURLException, JAXBException, ParseException {
        HarRecord harRecordObj;

        harRecordObj = recordDao.getHarRecordByRecordIdentifier(recordTypeObject.getHeader().getIdentifier());
        if (harRecordObj == null) {
            saveGetRecord(harRepoObject, MethodEnum.GET, "", recordTypeObject.getHeader().getIdentifier(), HarRecordMetadataTypeEnum.OAI_DC.value());
        } else if (incrementalFlag) {
            saveOrUpdateGetRecord(harRepoObject, MethodEnum.GET, "", recordTypeObject.getHeader().getIdentifier(), HarRecordMetadataTypeEnum.OAI_DC.value());
        } else {
            return harRecordObj;
        }
        return recordDao.getHarRecordByRecordIdentifier(recordTypeObject.getHeader().getIdentifier());
    }

    @Override
    public void saveHarRecordDataInFileSystem(HarRecordData harRecordDataObj, String path) throws IOException {
        String recordData = harRecordDataObj.getRecordData();
        String recordText = harRecordDataObj.getRecordDataText();
        String recordLicense = harRecordDataObj.getRecordDataLicense();

        if (recordData != null) {
            FileUtils.saveFile(recordData, path, FileUtils.getNameFromURL(harRecordDataObj.getRecordData()));
        }
        if (recordText != null) {
            FileUtils.saveFile(recordText, path, FileUtils.getNameFromURL(harRecordDataObj.getRecordDataText()));
        }
        if (recordLicense != null) {
            FileUtils.saveFile(recordLicense, path, FileUtils.getNameFromURL(harRecordDataObj.getRecordDataLicense()));
        }

    }

    @Override
    public HarRecord getRecord(long recordId) {
        return recordDao.getRecord(recordId);
    }

    @Override
    public HarRecordMetadataDc GetMetaDataByHarRecord(HarRecord harRecord) {
        return metadataDcDao.GetByHarRecord(harRecord);
    }

    @Override
    public boolean saveHarRecordDataInFileSystem(HarRepo harRepoObj, HarRecordMetadataTypeEnum harRecordMetadataTypeObj) {

        List<HarRecord> harRecordList;
        List<HarRecordDataCustomised> harRecordDataCustomisedList;
        String filePath;
        long recordCount;
        int displayStart = 0;

        try {
            recordCount = recordDao.rowCount(CommonConstants.RECORD_NOT_DELETED);

            while (displayStart <= recordCount) {
                harRecordList = recordDao.list(displayStart, DISPLAY_LENGTH, CommonConstants.RECORD_NOT_DELETED);
                harRecordDataCustomisedList = readHarRecordDataFromXML(harRepoObj, harRecordMetadataTypeObj, harRecordList);
                for (HarRecordDataCustomised tempHarRecordDataCustomised : harRecordDataCustomisedList) {
                    filePath = repositoryDataPath + File.separator + harRepoObj.getRepoUID() + File.separator + tempHarRecordDataCustomised.getRecordUID() + File.separator + CommonConstants.DIRECTORY_Name_RECORD_DATA;
                    LOGGER.info("Repository UID --> " + harRepoObj.getRepoUID() + " : File URL --> " + tempHarRecordDataCustomised.getFileURL());
                    FileUtils.saveFile(tempHarRecordDataCustomised, filePath);
                }
                displayStart += DISPLAY_LENGTH;
            }
        } catch (IOException | SAXException | TransformerException | IllegalArgumentException | FeedException | ParserConfigurationException | JAXBException ex) {
            LOGGER.error("RepositoryUID --> " + harRepoObj.getRepoUID()
                    + "\nActivity --> " + harRecordMetadataTypeObj.value() + " : data downloading"
                    + ex.getMessage(), ex);
            return false;
        }
        return true;
    }

    @Override
    public List<HarRecordDataCustomised> readHarRecordDataFromXML(HarRepo harRepoObj, HarRecordMetadataTypeEnum harRecordMetadataTypeObj, List<HarRecord> harRecordList) throws SAXException, IOException, TransformerException, TransformerConfigurationException, IllegalArgumentException, FeedException, ParserConfigurationException, JAXBException {
        if (harRecordMetadataTypeObj == HarRecordMetadataTypeEnum.ORE) {
            return readHarRecordDataFromORE(harRepoObj, harRecordList);
        } else if (harRecordMetadataTypeObj == HarRecordMetadataTypeEnum.METS) {
            return readHarRecordDataFromMETS(harRepoObj, harRecordList);
        }
        return new ArrayList<>();
    }

    private List<HarRecordDataCustomised> readHarRecordDataFromORE(HarRepo harRepoObj, List<HarRecord> harRecordList) throws SAXException, IOException, TransformerConfigurationException, TransformerException, IllegalArgumentException, FeedException, ParserConfigurationException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Namespace oreNs = Namespace.getNamespace("ore", "http://www.openarchives.org/ore/terms/");
        Namespace rdfNs = Namespace.getNamespace("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        Namespace dcNs = Namespace.getNamespace("dc", "http://purl.org/dc/terms/");
        List<HarRecordDataCustomised> harRecordDataCustomisedList = new ArrayList<>();

        File oreXMLFile;
        Document doc;
        ByteArrayOutputStream outputStream;
        Source xmlSource;
        Result outputTarget;
        InputStream is;
        XmlReader reader;
        WireFeedInput input;
        Feed atom;
        List<Link> links;
        List<Link> altLinks;
        String harRepoLink;

        HarRecordDataCustomised tempHarRecordDataCustomised;

        for (HarRecord tempHarRecord : harRecordList) {
            oreXMLFile = new File(repositoryDataPath + File.separator + harRepoObj.getRepoUID() + File.separator + tempHarRecord.getRecordUid() + File.separator + CommonConstants.DIRECTORY_Name_ORE + File.separator + tempHarRecord.getRecordUid() + ".xml");
            if (oreXMLFile.exists()) {
                doc = dBuilder.parse(oreXMLFile);
                outputStream = new ByteArrayOutputStream();
                xmlSource = new DOMSource(doc);
                outputTarget = new StreamResult(outputStream);
                TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
                is = new ByteArrayInputStream(outputStream.toByteArray());

                reader = new XmlReader(is);
                input = new WireFeedInput();
                atom = (Feed) input.build(reader);

                links = atom.getOtherLinks();
                altLinks = atom.getAlternateLinks();
                links.addAll(altLinks);

                List<Element> rdf = (List<Element>) atom.getForeignMarkup();
                for (Element e : rdf) {
                    List<Element> children = e.getChildren("Description", rdfNs);
                    for (Element childElement : children) {
                        Element descriptionElement = childElement.getChild("description", dcNs);
                        String recordAbout = childElement.getAttributeValue("about", rdfNs);
                        if (descriptionElement != null) {
                            tempHarRecordDataCustomised = new HarRecordDataCustomised();

                            for (Link tempLink : links) {
                                if (tempLink.getHref().equals(recordAbout)) {
                                    tempHarRecordDataCustomised.setFileName(tempLink.getTitle());
                                    tempHarRecordDataCustomised.setFileMimeType(tempLink.getType());
                                    tempHarRecordDataCustomised.setFileSize(tempLink.getLength());
                                    break;
                                }
                            }

                            recordAbout = recordAbout.toLowerCase();
                            if (recordAbout.contains(LOCALHOST) || recordAbout.contains(PORT_8080)) {
                                harRepoLink = harRepoObj.getRepoLink();
                                if (harRepoLink != null) {
                                    recordAbout = HttpURLConnectionUtil.updateBaseURL(recordAbout, harRepoLink);
                                }
                            }

                            tempHarRecordDataCustomised.setFileURL(recordAbout);
                            tempHarRecordDataCustomised.setRecordUID(tempHarRecord.getRecordUid());

                            harRecordDataCustomisedList.add(tempHarRecordDataCustomised);
                        }
                    }
                }
                outputStream.close();
                is.close();

            }
        }
        return harRecordDataCustomisedList;
    }

    private List<HarRecordDataCustomised> readHarRecordDataFromMETS(HarRepo harRepoObj, List<HarRecord> harRecordList) throws JAXBException {

        File metsXMLFile;
        Mets metsObj;
        MetsType.FileSec fileSecObj;
        List<MetsType.FileSec.FileGrp> fileGroupList;
        List<FileType> fileTypeList;
        String fileURL;
        String harRepoLink;

        List<HarRecordDataCustomised> harRecordDataCustomisedList = new ArrayList<>();
        HarRecordDataCustomised tempHarRecordDataCustomised;

        for (HarRecord tempHarRecord : harRecordList) {
            metsXMLFile = new File(repositoryDataPath + File.separator + harRepoObj.getRepoUID() + File.separator + tempHarRecord.getRecordUid() + File.separator + CommonConstants.DIRECTORY_Name_METS + File.separator + tempHarRecord.getRecordUid() + ".xml");
            metsObj = UnmarshalUtils.xmlToMETS(metsXMLFile);
            fileSecObj = metsObj.getFileSec();
            if (fileSecObj != null) {
                fileGroupList = fileSecObj.getFileGrp();
                if (fileGroupList != null) {
                    for (MetsType.FileSec.FileGrp tempFileGrp : fileGroupList) {
                        fileTypeList = tempFileGrp.getFile();
                        if (fileTypeList != null) {
                            for (FileType tempFileType : fileTypeList) {
                                tempHarRecordDataCustomised = new HarRecordDataCustomised();
                                tempHarRecordDataCustomised.setFileSize(tempFileType.getSIZE());
                                tempHarRecordDataCustomised.setFileMimeType(tempFileType.getMIMETYPE());
                                tempHarRecordDataCustomised.setFileChecksum(tempFileType.getCHECKSUM());
                                tempHarRecordDataCustomised.setChecksumType(tempFileType.getCHECKSUMTYPE());
                                tempHarRecordDataCustomised.setRecordUID(tempHarRecord.getRecordUid());

                                fileURL = tempFileType.getFLocat().get(0).getHref().toLowerCase();
                                if (fileURL.contains(LOCALHOST) || fileURL.contains(PORT_8080)) {
                                    harRepoLink = harRepoObj.getRepoLink();
                                    if (harRepoLink != null) {
                                        fileURL = HttpURLConnectionUtil.updateBaseURL(fileURL, harRepoLink);
                                    }
                                }
                                tempHarRecordDataCustomised.setFileURL(fileURL);

                                harRecordDataCustomisedList.add(tempHarRecordDataCustomised);
                            }
                        }
                    }
                }
            }
        }
        return harRecordDataCustomisedList;
    }
}
