package Stoecker.Karsten.Client;

public final class TokenType {

    public static final int USER_ACCESS_TOKEN = 1;
    public static final int CONSUMER_KEY = 2;
    public static final int CONSUMER_SECRET = 3;
    public static final int REQUEST_TOKEN = 4;
    public static final int ACCESS_TOKEN = 5;

    private TokenType()
    {

    }

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

    public static String[] getStringRepresentation(Integer[] tokenTypeAsInt)
    {
        String[] tokenTypesAsString = new String[tokenTypeAsInt.length];

        for(int index = 0; index < tokenTypeAsInt.length; index++)
        {
            tokenTypesAsString[index] = getStringRepresentation(tokenTypeAsInt[index]);
        }

        return tokenTypesAsString;
    }

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
