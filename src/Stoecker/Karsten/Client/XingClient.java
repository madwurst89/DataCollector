package Stoecker.Karsten.Client;

import Stoecker.Karsten.Helper.JSONHelper;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.XingApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Client for querying data from the social network Facebook.
 *
 * @author Karsten Stoecker
 * @date 19.01.2016
 * @version 0.1
 */
public class XingClient extends Client{

    private Verifier verifier;

    public XingClient()
    {
        super(BasicAPIPath.xing, new int[]{TokenType.CONSUMER_KEY, TokenType.CONSUMER_SECRET, TokenType.REQUEST_TOKEN, TokenType.ACCESS_TOKEN});
    }

    @Override
    public JSONObject queryNode(String path) {

        Token consumerKey = getToken(TokenType.CONSUMER_KEY);
        Token consumerSecret = getToken(TokenType.CONSUMER_SECRET);
        Token requestToken = getToken(TokenType.REQUEST_TOKEN);
        Token accessToken = getToken(TokenType.ACCESS_TOKEN);

        OAuthService service = new ServiceBuilder().provider(XingApi.class).apiKey(consumerKey.getToken()).apiSecret(consumerSecret.getToken()).build();

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

        OAuthRequest request = new OAuthRequest(Verb.GET, getBasicAPIPath() + path + "?format=json");
        service.signRequest(accessToken, request);
        Response response = request.send();

        return JSONHelper.getJSONObject(response.getBody());
    }
}