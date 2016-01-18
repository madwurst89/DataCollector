package Stoecker.Karsten.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

/**
 *
 * @author Karsten Stoecker
 * @date 18.01.2016
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
     * @version 0.1
     *
     */
    public static JSONObject getJSONObject(String string)
    {
        return new JSONObject(string);
    }

    /**
     *
     * @param jsonObject {@link JSONObject}
     * @version 0.1
     * @deprecated
     *
     */
    public static String extractJSONObject(JSONObject jsonObject)
    {
        String jOString = "";
        Iterator iterator = jsonObject.keySet().iterator();

        while(iterator.hasNext()) {
            String key = (String) iterator.next();

            if(jsonObject.get(key).getClass().equals(JSONArray.class))
            {
                jOString += "\n#### " + key + " ###\n";

                JSONArray jsonArray = (JSONArray) jsonObject.get(key);

                Iterator i = jsonArray.iterator();

                while(i.hasNext())
                {
                    String interimString = JSONHelper.extractJSONObject((JSONObject) i.next());

                    interimString = checkEntryOrder(interimString);
                    interimString = StringHelper.checkForDuplicateLinebreaks(interimString);

                    jOString += "\n" + interimString;
                }
            }
            else if(jsonObject.get(key).getClass().equals(JSONObject.class))        //
            {
                jOString += "\n#### " + key + " ###\n";

                JSONObject jO = (JSONObject) jsonObject.get(key);

                Iterator i = jO.keySet().iterator();

                while (i.hasNext())
                {
                    String k = (String) i.next();
                    jOString = jOString + k + ": " + jO.get(k) + "\n";
                }
            }
            else                                                                    // "normal" entries
            {
                jOString = key + ": " + jsonObject.get(key) + "\n" + jOString;
            }
        }

        return  jOString;
    }

    /**
     *
     * @param str {@link String}
     * @return {@link String}
     * @version 0.1
     * @deprecated
     *
     */
    private static String checkEntryOrder(String str)
    {
        if(str.contains("#"))
        {
            int firstRhombusOccur = str.indexOf("#");

            String substring1 = str.substring(firstRhombusOccur);
            String substring2 = str.substring(0,firstRhombusOccur - 1);

            str = substring1 + substring2;
            str = StringHelper.checkForDuplicateLinebreaks(str);
        }

        return str;
    }
}
