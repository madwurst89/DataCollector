package Stoecker.Karsten.Modell;

import Stoecker.Karsten.Client.BasicAPIPath;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an prototype implementation of the standard model, which is described in the paper "Vergleichende
 * Betrachtung der Application Programming Interfaces sozialer Netzwerke" -- written by Karsten Stoecker. At version 0.1
 * there are three supported querys:
 *      1
 *      2
 *      3
 * The general idea is, to define a "catch clause" for every supported query. This is required cause the rest api is not used
 * as normal. Normally you send a defined request and you know how the response look like and how it must be processed (e.g.
 * overview of your contact people). But the prototype allows it to send any request you want by just typing it to the path
 * text field.
 *
 * @author Karsten Stoecker
 * @date 12.03.2016
 * @version 0.1
 */
public class StandardModel
{
    // Personal- and account-related data
    private static JSONObject generalInformation = new JSONObject();
    private static JSONObject contactInformation = new JSONObject();
    private static JSONObject imageInformation = new JSONObject();
    private static JSONObject careerInformation = new JSONObject();
    private static JSONObject furtherInformation = new JSONObject();

    private static JSONObject contacts = new JSONObject();
    private static JSONObject activitys = new JSONObject();
    private static JSONObject interests = new JSONObject();

    private static List<Integer> hashValuesOfAlreadyInsertedQueryRespones = new ArrayList<>();

    /**
     * Method to insert query response into the standard model.
     *
     * @param queryResult which should be inserted into standard model
     * @return false if response is already part of the model, else false
     */
    public boolean insertQueryResponse(String basicApiPath, String queryPath, JSONObject queryResult)
    {
        if(!hashValuesOfAlreadyInsertedQueryRespones.contains(queryResult.toString().hashCode())) // avoid double insertion
        {
            if(basicApiPath.equals(BasicAPIPath.facebook)) // query {userid}/friends
            {
                if(queryPath.equals("me/friends"))
                {
                    hashValuesOfAlreadyInsertedQueryRespones.add(queryResult.toString().hashCode());

                    // wenn Eintrag vorhanden, wird Array angelegt.
                    //queriedData.accumulate();

                    return true;
                }
                else if(queryPath.matches("[0-9]+/picture")) // query {pictureid}/picture
                {
                    hashValuesOfAlreadyInsertedQueryRespones.add(queryResult.toString().hashCode());
                    return true;
                }
            }
            else if(basicApiPath.equals(BasicAPIPath.twitter))
            {
                if(true) //lasttweets
                {
                    hashValuesOfAlreadyInsertedQueryRespones.add(queryResult.toString().hashCode());
                    return true;
                }
            }
            else if(basicApiPath.equals(BasicAPIPath.linkedIn))
            {
            }
        }

        return false;
    }

    /**
     * Method to save the {@link JSONObject}, which represents the standard model categories, should be saved.
     *
     * @param path including the file name under which the actual model should be saved
     */
    public static void toString(String path)
    {
        String model = new String();

        model += generalInformation.toString() + "\n";
        model += contactInformation.toString() + "\n";
        model += imageInformation.toString() + "\n";
        model += careerInformation.toString() + "\n";
        model += furtherInformation.toString() + "\n";

        model += contacts.toString() + "\n";
        model += activitys.toString() + "\n";
        model += interests.toString();
    }
}
