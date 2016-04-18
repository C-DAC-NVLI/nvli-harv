/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.beans.HarRecord;
import in.gov.nvli.harvester.dao.HarRecordDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author richa
 */
@Repository
public class HarRecordDaoImpl implements HarRecordDao {

  @Autowired
  private SessionFactory sessionFactory;

  @Override
  public void saveHarRecord(HarRecord record) {
    Session session = null;
    Transaction tx=null;
    try {
      session = sessionFactory.getCurrentSession();
      tx=session.beginTransaction();
      session.save(record);
      tx.commit();

    } catch (Exception e) {
      if(tx!=null)
        tx.rollback();
      e.printStackTrace();
    }
  }

}
