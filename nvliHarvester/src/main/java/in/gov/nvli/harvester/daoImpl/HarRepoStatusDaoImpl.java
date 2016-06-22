/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.beans.HarRepoStatus;
import in.gov.nvli.harvester.custom.annotation.TransactionalReadOnly;
import in.gov.nvli.harvester.dao.HarRepoStatusDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ankit
 */
@Repository
@TransactionalReadOnly
public class HarRepoStatusDaoImpl extends GenericDaoImpl<HarRepoStatus, Short> implements HarRepoStatusDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(HarRepoStatusDaoImpl.class);

    public HarRepoStatusDaoImpl() {
        super(HarRepoStatus.class);
    }

}
