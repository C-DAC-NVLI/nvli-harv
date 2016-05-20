
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.dao;

import in.gov.nvli.harvester.beans.HarRepo;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ankit
 */
public interface RepositoryDao extends GenericDao<HarRepo, Integer> {

    public HarRepo addRepository(HarRepo repositoryObject);

    public HarRepo getRepository(String baseURL);

    public HarRepo getRepositoryByUID(String repoUID);

    public List<HarRepo> getRepositories(List<String> repoUIDS);

    boolean changeRepoStatus(List<String> repositoryUIDs, short status);

    boolean changeRepoStatus(String repositoryUID, short status);

    boolean changeRepoStatusByHarRepo(List<HarRepo> repos, short status);

    public List<HarRepo> getRepositories();

    public List<HarRepo> getActiveRepositories();

    public List<HarRepo> getRepositoriesByStaus(short repoStatusId);

    public void updateLastSyncDate(String repoUID, Date updatedDate);

    public long getHarRecordCount(HarRepo harRepoObj);

    public void updateHarRecordCount(HarRepo harRepoObj);
    
    public void updateLastSyncEndDate(String repoUID, Date updatedDate);
}
