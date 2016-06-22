/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarRepoStatus;
import in.gov.nvli.harvester.custom.annotation.TransactionalReadOnly;
import in.gov.nvli.harvester.custom.annotation.TransactionalReadOrWrite;
import in.gov.nvli.harvester.custom.harvester_enum.RepoStatusEnum;
import in.gov.nvli.harvester.dao.HarRecordDao;
import in.gov.nvli.harvester.dao.HarRepoStatusDao;
import in.gov.nvli.harvester.dao.RepositoryDao;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ankit
 */
@Repository
@TransactionalReadOnly
public class RepositoryDaoImpl extends GenericDaoImpl<HarRepo, Integer> implements RepositoryDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryDao.class);

    @Autowired
    private HarRecordDao harRecordDaoObj;

    @Autowired
    private HarRepoStatusDao harRepoStatusDaoObj;

    public RepositoryDaoImpl() {
        super(HarRepo.class);
    }

    @Override
    @TransactionalReadOrWrite
    public HarRepo addRepository(HarRepo repositoryObject) {
        try {
            if (getRepositoryByUID(repositoryObject.getRepoUID()) == null) {
                createNew(repositoryObject);
            } else {
                LOGGER.error("Repository with UID --> " + repositoryObject.getRepoUID() + " is already available");
                return null;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        return repositoryObject;
    }

    @Override
    public HarRepo getRepository(String baseURL) {
        HarRepo harRepo = null;
        try {
            harRepo = (HarRepo) currentSession().createCriteria(HarRepo.class).add(Restrictions.eq("repoBaseUrl", baseURL)).uniqueResult();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        return harRepo;
    }

    @Override
    public HarRepo getRepositoryByUID(String repoUID) {
        HarRepo harRepo = null;
        try {
            harRepo = (HarRepo) currentSession().createCriteria(HarRepo.class).add(Restrictions.eq("repoUID", repoUID)).uniqueResult();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return harRepo;
        }
        return harRepo;
    }

    @Override
    public List<HarRepo> getRepositories(List<String> repoUIDS) {
        List<HarRepo> harRepos = null;
        try {
            harRepos = currentSession().createCriteria(HarRepo.class).add(Restrictions.in("repoUID", repoUIDS)).list();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return harRepos;
        }
        return harRepos;
    }

    @Override
    public List<HarRepo> getRepositories() {
        List<HarRepo> harRepos = null;
        try {
            harRepos = currentSession().createCriteria(HarRepo.class).list();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return harRepos;
        }
        return harRepos;
    }

    @Override
    @TransactionalReadOrWrite
    public boolean changeRepoStatus(List<String> repositoryUIDs, short status) {

        for (String repositoryUID : repositoryUIDs) {
            if (!changeRepoStatusInternal(repositoryUID, status)) {
                return false;
            }
        }
        return true;
    }

    @Override
    @TransactionalReadOrWrite
    public boolean changeRepoStatus(String repositoryUID, short status) {
        return changeRepoStatusInternal(repositoryUID, status);
    }

    @Override
    @TransactionalReadOrWrite
    public void changeRepoStatus(HarRepo harRepoObj, short status) {
        HarRepoStatus harRepoStatusObj = harRepoStatusDaoObj.get(status);
        harRepoObj.setRepoStatusId(harRepoStatusObj);
        merge(harRepoObj);
    }

    @Override
    @TransactionalReadOrWrite
    public boolean changeRepoStatusByHarRepo(List<HarRepo> repos, short status) {
        try {
            for (HarRepo repo : repos) {
                HarRepoStatus repoStatus = (HarRepoStatus) currentSession().createCriteria(HarRepoStatus.class).add(Restrictions.eq("repoStatusId", status)).uniqueResult();
                repo.setRepoStatusId(repoStatus);
                currentSession().saveOrUpdate(repo);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private boolean changeRepoStatusInternal(String repositoryUID, short status) {
        try {
            HarRepo repo = (HarRepo) currentSession().createCriteria(HarRepo.class).add(Restrictions.eq("repoUID", repositoryUID)).uniqueResult();
            HarRepoStatus repoStatus = (HarRepoStatus) currentSession().createCriteria(HarRepoStatus.class).add(Restrictions.eq("repoStatusId", status)).uniqueResult();
            repo.setRepoStatusId(repoStatus);
            currentSession().saveOrUpdate(repo);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public List<HarRepo> getActiveRepositories() {
        List<HarRepo> activeRepos = null;
        try {
            activeRepos = currentSession().createCriteria(HarRepo.class).createAlias("repoStatusId", "repoStatusId").add(Restrictions.eq("repoStatusId.repoStatusId", RepoStatusEnum.ACTIVE)).list();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return activeRepos;
    }

    @Override
    public List<HarRepo> getRepositoriesByStaus(short repoStatusId) {
        List<HarRepo> Repos = null;
        try {
            Repos = currentSession().createCriteria(HarRepo.class).createAlias("repoStatusId", "repoStatusId").add(Restrictions.eq("repoStatusId.repoStatusId", repoStatusId)).list();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return Repos;
    }

    @Override
    @TransactionalReadOrWrite
    public void updateLastSyncStartDate(HarRepo harRepoObj, Date updatedDate) {
        try {
            harRepoObj.setRepoLastSyncDate(updatedDate);
            merge(harRepoObj);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    @TransactionalReadOrWrite
    public void updateLastSyncEndDate(HarRepo harRepoObj, Date updatedDate) {
        try {
            harRepoObj.setRepoLastSyncEndDate(updatedDate);
            merge(harRepoObj);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public long getHarRecordCount(HarRepo harRepoObj) {
        return harRecordDaoObj.getCount(harRepoObj);
    }

    @Override
    @TransactionalReadOrWrite
    public void updateHarRecordCount(HarRepo harRepoObj) {
        try {
            harRepoObj.setRecordCount(getHarRecordCount(harRepoObj));
            saveOrUpdate(harRepoObj);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
