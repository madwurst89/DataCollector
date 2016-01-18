package Stoecker.Karsten.Helper;

import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author Karsten Stoecker
 * @date 15.01.2016
 * @version 0.1
 *
 */
public class XMLHelper
{
    /**
     *
     * Method to transform an {@link JSONObject} to an XML-{@link String}.
     *
     * @param jsonObject {@link JSONObject} which should be converted to an XML-{@link String}.
     * @return XML-{@link String}.
     * @version 0.1
     *
     */
    public static String getXMLString(JSONObject jsonObject)
    {
        return XML.toString(jsonObject);
    }

    /**
     *
     * Method to check if XML-{@link String} contains  an root element. If not an root element is added.
     *
     * @param str XML-{@link String} which should be checked.
     * @return XML-{@link String} which contains root element.
     * @version 0.1
     *
     */
    public static String checkForRootElement(String str)
    {
        if(str.contains("<root>") && str.contains("</root>"))
        {
            return str;
        }
        else
        {
            return "<root>" + str + "</root>";
        }
    }

    /**
     *
     * Method to check if XML-{@link String} contains xml informations (e.g. version, encondig, ...). If not the informations are added.
     *
     * @param str XML-{@link String} which should be checked.
     * @return ML-{@link String} which contains xml informations.
     * @version 0.1
     *
     */
    public static String checkForXMLInformations(String str)
    {
        if(str.contains("<?xml") && str.contains("version=\"") && str.contains("encoding=\"") && str.contains("standalone=\"") && str.contains("?>"))
        {
            return str;
        }
        else
        {
            return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + str;
        }
    }
}
