package Stoecker.Karsten.Clients;

import Stoecker.Karsten.Helper.JSONHelper;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

/**
 *
 * @author Karsten Stoecker
 * @date 18.01.2016
 * @version 0.1
 *
 */
public class TwitterClient implements Client
{
    private static final String basicAPIPath = "https://api.twitter.com/1.1/";

    private LinkedHashMap<String, Token> tokens;
    private static final String[] tokenTypes = {"Consumer key", "Consumer secret", "Request token", "Access token"};

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
        Token requestToken = tokens.get("Request token");
        Token accessToken = tokens.get("Access token");

        // based on example from https://github.com/scribejava/scribejava/wiki/Getting-Started
        OAuthService service = new ServiceBuilder().provider(TwitterApi.SSL.class).apiKey(consumerKey.getToken()).apiSecret(consumerSecret.getToken()).build();

        if(requestToken.getToken() == "" || accessToken.getToken() == "") {
            requestToken = service.getRequestToken();
            String authUrl = service.getAuthorizationUrl(requestToken);

            System.out.println("Open " + authUrl + " and authorize app.");
            System.out.println("Enter pin:");

            //read pin/veriefierString from terminal
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String verifierString = null;
            try {
                verifierString = bufferedReader.readLine();
            } catch (IOException e) {
                System.out.println("Pin could not be read.");
                System.exit(0);
            }

            Verifier v = new Verifier(verifierString);
            accessToken = service.getAccessToken(requestToken, v);
        }

        OAuthRequest request = new OAuthRequest(Verb.GET, basicAPIPath + path);
        service.signRequest(accessToken, request);
        Response response = request.send();

        return JSONHelper.getJSONObject(response.isSuccessful() + "");
    }

    @Override
    public String[] getTokenTypes() {
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