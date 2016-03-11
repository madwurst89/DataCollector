package Stoecker.Karsten.Client;

import Stoecker.Karsten.Helper.JSONHelper;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 *
 * @author Karsten Stoecker
 * @date 18.01.2016
 * @version 0.2
 *
 */
public class TwitterClient extends Client
{
    private Verifier verifier;

    public TwitterClient()
    {
        super(BasicAPIPath.twitter, new int[]{TokenType.CONSUMER_KEY, TokenType.CONSUMER_SECRET, TokenType.REQUEST_TOKEN, TokenType.ACCESS_TOKEN});
    }

    /**
     * Method to query the Twitter API by just defining the search expression, beginning after https://api.twitter.com/1.1/. This method uses pin-based authorization.
     *
     * @param path Path beginning after the first question mark.
     * @return Response in form of an {@link JSONObject}.
     * @version 0.1
     *
     */
    public JSONObject queryNode(String path)
    {
        Token consumerKey = getToken(TokenType.CONSUMER_KEY);
        Token consumerSecret = getToken(TokenType.CONSUMER_SECRET);
        Token requestToken = getToken(TokenType.REQUEST_TOKEN);
        Token accessToken = getToken(TokenType.ACCESS_TOKEN);

        OAuthService service = new ServiceBuilder().provider(TwitterApi.SSL.class).apiKey(consumerKey.getToken()).apiSecret(consumerSecret.getToken()).build();

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
                setToken(TokenType.ACCESS_TOKEN, accessToken);
            }
            fireTokenChanged();
        }

        OAuthRequest request = new OAuthRequest(Verb.GET, getBasicAPIPath() + path);
        service.signRequest(accessToken, request);
        Response response = request.send();

        return JSONHelper.getJSONObject(response.getBody());
    }
}