package Stoecker.Karsten.Client;

import Stoecker.Karsten.Helper.JSONHelper;
import org.json.JSONObject;
import org.scribe.model.Token;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Client for querying data from the social network Facebook.
 *
 * @author Karsten Stoecker
 * @date 15.01.2016
 * @version 0.1
 *
 */
public class FacebookClient extends Client{


    public FacebookClient()
    {
        super(BasicAPIPath.facebook,new int[]{TokenType.USER_ACCESS_TOKEN});
    }

    public JSONObject queryNode(String path)
    {
        Token userAccessToken = getToken(TokenType.USER_ACCESS_TOKEN);

        String sURL = getBasicAPIPath() + path + "?access_token=" + userAccessToken.getToken();

        URL url = null;

        try {
            url = new URL(sURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        int responseCode = 0;
        HttpURLConnection httpURLConnection = null;
        try
        {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            responseCode = httpURLConnection.getResponseCode();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if(responseCode == 400) // catch expired user access token = error code 400
        {
            JOptionPane.showMessageDialog(null, "Your user access token is expired. Please go to https://developers.facebook.com/tools/explorer, get a new one, save it to token file and open it.", "User access token expired", JOptionPane.WARNING_MESSAGE);

            if(Desktop.isDesktopSupported())
            {
                try {
                    Desktop.getDesktop().browse(new URL("https://developers.facebook.com/tools/explorer").toURI());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return new JSONObject();
        }
        else
        {
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

            insertIntoQueriedData(getBasicAPIPath(), path, JSONHelper.getJSONObject(response.toString()));

            return JSONHelper.getJSONObject(response.toString());
        }
    }
}
