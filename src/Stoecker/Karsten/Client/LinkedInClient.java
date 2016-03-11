package Stoecker.Karsten.Client;

import Stoecker.Karsten.Helper.JSONHelper;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 *
 * @author Karsten Stoecker
 * @date 20.01.2016
 * @version 0.2
 *
 */
public class LinkedInClient extends Client
{
    private Verifier verifier;

    public LinkedInClient()
    {
        super(BasicAPIPath.linkedIn, new int[]{TokenType.CONSUMER_KEY, TokenType.CONSUMER_SECRET, TokenType.REQUEST_TOKEN, TokenType.ACCESS_TOKEN});
    }

    @Override
    public JSONObject queryNode(String path)
    {
        Token consumerKey = getToken(TokenType.CONSUMER_KEY);
        Token consumerSecret = getToken(TokenType.CONSUMER_SECRET);
        Token requestToken = null;
        Token accessToken = null;

        OAuthService service = new ServiceBuilder().provider(LinkedInApi.class).apiKey(consumerKey.getToken()).apiSecret(consumerSecret.getToken()).build();

        if(verifier == null)
        {
            requestToken = service.getRequestToken();
            setToken(TokenType.REQUEST_TOKEN, requestToken);

            String authorizationUrl = service.getAuthorizationUrl(requestToken);

            if(Desktop.isDesktopSupported())
            {
                try {
                    Desktop.getDesktop().browse(new URL(authorizationUrl).toURI());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String verifierString = (String) JOptionPane.showInputDialog(null, "Authorize app and enter pin:", "Enter pin", JOptionPane.PLAIN_MESSAGE, null, null, null);

            if(verifierString != "" && verifierString != null)
            {
                verifier = new Verifier(verifierString);
                accessToken = service.getAccessToken(requestToken, verifier);
            }
        }

        OAuthRequest request = new OAuthRequest(Verb.GET, getBasicAPIPath() + path + "?format=json");
        service.signRequest(accessToken, request);
        Response response = request.send();

        return JSONHelper.getJSONObject(response.getBody());
    }
}
