/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.dao.RepositoryDao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ankit
 */
@Repository
@Transactional(readOnly = true)
public class RepositoryDaoImpl implements RepositoryDao{

    @Autowired
    SessionFactory sf;
    
    @Override
    @Transactional
    public HarRepo addRepository(HarRepo repositoryObject) {
        Session session = sf.getCurrentSession();
        try {
            
            return new HarRepo((Integer) session.save(repositoryObject));
        } catch (Exception e) {
            return null;
        }
    }

  @Override
  public List<HarRepo> getRepositories() {
    Session session = sf.getCurrentSession();
    List<HarRepo> harRepos=null;
    try{
      harRepos=session.createCriteria(HarRepo.class).list();
    }catch(Exception e){
      
    }
    return harRepos;
  }

  @Override
  public HarRepo getRepository(String baseURL) {
    Session session = sf.getCurrentSession();
    HarRepo harRepo=null;
    try{
      harRepo=(HarRepo) session.createCriteria(HarRepo.class).add(Restrictions.eq("repoBaseUrl", baseURL)).uniqueResult();
    }catch(Exception e){
      
    }
    return harRepo;
  }
    
}
