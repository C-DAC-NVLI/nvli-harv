/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.customised;

/**
 *
 * @author svootla
 */
public enum RepoStatusEnum {
    
    NOT_ACTIVE((short)1),
    ACTIVE((short)2),
    HARVEST_PROCESSING((short)3),
    HARVEST_PROCESSING_ERROR((short)4),
    HARVEST_COMPLETE((short)5),
    INCREMENT_HARVEST_PROCESSING((short)6),
    INCREMENT_HARVEST_PROCESSING_ERROR((short)7),
    INVALID_URL((short)8);
    

    private final short value;

    private RepoStatusEnum(final short value) {
        this.value = value;
    }

   public short getValue() { 
       return value; 
   }

}
