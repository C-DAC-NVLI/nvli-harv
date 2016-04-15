/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.dao;

import in.gov.nvli.harvester.beans.HarRepo;

/**
 *
 * @author ankit
 */
public interface RepositoryDao {
    public HarRepo addRepository(HarRepo repositoryObject);
}
