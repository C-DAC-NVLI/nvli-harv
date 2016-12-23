/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarSet;
import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author user
 */
public class TestDao {
   public static int VAR=1;
private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(IdentifyDaoImpl.class);
 SessionFactory sf;
//    @Override
    @Cacheable(value="abctemp", key="#root.method.name")	
    public  void saveREpositoryInformation() {
//        HarSet obj=new HarSet(new Long(1));
        VAR++;
       
        sf.getCurrentSession().createCriteria(HarRepo.class).list();
       sf.getCurrentSession().createCriteria(HarSet.class).list();
       
       LOGGER.debug("saved in cache"+VAR);	
          
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Caching(evict = {
		@CacheEvict(value="abctemp", allEntries=true),		    
	})	
public static void flushAllCaches() {
	       System.out.println("All caches have been completely flushed");		
}
    

    public static void main(String[] args) {
         TestDao obj=new TestDao();
           ApplicationContext ctx=new FileSystemXmlApplicationContext("E:\\NetbeansProject\\harvester\\nvliHarvester\\src\\main\\webapp\\WEB-INF\\spring-servlet.xml");
         obj.sf=(SessionFactory) ctx.getBean("sessionFactory");
      obj.saveREpositoryInformation();
       obj.saveREpositoryInformation();
      
          
          flushAllCaches();
          new TestDao().saveREpositoryInformation();
    }
}

