/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.MetadataFormatType;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.beans.HarMetadataType;
import in.gov.nvli.harvester.beans.HarMetadataTypeRepository;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.dao.HarMetadataTypeDao;
import in.gov.nvli.harvester.dao.HarMetadataTypeRepositoryDao;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vootla
 */
@Service
public class ListMetadataFormatsServiceImpl implements ListMetadataFormatsService {

    private HttpURLConnection connection;

    @Autowired
    private HarMetadataTypeDao harMetadataTypeDao;

    @Autowired
    private HarMetadataTypeRepositoryDao harMetadataTypeRepositoryDao;

    private HarRepo repository = null;

    @Override
    public int getConnectionStatus(String baseURL, String method, String userAgnet, String adminEmail) throws MalformedURLException, IOException {
        connection = HttpURLConnectionUtil.getConnection(baseURL, "GET", "", "");
        int status = HttpURLConnectionUtil.getConnectionStatus(connection);
        if (status == -1) {
            connection.disconnect();
        }
        return status;
    }

    @Override
    public List<MetadataFormatType> getListMetadataFormats() throws IOException, JAXBException {
        String response = OAIResponseUtil.createResponseFromXML(connection);
        OAIPMHtype obj = (OAIPMHtype) UnmarshalUtils.xmlToOaipmh(response);
        return obj.getListMetadataFormats().getMetadataFormat();
    }

    @Override
    public boolean saveListOfMetadataFormats() throws IOException, JAXBException {
        List<HarMetadataType> metadatFormts = new ArrayList<>();
        for (MetadataFormatType metadataFormatType : getListMetadataFormats()) {
            metadatFormts.add(OAIBeanConverter.metadataFormatTypeToHarMetadataType(metadataFormatType));
        }
        if (harMetadataTypeDao.saveHarMetadataTypes(metadatFormts)) {
            return saveListOfMetadataFormatsOfRepository(metadatFormts);
        } else {
            return false;
        }
    }

    @Override
    public List<MetadataFormatType> getListMetadataFormats(String baseUrl) throws MalformedURLException, IOException, JAXBException {
        connection = HttpURLConnectionUtil.getConnection(baseUrl, "GET", "", "");
        if (connection.getResponseCode() != 200) {
            connection.disconnect();
            return null;
        } else {
            return getListMetadataFormats();
        }

    }

    @Override
    public boolean saveListOfMetadataFormats(String baseUrl) throws MalformedURLException, IOException, JAXBException {
        connection = HttpURLConnectionUtil.getConnection(baseUrl, "GET", "", "");
        if (connection.getResponseCode() != 200) {
            connection.disconnect();
            return false;
        } else {
            return saveListOfMetadataFormats();
        }
    }

    public boolean saveListOfMetadataFormatsOfRepository(List<HarMetadataType> metadataOfRepo) {
        HarMetadataTypeRepository obj = null;
        List<HarMetadataTypeRepository> metadatOfRepo = new ArrayList<>();
        for (HarMetadataType meataData : metadataOfRepo) {
            obj = new HarMetadataTypeRepository();
            obj.setRepoId(repository);
            obj.setMetadataTypeId(harMetadataTypeDao.getMetadataTypeByMetadatPrefix(meataData.getMetadataPrefix()));
            metadatOfRepo.add(obj);
        }
        return harMetadataTypeRepositoryDao.saveHarMetadataTypesOfRepository(metadatOfRepo);
    }

    public HarRepo getRepository() {
        return repository;
    }

    @Override
    public void setRepository(HarRepo repository) {
        this.repository = repository;
    }

}
