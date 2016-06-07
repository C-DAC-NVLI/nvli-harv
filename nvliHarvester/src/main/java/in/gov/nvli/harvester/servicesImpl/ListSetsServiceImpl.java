/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHerrorType;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.SetType;
import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarSet;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.constants.HarvesterLogConstants;
import in.gov.nvli.harvester.custom.exception.OAIPMHerrorTypeException;
import in.gov.nvli.harvester.custom.harvester_enum.MethodEnum;
import in.gov.nvli.harvester.dao.HarSetDao;
import in.gov.nvli.harvester.dao.RepositoryDao;
import in.gov.nvli.harvester.services.ListSetsService;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIBeanConverter;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vootla
 */
@Service
public class ListSetsServiceImpl implements ListSetsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListSetsServiceImpl.class);

    @Autowired
    private HarSetDao harSetDao;

    @Autowired
    private RepositoryDao repositoryDao;

    private List<SetType> getSetTypeList(OAIPMHtype oAIPMHtypeObject, HarRepo harRepoObj, String desiredURL) throws IOException, JAXBException, OAIPMHerrorTypeException {
        if (oAIPMHtypeObject.getError().isEmpty()) {
            return oAIPMHtypeObject.getListSets().getSet();
        } else {
            String errorMessages = "";
            for (OAIPMHerrorType tempOAIPMHerrorType : oAIPMHtypeObject.getError()) {
                errorMessages += tempOAIPMHerrorType.getValue() + "(" + tempOAIPMHerrorType.getCode() + "),";
            }
            throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                    + "\nActivity --> " + VerbType.LIST_SETS.value()
                    + "\nCallingURL --> " + desiredURL
                    + "\nErrorCode --> " + errorMessages);
        }

    }

    @Override
    public boolean saveHarSets(String baseURL, MethodEnum method, String adminEmail) throws IOException, JAXBException, OAIPMHerrorTypeException {
        HarRepo repository = repositoryDao.getRepository(baseURL);
        return saveHarSets(repository, method, adminEmail);
    }

    @Override
    public boolean saveHarSets(HarRepo repository, MethodEnum method, String adminEmail) throws IOException, JAXBException, OAIPMHerrorTypeException {
        String desiredURL = repository.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_SETS.value();
        return saveOrUpdateListSetsRecursive(repository, desiredURL, method, adminEmail, false);
    }

    private boolean saveOrUpdateListSetsRecursive(HarRepo harRepoObj, String desiredURL, MethodEnum method, String adminEmail, boolean incrementalFlag) throws IOException, JAXBException, OAIPMHerrorTypeException {
        try {
            HttpURLConnection connection = HttpURLConnectionUtil.getConnection(desiredURL, method, adminEmail);
            if (HttpURLConnectionUtil.isConnectionAlive(connection)) {
                String resumptionToken;

                String response = OAIResponseUtil.createResponseFromXML(connection);
                OAIPMHtype oAIPMHtypeObject = (OAIPMHtype) UnmarshalUtils.xmlToOaipmh(response);

                List<HarSet> sets = OAIBeanConverter.convertSetTypeToHarSet(getSetTypeList(oAIPMHtypeObject, harRepoObj, desiredURL));
                connection.disconnect();

                if (incrementalFlag) {
                    harSetDao.saveOrUpdateHarSets(sets);
                } else {
                    harSetDao.saveHarSets(sets);
                }
                LOGGER.info(harRepoObj.getRepoUID() + ":" + sets.size() + " Sets saved/updated");
                if (oAIPMHtypeObject.getListSets().getResumptionToken() != null) {
                    resumptionToken = oAIPMHtypeObject.getListSets().getResumptionToken().getValue();
                    if (resumptionToken != null && !resumptionToken.equals("") && !resumptionToken.isEmpty()) {
                        desiredURL = harRepoObj.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_SETS.value() + CommonConstants.RESUMPTION_TOKEN + resumptionToken;
                        saveOrUpdateListSetsRecursive(harRepoObj, desiredURL, method, adminEmail, incrementalFlag);
                    }
                }
            } else {
                if (connection != null) {
                    connection.disconnect();
                }
                throw new OAIPMHerrorTypeException("RepositoryUID --> " + harRepoObj.getRepoUID()
                        + "\nActivity --> " + VerbType.LIST_SETS.value()
                        + "\nCallingURL --> " + desiredURL
                        + "\nErrorCode --> " + HarvesterLogConstants.RESPONSE_CODE_IS_NOT_200);
            }
        } catch (JAXBException | IOException ex) {
            LOGGER.error("RepositoryUID --> " + harRepoObj.getRepoUID()
                    + "\nActivity --> " + VerbType.LIST_SETS.value()
                    + "\nCallingURL --> " + desiredURL
                    + ex.getMessage(), ex);
            throw ex;
        }
        return true;
    }

    @Override
    public HarSet getHarSetByNameAndSpec(String name, String Spec) {
        return harSetDao.getHarSet(name, Spec);
    }

    @Override
    public boolean saveOrUpdateHarSets(HarRepo repository, MethodEnum method, String adminEmail) throws IOException, JAXBException, OAIPMHerrorTypeException {
        String desiredURL = repository.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_SETS.value();
        return saveOrUpdateListSetsRecursive(repository, desiredURL, method, adminEmail, true);
    }

    @Override
    public boolean saveOrUpdateHarSets(String baseURL, MethodEnum method, String adminEmail) throws IOException, JAXBException, OAIPMHerrorTypeException {
        HarRepo repository = repositoryDao.getRepository(baseURL);
        return saveOrUpdateHarSets(repository, method, adminEmail);
    }
}
