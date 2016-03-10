package Stoecker.Karsten.Clients;

import Stoecker.Karsten.Helper.JSONHelper;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.XingApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.LinkedHashMap;

public class XingClient implements Client{
    private static String basicAPIPath = "https://api.xing.com/v1/";

    private LinkedHashMap<String, Token> tokens;
    private static final String[] tokenTypes = {"Consumer key", "Consumer secret", "Request token", "Access token"};
    private Token requestToken;
    private Token accessToken;
    private Verifier verifier;

    public XingClient()
    {
        tokens = new LinkedHashMap<>();

        for(String tokenType : tokenTypes)
        {
            tokens.put(tokenType, new Token("", ""));
        }
    }

    private Token getToken(String tokenType)
    {
        return tokens.get(tokenType);
    }

    @Override
    public JSONObject queryNode(String path) {

        Token consumerKey = tokens.get("Consumer key");
        Token consumerSecret = tokens.get("Consumer secret");

        OAuthService service = new ServiceBuilder().provider(XingApi.class).apiKey(consumerKey.getToken()).apiSecret(consumerSecret.getToken()).build();

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
                verifier = new Verifier(verifierString);
                accessToken = service.getAccessToken(requestToken, verifier);
            }
        }

        OAuthRequest request = new OAuthRequest(Verb.GET, basicAPIPath + path + "?format=json");
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