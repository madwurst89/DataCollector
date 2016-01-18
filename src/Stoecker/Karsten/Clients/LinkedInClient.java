package Stoecker.Karsten.Clients;

import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

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

    public LinkedInClient(String consumerKey, String consumerSecret)
    {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
    }


    @Override
    public JSONObject queryNode(String path) {

        OAuthService service = new ServiceBuilder().provider(LinkedInApi.class).apiKey(consumerKey).apiSecret(consumerSecret).build();

        if(requestToken == null || accessToken == null)
        {
            requestToken = service.getRequestToken();
            System.out.println(requestToken.toString());
        }

        return null;
    }
}
