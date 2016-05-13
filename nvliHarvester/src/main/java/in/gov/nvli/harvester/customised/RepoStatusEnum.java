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

    NOT_ACTIVE((short) 1, "not_active"),
    ACTIVE((short) 2, "active"),
    HARVEST_PROCESSING((short) 3, "harvest_processing"),
    HARVEST_PROCESSING_ERROR((short) 4,"harvest_processing_error"),
    HARVEST_COMPLETE ((short)5,"harvest_complete"),
    INCREMENT_HARVEST_PROCESSING((short)6,"increment_harvest_processing"),
    INCREMENT_HARVEST_PROCESSING_ERROR((short)7,"increment_harvest_processing_error"),
    INVALID_URL((short)8,"invalid_url");
    

    private final short id;
    private final String name;

    private RepoStatusEnum(final short id,final String name)
{
        this.id = id;
        this.name = name;
        
    }

   public short getId() { 
       return id; 
   }
public String getName(){ 
       return name; 
   }
}
