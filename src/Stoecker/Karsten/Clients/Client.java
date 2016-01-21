package Stoecker.Karsten.Clients;

import org.json.JSONObject;

/**
 *
 * @author Karsten Stoecker
 * @date 18.01.2016
 * @version 0.1
 *
 */
public interface Client {

    public JSONObject queryNode(String path);

    /**
     * Method to get all types of token, which these client need.
     *
     * @return {@link String}-Array with the names of all needed token types.
     */
    public String[] getTokenTypes();

    public String getBasicAPIPath();

    public void setToken(String tokenType, String token);
}
