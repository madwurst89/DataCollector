package Stoecker.Karsten.Clients;

import Stoecker.Karsten.Helper.JSONHelper;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Karsten Stoecker
 * @date 20.01.2016
 * @version 0.1
 *
 */
public class LinkedInClient implements Client
{
    private String consumerKey;
    private String consumerSecret;

    private Token requestToken;
    private Token accessToken;

    private static String basicAPIPath = "https://api.linkedin.com/v1/";

    private static final String[] tokenTypes = {"Consumer key", "Consumer secret", "Request token", "Access token"};

    @Override
    public JSONObject queryNode(String path) {

        OAuthService service = new ServiceBuilder().provider(LinkedInApi.class).apiKey(consumerKey).apiSecret(consumerSecret).build();

        if(requestToken == null || accessToken == null)
        {
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

            OAuthRequest request = new OAuthRequest(Verb.GET, basicAPIPath + path +"?format=json");
            service.signRequest(accessToken, request);
            Response response = request.send();

            return JSONHelper.getJSONObject(response.getBody());
        }

        return null;
    }

    @Override
    public String[] getTokenTypes() {
        return tokenTypes;
    }

    @Override
    public String getBasicAPIPath() {
        return basicAPIPath;
    }
}
