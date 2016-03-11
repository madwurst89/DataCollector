package Stoecker.Karsten.Client;

import org.json.JSONObject;
import org.scribe.model.Token;

import java.util.LinkedHashMap;

/**
 *
 * @author Karsten Stoecker
 * @date 18.01.2016
 * @version 0.1
 *
 */
public abstract class Client {

    private String basicAPIPath;
    private LinkedHashMap<Integer, Token> tokens;

    public Client(String basicAPIPath, int[] tokenTypes)
    {
        this.basicAPIPath = basicAPIPath;

        tokens = new LinkedHashMap<>();

        for(int tokenType : tokenTypes)
        {
            tokens.put(tokenType, new Token("", ""));
        }
    }

    /**
     * Method to query a node of client related service/network. Should be overwritten in extending Class.
     *
     * @param path of the node, which should be queried, without Basic-API-Path.
     * @return node in the form of an {@link JSONObject}.
     */
    public JSONObject queryNode(String path) {
        return null;
    }

    public Token getToken(int tokenType)
    {
        return tokens.get(tokenType);
    }

    /**
     * Method to get all types of token, which these client need.
     *
     * @return {@link Integer}-Array with the names of all needed token types.
     */
    public Integer[] getRequiredTokenTypes()
    {
        return (Integer[]) tokens.keySet().toArray();
    }

    public String[] getRequiredTokenTypeNames()
    {
        String[] tokenTypeNames = new String[tokens.size()];

        int index = 0;

        for(Integer token : tokens.keySet())
        {
            tokenTypeNames[index] = TokenType.getStringRepresentation(token);
            index++;
        }

        return tokenTypeNames;
    }

    /**
     * Method to get the Basic-API-Path as {@link String}.
     *
     * @return Basic-API-Path as {@link String}.
     */
    public String getBasicAPIPath() {
        return basicAPIPath;
    }

    /**
     * Method to store tokens/secrets directly into the {@link Client}.
     *
     * @param tokenType which should be stored.
     * @param token     which should be stored.
     */
    public void setToken(int tokenType, Token token)
    {
        tokens.put(tokenType, token);
    }
}
