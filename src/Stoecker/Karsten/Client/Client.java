package Stoecker.Karsten.Client;

import Stoecker.Karsten.Helper.FileHelper;
import Stoecker.Karsten.Listener.TokenChangedListener;
import Stoecker.Karsten.Modell.StandardModel;
import org.json.JSONObject;
import org.scribe.model.Token;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Class which acts as a template and implements basic functions for a client which is able to query data from a social network.
 *
 * @author Karsten Stoecker
 * @date 18.01.2016
 * @version 0.2
 *
 */
public abstract class Client {

    private String basicAPIPath;
    private LinkedHashMap<Integer, Token> tokens;
    private ArrayList<TokenChangedListener> tokenChangedListener;
    private static StandardModel standardModel = new StandardModel();
    private static boolean shutDownHookAdded = false;


    /**
     * Constructor which should be called from every derived client.
     *
     * @param basicAPIPath The Basic API Path for the social network which should be queried.
     * @param tokenTypes The Token types which are required to query the API of the social network.
     */
    public Client(String basicAPIPath, int[] tokenTypes)
    {
        tokenChangedListener = new ArrayList<>();

        this.basicAPIPath = basicAPIPath;

        tokens = new LinkedHashMap<>();

        for(int tokenType : tokenTypes)
        {
            tokens.put(tokenType, new Token("", ""));
        }

        checkIfShutDownHookIsAlreadyAdded();
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

    /**
     * Method to add an listener to the client, which is informed by every token change.
     *
     * @param listener which should be informed, e.g. a gui element.
     */
    public void addListener(TokenChangedListener listener)
    {
        tokenChangedListener.add(listener);
    }

    /**
     * Method to inform registered listeners about a changed token.
     */
    public void fireTokenChanged()
    {
        for(TokenChangedListener tCL : tokenChangedListener)
        {
            tCL.tokenChanged();
        }
    }

    /**
     * Method to check if the shutdown hook, which is responsible for saving the queried data to a textfile.
     */
    private static synchronized void checkIfShutDownHookIsAlreadyAdded()
    {
        if(!shutDownHookAdded)
        {
            shutDownHookAdded = true;

            Runtime.getRuntime().addShutdownHook( new Thread() {
                @Override public void run() {
                   FileHelper.writeStringToFile(standardModel.toString(), "./savedQueryResponses.txt");
                }
            } );
        }
    }

    /**
     * Method to insert queried data from different networks into one model.
     */
    public static void insertIntoQueriedData(String basicAPIPath, String queryPath, JSONObject queryResult)
    {
        standardModel.insertQueryResponse(basicAPIPath, queryPath, queryResult);
    }
}
