/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.IdentifyType;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.custom.harvester_enum.MethodEnum;
import in.gov.nvli.harvester.services.IdentifyService;
import in.gov.nvli.harvester.utilities.DatesRelatedUtil;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.Date;
import javax.xml.bind.JAXBException;
import org.springframework.stereotype.Service;

/**
 *
 * @author vootla
 */
@Service
public class IdentifyServiceImpl implements IdentifyService {

    private IdentifyType getIdentifyTypeObject(HttpURLConnection connection) throws IOException, JAXBException {
        String response = OAIResponseUtil.createResponseFromXML(connection);
        OAIPMHtype obj = (OAIPMHtype) UnmarshalUtils.xmlToOaipmh(response);
        return obj.getIdentify();
    }

    @Override
    public HarRepo convertIdentifyTypeToHarRepo(IdentifyType identifyTypeObject) {
        if (identifyTypeObject == null) {
            return null;
        }
        HarRepo repo = new HarRepo();
        repo.setRepoName(identifyTypeObject.getRepositoryName());
        repo.setRepoBaseUrl(identifyTypeObject.getBaseURL());
        repo.setRepoProtocolVersion(identifyTypeObject.getProtocolVersion());

        Date repoDate = DatesRelatedUtil.convertDateToGranularityFormat(identifyTypeObject.getGranularity().value(), identifyTypeObject.getEarliestDatestamp());
        repo.setRepoEarliestTimestamp(repoDate);

        repo.setRepoGranularityDate(identifyTypeObject.getGranularity().value());
        repo.setRepoDeletionMode(identifyTypeObject.getDeletedRecord().value());

        StringBuilder compressions = null;
        boolean tempflag = true;
        for (String temp : identifyTypeObject.getCompression()) {
            if (tempflag) {
                compressions = new StringBuilder(temp);
                tempflag = false;
            } else {
                compressions.append(CommonConstants.COLUMN_VALUE_SEPARATOR);
                compressions.append(temp);
            }
        }

        if (compressions != null) {
            repo.setRepoCompression(compressions.toString());
        }

        return repo;
    }

    @Override
    public HarRepo getIdentify(String baseURL, MethodEnum method, String adminEmail) throws MalformedURLException, IOException, JAXBException {
        String desiredURL = baseURL+CommonConstants.VERB+VerbType.IDENTIFY.value();
        HttpURLConnection connection = HttpURLConnectionUtil.getConnection(desiredURL, MethodEnum.GET, adminEmail);
        if (HttpURLConnectionUtil.isConnectionAlive(connection)) {
            IdentifyType identifyTypeObject = getIdentifyTypeObject(connection);
            return convertIdentifyTypeToHarRepo(identifyTypeObject);
        } else {
            connection.disconnect();
            return null;
        }
    }

    @Override
    public IdentifyType getIdentifyTypeObject(String baseURL, MethodEnum method, String adminEmail) throws MalformedURLException, IOException, JAXBException {
        String desiredURL = baseURL+CommonConstants.VERB+VerbType.IDENTIFY.value();
        HttpURLConnection connection = HttpURLConnectionUtil.getConnection(desiredURL, MethodEnum.GET, adminEmail);
        if (HttpURLConnectionUtil.isConnectionAlive(connection)) {
            return getIdentifyTypeObject(connection);
        } else {
            connection.disconnect();
            return null;
        }
    }
}
