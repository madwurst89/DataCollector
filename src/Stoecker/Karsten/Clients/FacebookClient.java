package Stoecker.Karsten.Clients;

import Stoecker.Karsten.Helper.JSONHelper;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 *
 * @author Karsten Stoecker
 * @date 15.01.2016
 * @version 0.1
 *
 */
public class FacebookClient implements Client{

    private String userAccessToken;

    /**
     *
     * @param userAccessToken user access token
     * @version 0.1
     *
     */
    public FacebookClient(String userAccessToken)
    {
        this.userAccessToken = userAccessToken;
    }

    /**
     *
     * @param path Path to the node to query, excluding https://graph.facebook.com/
     * @return Node as {@link JSONObject}
     * @version 0.1
     *
     */
    public JSONObject queryNode(String path)
    {
        String sURL = "https://graph.facebook.com/" + path + "?access_token=" + userAccessToken;

        URL url = null;
        try {
            url = new URL(sURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        int responseCode = 0;
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            responseCode = httpURLConnection.getResponseCode();

            if(responseCode == 400) // catch expired user access token = error code 400
            {
                System.out.println("User access token expired. Create a new one on https://developers.facebook.com/tools/explorer");
                System.exit(0);
            }
        }
        catch (ProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        String inputLine;
        StringBuffer response = new StringBuffer();
        try(BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())))
        {
            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine);
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return JSONHelper.getJSONObject(response.toString());
    }

    /**
     * Method to change user access token after initialization.
     *
     * @param userAccessToken user access token
     * @version 0.1
     *
     */
    public void setUserAccessToken(String userAccessToken)
    {
        this.userAccessToken = userAccessToken;
    }
}