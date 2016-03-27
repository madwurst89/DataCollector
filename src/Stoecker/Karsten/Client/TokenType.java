package Stoecker.Karsten.Client;

/**
 * Class which acts as a container for the different token types which are needed by diverse social networks.
 */
public final class TokenType {

    public static final int USER_ACCESS_TOKEN = 1;
    public static final int CONSUMER_KEY = 2;
    public static final int CONSUMER_SECRET = 3;
    public static final int REQUEST_TOKEN = 4;
    public static final int ACCESS_TOKEN = 5;

    /**
     * Method to translate the int representation of token type to string representation (e.g. for use at a gui).
     *
     * @param tokenTypeAsInt int representation of token type
     * @return string representation of token type
     */
    public static String getStringRepresentation(int tokenTypeAsInt)
    {
        if(tokenTypeAsInt == USER_ACCESS_TOKEN)
        {
            return "user access token";
        }
        else if(tokenTypeAsInt == CONSUMER_KEY)
        {
            return "consumer key";
        }
        else if(tokenTypeAsInt == CONSUMER_SECRET)
        {
            return "consumer secret";
        }
        else if(tokenTypeAsInt == REQUEST_TOKEN)
        {
            return "request token";
        }
        else if(tokenTypeAsInt == ACCESS_TOKEN)
        {
            return "access token";
        }
        else
        {
            return null;
        }
    }

    /**
     * Method to translate the int representation of a number of token types to string representation (e.g. for use at a gui).
     *
     * @param tokenTypeAsInt int representation of token types
     * @return string representation of token types
     */
    public static String[] getStringRepresentation(Integer[] tokenTypeAsInt)
    {
        String[] tokenTypesAsString = new String[tokenTypeAsInt.length];

        for(int index = 0; index < tokenTypeAsInt.length; index++)
        {
            tokenTypesAsString[index] = getStringRepresentation(tokenTypeAsInt[index]);
        }

        return tokenTypesAsString;
    }

    /**
     * Method to translate the string representation of token type to int representation.
     *
     * @param tokenTypeAsString string representation of token type
     * @return int representation of token type
     */
    public static int getIntRepresentation(String tokenTypeAsString)
    {
        if(tokenTypeAsString.equals("user access token"))
        {
            return USER_ACCESS_TOKEN;
        }
        else if(tokenTypeAsString.equals("consumer key"))
        {
            return CONSUMER_KEY;
        }
        else if(tokenTypeAsString.equals("consumer secret"))
        {
            return CONSUMER_SECRET;
        }
        else if(tokenTypeAsString.equals("request token"))
        {
            return REQUEST_TOKEN;
        }
        else if(tokenTypeAsString.equals("access token"))
        {
            return ACCESS_TOKEN;
        }
        else
        {
            return 0;
        }
    }
}
