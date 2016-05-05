package in.gov.nvli.harvester.services;

import in.gov.nvli.harvester.beans.HarRepo;
import java.util.List;

/**
 * This class has methods which are important for repository related events like
 * saving,deleting,updating,publishing,blocking etc repositories.
 *
 * @author Bhumika
 */
//@Service
public interface RepositoryService {

    HarRepo addRepository(HarRepo repositoryObject);

    List<HarRepo> getAllRepositories();

    HarRepo getRepository(int repositoryId);

    List<HarRepo> getRepositoriesByStatus(List<Integer> statusIds);

    boolean editRepository(HarRepo repositoryObject);

    boolean deleteRepository(int repositoryId);

    void deleteRepositories(List<Integer> repositoryIds);

    void activateOrDeactivateRepositories(List<Integer> repositoryIds);

    void activateOrDeactivateRepository(int repositoryId);

    void publishOrWithdrawRepositories(List<Integer> repositoryIds);

    void publishOrWithdrawRepository(int repositoryId);

    void blockRepositories(List<Integer> repositoryIds);

    void blockRepository(int repositoryId);

    void synchroniseRepositories(List<Integer> repositoryIds);

    void synchroniseRepository(int repositoryId);

    void scheduleSynchronisationOfRepositories(List<Integer> repositoryIds);

    void scheduleSynchronisationOfRepository(int repositoryId);

    void validateRepositories(List<Integer> repositoryIds);

    boolean isRepositoryValid(int repositoryId);
    
    public HarRepo getRepositoryByUID(int repoUID);

    public List<HarRepo> getRepositoriesByUIDS(List<Integer> repoUIDS);
}
