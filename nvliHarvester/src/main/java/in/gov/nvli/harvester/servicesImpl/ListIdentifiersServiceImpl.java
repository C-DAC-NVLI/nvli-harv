/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.HeaderType;
import in.gov.nvli.harvester.OAIPMH_beans.ListIdentifiersType;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.ResumptionTokenType;
import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.custom.harvester_enum.MethodEnum;
import in.gov.nvli.harvester.dao.RepositoryDao;
import in.gov.nvli.harvester.services.ListIdentifiersService;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author richa
 */
@Service
public class ListIdentifiersServiceImpl implements ListIdentifiersService {

    @Autowired
    RepositoryDao repositoryDao;

    Logger LOGGER = LoggerFactory.getLogger(ListIdentifiersService.class);

    @Override
    public void getListIdentifiers(String basUrl, MethodEnum method, String adminEmail, String metadataPrefix) throws MalformedURLException, IOException, JAXBException {
        HarRepo harRepoObj = repositoryDao.getRepository(basUrl);
        getListIdentifiers(harRepoObj, method, adminEmail, metadataPrefix);
    }

    @Override
    public void getListIdentifiers(HarRepo repository, MethodEnum method, String adminEmail, String metadataPrefix) throws MalformedURLException, IOException, JAXBException {
        String desiredURL = repository.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_IDENTIFIERS.value() + CommonConstants.METADATA_PREFIX + metadataPrefix;
        getListIdentifiersRecursive(repository, desiredURL, method, adminEmail);
    }

    private void getListIdentifiersRecursive(HarRepo harRepoObj, String desiredUrl, MethodEnum method, String adminEmail) throws MalformedURLException, IOException, JAXBException {
        HttpURLConnection connection = HttpURLConnectionUtil.getConnection(desiredUrl, MethodEnum.GET, adminEmail);

        if (HttpURLConnectionUtil.isConnectionAlive(connection)) {
            String identifier;
            ListIdentifiersType listIdentifiers;
            List<HeaderType> headers;
            ResumptionTokenType resumptionToken;

            listIdentifiers = getListIdentifiersType(connection);
            headers = listIdentifiers.getHeader();

            for (HeaderType header : headers) {
                identifier = header.getIdentifier();
                LOGGER.info(" Identifier " + identifier);
            }

            resumptionToken = listIdentifiers.getResumptionToken();
            if (resumptionToken != null) {
                desiredUrl = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_IDENTIFIERS.value() + CommonConstants.RESUMPTION_TOKEN + resumptionToken.getValue();
                getListIdentifiersRecursive(harRepoObj, desiredUrl, method, adminEmail);
            }

        } else {
            try {
                Thread.sleep(1000);
                getListIdentifiersRecursive(harRepoObj, desiredUrl, method, adminEmail);
            } catch (InterruptedException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }

        }
    }

    private ListIdentifiersType getListIdentifiersType(HttpURLConnection connection) throws IOException, JAXBException {

        String response = OAIResponseUtil.createResponseFromXML(connection);
        OAIPMHtype oaiPMHTypeObj = UnmarshalUtils.xmlToOaipmh(response);

        return oaiPMHTypeObj.getListIdentifiers();

    }

    @Override
    public void getRecordByIdentifiers(HarRepo repository, MethodEnum method, String adminEmail, String identifier, String metadataPrefix) {
        String desiredURL = repository.getRepoBaseUrl();
        String requestUrl = desiredURL + CommonConstants.VERB + VerbType.GET_RECORD.value() + "&identifier=" + identifier + "&metadataPrefix=" + metadataPrefix;
        System.out.println("identifier " + identifier);
    }
}
