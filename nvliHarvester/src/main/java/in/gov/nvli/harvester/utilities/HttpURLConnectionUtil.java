/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.utilities;

import in.gov.nvli.harvester.custom.harvester_enum.MethodEnum;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import org.slf4j.LoggerFactory;

/**
 *
 * @author vootla
 */
public class HttpURLConnectionUtil {

    private static final int INTERVAL = 60000;
    private static final int MAX_ATTEMPT = 3;
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HttpURLConnectionUtil.class);

    private static final HashMap<String, Integer> URL_MAP = new HashMap<>();

    public static HttpURLConnection getConnection(String desiredURL, MethodEnum method, String adminEmail) throws IOException {
//        if (adminEmail == null || adminEmail.isEmpty() || adminEmail == "") {
//            return null;
//        }
        try {
            URL identifyRequestURL = new URL(desiredURL);
            HttpURLConnection con = (HttpURLConnection) identifyRequestURL.openConnection();
            // optional default is GET
            con.setRequestMethod(method.toString());
            // add request header
            con.setRequestProperty("User-Agent", "");
            con.setRequestProperty("From", "From : " + adminEmail);

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                URL_MAP.remove(desiredURL);
                return con;
            } else {
                return null;
            }

        } catch (IOException ex) {
            if (connectionRetry(desiredURL)) {
                return getConnection(desiredURL, method, adminEmail);
            } else {
                throw ex;
            }
        }
    }

    public static HttpURLConnection getConnection(String url) throws IOException {
        try {
            URL identifyRequestURL = new URL(url);
            HttpURLConnection con = (HttpURLConnection) identifyRequestURL.openConnection();
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return con;
            } else {
                return null;
            }

        } catch (IOException ex) {
            if (connectionRetry(url)) {
                return getConnection(url);
            } else {
                throw ex;
            }
        }
    }

    public static boolean isConnectionAlive(HttpURLConnection connection) {
        try {
            if (connection != null) {
                return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
            } else {
                return false;
            }
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return false;
        }

    }

    public static boolean isConnectionAlive(String desiredURL, MethodEnum method, String adminEmail) {
        try {
            HttpURLConnection connection = getConnection(desiredURL, method, adminEmail);
            if (connection != null) {
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    connection.disconnect();
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (IOException ex) {
            LOGGER.error("CallingURL --> " + desiredURL + ex.getMessage(), ex);
            return false;
        }

    }

    public static int getConnectionResponseCode(String desiredURL, MethodEnum method, String adminEmail) throws ProtocolException, MalformedURLException, IOException {
        HttpURLConnection con = getConnection(desiredURL, method, adminEmail);
        if (con != null) {
            return con.getResponseCode();
        }
        return -1;
    }

    public static boolean connectionRetry(String desiredURL) {
        try {
            LOGGER.info("Trying to re-connect --> " + desiredURL);
            Thread.sleep(INTERVAL);
            boolean result = isConnectionRetryLimitAvailable(desiredURL);
            if (!result) {
                LOGGER.info("Given up --> " + desiredURL);
            }
            return result;
        } catch (InterruptedException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return false;
    }

    private static boolean isConnectionRetryLimitAvailable(String desiredURL) {
        if (URL_MAP.get(desiredURL) == null) {
            URL_MAP.put(desiredURL, 1);
            return true;
        } else if (URL_MAP.get(desiredURL) < MAX_ATTEMPT - 1) {
            URL_MAP.put(desiredURL, URL_MAP.get(desiredURL) + 1);
            return true;
        } else {
            URL_MAP.remove(desiredURL);
            return false;
        }
    }
}
