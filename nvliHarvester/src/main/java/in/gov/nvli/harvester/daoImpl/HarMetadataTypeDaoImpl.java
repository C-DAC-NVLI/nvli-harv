/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.beans.HarMetadataType;
import in.gov.nvli.harvester.dao.HarMetadataTypeDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author richa
 */
@Repository
@Transactional(readOnly = true)
public class HarMetadataTypeDaoImpl implements HarMetadataTypeDao {

  @Autowired
  private SessionFactory sessionFactory;

  @Override
  public HarMetadataType getMetadataType(Short metadataId) {
    Session session = null;
    HarMetadataType metadataType=null;
    try {
      session = sessionFactory.getCurrentSession();
      Query query =session.createQuery("from HarMetadataType where metadataId=:metadataId");
      query.setParameter("metadataId", metadataId);
      metadataType=(HarMetadataType) query.uniqueResult();
    } catch (Exception e) {
e.printStackTrace();
    }
    return metadataType;
  }

}
