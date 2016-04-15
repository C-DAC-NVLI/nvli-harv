/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.beans.HarSet;
import in.gov.nvli.harvester.dao.HarSetDao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author vootla
 */
@Repository
@Transactional(readOnly = true)
public class HarSetDaoImpl implements HarSetDao{

  @Autowired
  public SessionFactory sf;
    

    @Override
    @Transactional()
    public boolean saveHarSets(List<HarSet> sets) {
        Session session = sf.getCurrentSession();
       try
       {
        for(HarSet set:sets)
        {
            session.save(set);
        }
      return true;
       }catch(Exception e)
       {
           return false;
       }
    }
    
}
