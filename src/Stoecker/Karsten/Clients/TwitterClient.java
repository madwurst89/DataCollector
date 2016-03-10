package Stoecker.Karsten.Clients;

import Stoecker.Karsten.Helper.JSONHelper;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.LinkedHashMap;

/**
 *
 * @author Karsten Stoecker
 * @date 18.01.2016
 * @version 0.2
 *
 */
public class TwitterClient implements Client
{
    private static final String basicAPIPath = "https://api.twitter.com/1.1/";

    private LinkedHashMap<String, Token> tokens;
    private static final String[] tokenTypes = {"Consumer key", "Consumer secret", "Request token", "Access token"};
    private Token requestToken;
    private Token accessToken;
    private Verifier verifier;

    public TwitterClient()
    {
        tokens = new LinkedHashMap<>();

        for(String tokenType : tokenTypes)
        {
            tokens.put(tokenType, new Token("", ""));
        }
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
        Token consumerKey = tokens.get("Consumer key");
        Token consumerSecret = tokens.get("Consumer secret");

        OAuthService service = new ServiceBuilder().provider(TwitterApi.SSL.class).apiKey(consumerKey.getToken()).apiSecret(consumerSecret.getToken()).build();

        if(verifier == null)
        {
            requestToken = service.getRequestToken();
            String authUrl = service.getAuthorizationUrl(requestToken);

            if(Desktop.isDesktopSupported())
            {
                try {
                    Desktop.getDesktop().browse(new URL(authUrl).toURI());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String verifierString = (String) JOptionPane.showInputDialog(null, "Authorize app and enter pin:", "Enter pin", JOptionPane.PLAIN_MESSAGE, null, null, null);

            if(verifierString != "" && verifierString != null)
            {
                Verifier v = new Verifier(verifierString);
                accessToken = service.getAccessToken(requestToken, v);
            }
        }

        OAuthRequest request = new OAuthRequest(Verb.GET, basicAPIPath + path);
        service.signRequest(accessToken, request);
        Response response = request.send();

        return JSONHelper.getJSONObject(response.getBody());
    }

    @Override
    public String[] getRequiredTokenTypes() {
        return tokenTypes;
    }

    @Override
    public String getBasicAPIPath() {
        return basicAPIPath;
    }

    /**
     * Method to change consumer key.
     *
     * @param tokenType Type of token which should be set.
     * @param token Token which should be set.
     * @version 0.1
     *
     */
    public void setToken(String tokenType, String token)
    {
        tokens.put(tokenType, new Token(token, ""));
    }
}