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
}
