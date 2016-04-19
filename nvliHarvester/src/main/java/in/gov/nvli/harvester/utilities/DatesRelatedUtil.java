/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author vootla
 */
public class DatesRelatedUtil {
    
    
    public static Date convertDateToGranularityFormat(String granularityFormat,String date)
    {
         Date d =null;
        try {
         SimpleDateFormat sdf=null;
	if("YYYY-MM-DDThh:mm:ssZ".equals(granularityFormat))
        {
          sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            
        }else if("YYYY-MM-DD".equals(granularityFormat))
        {
          sdf = new SimpleDateFormat("yyyy-MM-dd");   
        }
        if(sdf!=null)
        {
          d = sdf.parse(date);  
        }
    
	} catch (ParseException e) {
		e.printStackTrace();
	}
        
    return d;
    }
}
