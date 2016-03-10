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

    /**
     * Method to query a node of client related service/network.
     *
     * @param path of the node, which should be queried, without Basic-API-Path.
     * @return node in the form of an {@link JSONObject}.
     */
    public JSONObject queryNode(String path);

    /**
     * Method to get all types of token, which these client need.
     *
     * @return {@link String}-Array with the names of all needed token types.
     */
    public String[] getRequiredTokenTypes();

    /**
    * Method to get the Basic-API-Path as {@link String}.
            *
            * @return Basic-API-Path as {@link String}.
    */
    public String getBasicAPIPath();

    /**
     * Method to store tokens directly into the {@link Client}.
     *
     * @param tokenType which should be stored.
     * @param token which should be stored.
     */
    public void setToken(String tokenType, String token);
}
