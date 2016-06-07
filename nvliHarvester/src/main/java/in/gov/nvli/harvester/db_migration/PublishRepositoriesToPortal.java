/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.db_migration;

import in.gov.nvli.harvestertoprotaldbmigration.DBConnectionFactory;
import in.gov.nvli.harvestertoprotaldbmigration.DBMigrator;
import in.gov.nvli.harvestertoprotaldbmigration.DestJDBCConnectionBean;
import in.gov.nvli.harvestertoprotaldbmigration.SrcJDBCConnectionBean;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.ServletContext;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author vootla
 */
@Component
@RequestMapping("/publish")
public class PublishRepositoriesToPortal 
{
private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PublishRepositoriesToPortal.class);
    @Autowired
    ServletContext context; 
    
    
    @RequestMapping("/all")
    public @ResponseBody String  publishAllRepositories( )
   {
      
     new Thread(new Runnable() {
          @Override
          public void run() {
              publish();
          }
      }).start();
   return "migration Started at Background";
   }
    public void publish()
    {
        Properties properties = new Properties();
           InputStream input = context.getResourceAsStream("/WEB-INF/conf/DBMigration.properties");      
        try {
            properties.load(input);
           SrcJDBCConnectionBean srcBean = new SrcJDBCConnectionBean(properties.getProperty("src.DBURL"), properties.getProperty("src.DBUser"), properties.getProperty("src.DBPassword"), properties.getProperty("src.JdbcDriverName"));
           DestJDBCConnectionBean destBean = new DestJDBCConnectionBean(properties.getProperty("dest.DBURL"), properties.getProperty("dest.DBUser"), properties.getProperty("dest.DBPassword"), properties.getProperty("dest.JdbcDriverName"));
      
            DBConnectionFactory factory=new DBConnectionFactory(srcBean, destBean);
            DBMigrator migrator=new DBMigrator();
            
       migrator.migrate(factory.getSrcDbConnection(),factory.getDestDbConnection());
        } catch (IOException e) {
          LOGGER.error(e.getMessage(),e);
        }
          
           
    }
   
}

