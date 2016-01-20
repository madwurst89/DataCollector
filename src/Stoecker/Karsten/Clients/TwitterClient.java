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

/**
 *
 * @author Karsten Stoecker
 * @date 18.01.2016
 * @version 0.1
 *
 */
public class TwitterClient implements Client
{
    private String consumerKey;
    private String consumerSecret;

    private Token requestToken;
    private Token accessToken;

    private static String basicAPIPath = "https://api.twitter.com/1.1/";

    private static final String[] tokenTypes = {"Consumer key", "Consumer secret", "Request token", "Access token"};

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
        // based on example from https://github.com/scribejava/scribejava/wiki/Getting-Started
        OAuthService service = new ServiceBuilder().provider(TwitterApi.SSL.class).apiKey(consumerKey).apiSecret(consumerSecret).build();

        if(requestToken == null || accessToken == null) {
            requestToken = service.getRequestToken();
            String authUrl = service.getAuthorizationUrl(requestToken);

            System.out.println("Open " + authUrl + " and authorize app.");
            System.out.println("Enter pin:");

            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String verifierString = null;
            try {
                verifierString = bufferRead.readLine();
            } catch (IOException e) {
                System.out.println("Pin could not be read.");
                System.exit(0);
            }

            Verifier v = new Verifier(verifierString);
            accessToken = service.getAccessToken(requestToken, v);
        }

        OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/" + path);
        service.signRequest(accessToken, request);
        Response response = request.send();

        return JSONHelper.getJSONObject(response.toString());
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
     * Method to change consumer key token.
     *
     * @param consumerKey consumer key
     * @version 0.1
     *
     */
    public void setConsumerKey(String consumerKey)
    {
        this.consumerKey = consumerKey;
    }

    /**
     * Method to change consumer secret token.
     *
     * @param consumerSecret consumer secret
     * @version 0.1
     *
     */
    public void setConsumerSecret(String consumerSecret)
    {
        this.consumerSecret = consumerSecret;
    }
}