/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.dao.RepositoryDao;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ankit
 */
@Repository
@Transactional(readOnly = true)
public class RepositoryDaoImpl extends GenericDaoImpl<HarRepo, Integer> implements RepositoryDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryDao.class);

    public RepositoryDaoImpl() {
        super(HarRepo.class);
    }

    @Override
    public HarRepo addRepository(HarRepo repositoryObject) {
        try {
            createNew(repositoryObject);
            return repositoryObject;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public HarRepo getRepository(String baseURL) {
        HarRepo harRepo = null;
        try {
            harRepo = (HarRepo) currentSession().createCriteria(HarRepo.class).add(Restrictions.eq("repoBaseUrl", baseURL)).uniqueResult();
        } catch (Exception e) {

        }
        return harRepo;
    }

}
