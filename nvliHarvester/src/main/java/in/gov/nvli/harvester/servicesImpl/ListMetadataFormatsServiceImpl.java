/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.MetadataFormatType;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.beans.HarMetadataType;
import in.gov.nvli.harvester.beans.HarMetadataTypeRepository;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.customised.MethodEnum;
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

    @Autowired
    private HarMetadataTypeDao harMetadataTypeDao;

    @Autowired
    private HarMetadataTypeRepositoryDao harMetadataTypeRepositoryDao;

    @Override
    public List<MetadataFormatType> getMetadataFormatTypeList(HttpURLConnection connection) throws IOException, JAXBException {
        String response = OAIResponseUtil.createResponseFromXML(connection);
        OAIPMHtype obj = (OAIPMHtype) UnmarshalUtils.xmlToOaipmh(response);
        return obj.getListMetadataFormats().getMetadataFormat();
    }

    @Override
    public boolean saveHarMetadataTypes(HarRepo repo, MethodEnum method, String adminEmail) throws MalformedURLException, IOException, JAXBException {
        String desiredURL = repo.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_METADATA_FORMATS.value();
        return saveOrUpdate(desiredURL, method, adminEmail, repo);
    }

    private boolean saveOrUpdate(String baseUrl, MethodEnum method, String adminEmail, HarRepo repo) throws MalformedURLException, IOException, JAXBException {
        HttpURLConnection connection = HttpURLConnectionUtil.getConnection(baseUrl, method, adminEmail);
        if (!HttpURLConnectionUtil.isConnectionAlive(connection)) {
            connection.disconnect();
            return false;
        } else {
            List<HarMetadataType> harMetadataTypeList = OAIBeanConverter.convertMetadataFormatTypeToHarMetadataType(getMetadataFormatTypeList(connection));
            connection.disconnect();
            if (harMetadataTypeDao.saveHarMetadataTypes(harMetadataTypeList)) {
                return saveHarMetadataTypeRepositoryList(harMetadataTypeList, repo);
            } else {
                return false;
            }
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
