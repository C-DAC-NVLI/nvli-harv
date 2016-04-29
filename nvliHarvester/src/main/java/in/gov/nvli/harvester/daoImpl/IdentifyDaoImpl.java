/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.dao.IdentifyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author vootla
 */
@Repository
@Transactional(readOnly = true)
public class IdentifyDaoImpl implements IdentifyDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdentifyDaoImpl.class);
}
