/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.listener.hibernate;

import in.gov.nvli.harvester.beans.HarRecord;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author ankit
 */
@Component
public class PostInsertHarvesterListener implements PostInsertEventListener {

    private static final String SEPERATOR = "_";

    @Override
    public void onPostInsert(PostInsertEvent pie) {
        if (pie.getEntity() instanceof HarRecord) {
            HarRecord harRecordObj = (HarRecord) pie.getEntity();
            harRecordObj.setRecordUid(harRecordObj.getRepoId().getRepoUID() + SEPERATOR + harRecordObj.getRecordId());
        }
    }
}
