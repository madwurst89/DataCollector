package Stoecker.Karsten.Helper;

import org.json.JSONObject;

/**
 * Class which provides methods for transforming from and to a {@link JSONObject}.
 *
 * @author Karsten Stoecker
 * @version 0.1
 *
 */
public class JSONHelper
{
    /**
     * Method to transform a {@link String} to a {@link org.json.JSONObject}.
     *
     * @param string String representation of an JSON object.
     * @return To {@link org.json.JSONObject} transformed {@link String}.
     * @version 0.2
     *
     */
    public static JSONObject getJSONObject(String string)
    {
        if(string.charAt(0) != '{')
        {
            string = "{ \"root\": " + string + " }";
        }

        return new JSONObject(string);
    }
}
