/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.dao.RepositoryDao;
import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ankit
 */
@Repository
@Transactional(readOnly = true)
public class RepositoryDaoImpl extends GenericDaoImpl<HarRepo,Integer> implements RepositoryDao{



    public RepositoryDaoImpl() {
        super(HarRepo.class);
    }
    
    @Override
    public HarRepo addRepository(HarRepo repositoryObject) {
        try {
            
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public HarRepo getRepository(int repositoryId) {
       return get(repositoryId);
    }
    
}
