/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.utilities;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 *
 * @author vootla
 */
public class HttpURLConnectionUtil {
    
    public HttpURLConnection getConnection(String  baseURL,String method,String userAgnet,String adminEmail) throws ProtocolException, MalformedURLException, IOException
    {
        URL identifyRequestURL = new URL(baseURL);
		HttpURLConnection con = (HttpURLConnection) identifyRequestURL.openConnection();

		// optional default is GET
		con.setRequestMethod(method);

		// add request header
		con.setRequestProperty("User-Agent", userAgnet);
		con.setRequestProperty("From", "From : " + adminEmail);
                return con;

    }
    
}
