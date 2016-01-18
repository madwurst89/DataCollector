package Stoecker.Karsten.Helper;

/**
 * @author Karsten Stoecker
 * @date 18.01.2016
 * @version 0.1
 */
public class StringHelper
{
    /**
     * Method to check and remove double line breaks in a {@link String}. Method should be used after {@method checkForTripleLineBreaks}.
     *
     * @param str {@link String} which should be checked.
     * @return {@link String} which contains no double line breaks.
     * @version 0.1
     * @deprecated
     *
     */
    public static String checkForDuplicateLinebreaks(String str)
    {
        return str.replace("\n\n","\n");
    }

    /**
     * Method to check and remove triple line breaks in a {@link String}. Method should be used before {@method checkForDuplicateLinebreaks}.
     *
     * @param str {@link String} which should be checked.
     * @return {@link String} which contains no triple line breaks.
     * @version 0.1
     * @deprecated
     *
     */
    public static String checkForTripleLineBreaks(String str)
    {
        return str.replace("\n\n\n","\n\n");
    }

    /**
     * Method to replace URL-Encoding encoded elements by there ASCII representation.
     *
     * @param s {@link String} which contains URL-Encoding elements.
     * @return {@link String} where URL-Encoding encoded elements were replaced by there ASCII-Sign.
     * @version 0.1
     * @deprecated
     *
     */
    public static String replaceURLEncodingElements(String s)
    {
        s = s.replaceAll("%25", "%");
        s = s.replaceAll("%26", "&");
        s = s.replaceAll("%2F", "/");
        s = s.replaceAll("%3A", ":");
        s = s.replaceAll("%3D", "=");

        return s;
    }
}
