package in.gov.nvli.harvester.services;

import in.gov.nvli.harvester.beans.HarLog;
import java.util.List;

/**
 * This class has methods which are important for repository harvesting history
 * related events like saving history,deleting history etc.
 *
 * @author Bhumika
 */
//@Service
public interface RepositoryHarvestingHistoryService {

    HarLog saveHistoryObject(HarLog historyObject);

    List<HarLog> getCompleteHistory();

    List<HarLog> getRepositoryHistory(int repositoryId);

    void deleteHistoryOfRepositories(List<Integer> repositoryIds);

    boolean deleteHistoryOfRepository(int repositoryId);

    void deleteHistoryByIds(List<Integer> historyIds);

    boolean deleteParticularHistoryById(int historyId);

}
