/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.utilities;

import in.gov.nvli.harvester.customised.MethodEnum;
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

    private static final int RESPONSE_CODE_SUCCESS = 200;

    public static HttpURLConnection getConnection(String baseURL, MethodEnum method, String adminEmail) throws ProtocolException, MalformedURLException, IOException {
//        if (adminEmail == null || adminEmail.isEmpty() || adminEmail == "") {
//            return null;
//        }
        URL identifyRequestURL = new URL(baseURL);

        HttpURLConnection con = (HttpURLConnection) identifyRequestURL.openConnection();

        // optional default is GET
        con.setRequestMethod(method.toString());

        // add request header
        con.setRequestProperty("User-Agent", "");
        con.setRequestProperty("From", "From : " + adminEmail);
        return con;
    }

    public static boolean isConnectionAlive(HttpURLConnection connection) throws IOException {
        if (connection != null) {
            return connection.getResponseCode() == RESPONSE_CODE_SUCCESS;
        } else {
            return false;
        }

    }

    public static boolean isConnectionAlive(String baseURL, MethodEnum method, String adminEmail) throws ProtocolException, MalformedURLException, IOException {
        HttpURLConnection connection = getConnection(baseURL, method, adminEmail);
        if (connection != null) {
            int responseCode = connection.getResponseCode();
            if (responseCode == RESPONSE_CODE_SUCCESS) {
                connection.disconnect();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public static int getConnectionResponseCode(String baseURL, MethodEnum method, String adminEmail) throws ProtocolException, MalformedURLException, IOException {
        HttpURLConnection con = getConnection(baseURL, method, adminEmail);
        if (con != null) {
            return con.getResponseCode();
        }
        return -1;
    }

}
