package Stoecker.Karsten.Modell;

import Stoecker.Karsten.Client.BasicAPIPath;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class represents an prototype implementation of the standard model, which is described in the paper "Vergleichende
 * Betrachtung der Application Programming Interfaces sozialer Netzwerke" -- written by Karsten Stoecker. At version 0.1
 * there are three supported querys:
 *      1 Facebook: {myuserid} oder me
 *      2 Facebook: {myuserid} oder me /friends // including check if an entry already exists
 *      3 Twitter: friends/list.json //followed people
 * The general idea is, to define a "catch clause" for every supported query. This is required cause the rest api is not used
 * as normal. Normally you send a defined request and you know how the response look like and how it must be processed (e.g.
 * overview of your contact people). But the prototype allows it to send any request you want by just typing it to the path
 * text field.
 *
 * @author Karsten Stoecker
 * @version 0.1
 */
public class StandardModel
{
    // Personal- and account-related data
    private JSONObject generalInformation = new JSONObject();
    private JSONObject contactInformation = new JSONObject();
    private JSONObject imageInformation = new JSONObject();
    private JSONObject careerInformation = new JSONObject();
    private JSONObject furtherInformation = new JSONObject();

    private JSONObject contacts = new JSONObject();
    private JSONObject activitys = new JSONObject();
    private JSONObject interests = new JSONObject();


    /**
     * Method to insert query response into the standard model.
     *
     * @param basicApiPath Basic API path which identifies the queried network
     * @param queryPath Query Path which was used for creating queryResult
     * @param queryResult which should be inserted into standard model
     * @return false if response is already part of the model, else true
     */
    public boolean insertQueryResponse(String basicApiPath, String queryPath, JSONObject queryResult)
    {
            if(basicApiPath.equals(BasicAPIPath.facebook)) // facebook query result?
            {
                if(queryPath.equals("me/friends") || queryPath.equals("[0-9]+/friends")) // "friends" - query?
                {
                    JSONArray contactsJSONArray = new JSONArray(); // array of already saved friends for comparison with new query result
                    try
                    {
                        contactsJSONArray = contacts.getJSONArray("facebook");
                    }
                    catch (JSONException e)
                    {
                        // no facebook friends saved till now
                    }

                    JSONArray data = queryResult.getJSONArray("data"); // unzip
                    for(int i = 0 ; i < data.length() ; i++) { // run over friends list
                        String name = data.getJSONObject(i).getString("name");
                        String id = data.getJSONObject(i).getString("id");

                        if(!contactsJSONArray.toString().contains("\"" + id + "\"")) // was friend already saved from query before
                        {
                            JSONObject friend = new JSONObject("{\"" + id + "\":\"" + name + "\"}" );
                            contacts.accumulate("facebook",friend);
                        }
                    }

                    return true;
                }
                else if(queryPath.matches("me")) // "general information about me" - query?
                {
                    String prename = (String) queryResult.get("first_name");
                    String surname = (String) queryResult.get("last_name");
                    if(prename != "" ||surname != "")
                    {
                        JSONObject name = new JSONObject();
                        name.accumulate("value", prename + " " + surname);
                        name.accumulate("source", "facebook");

                        generalInformation.accumulate("names", name);

                        System.out.println(generalInformation.get("names"));
                    }

                    String birthday = (String) queryResult.get("birthday");
                    if(birthday != "")
                    {
                        JSONObject bDay = new JSONObject();
                        bDay.accumulate("value", birthday);
                        bDay.accumulate("source", "facebook");

                        generalInformation.accumulate("birthdates", bDay);
                    }

                    String email = (String) queryResult.get("email");
                    if(email != "")
                    {
                        JSONObject emailAdress = new JSONObject();
                        emailAdress.accumulate("value", email);
                        emailAdress.accumulate("source", "facebook");

                        contactInformation.accumulate("emailadresses", emailAdress);
                    }

                    return true;
                }
            }
            else if(basicApiPath.equals(BasicAPIPath.twitter))
            {
                if(queryPath.equals("friends/list.json")) // "friends" - query?
                {
                    JSONArray getContactsArray = queryResult.getJSONArray("users");
                    JSONArray followedUsers = new JSONArray();
                    for(int a =0 ; a < getContactsArray.length(); a++)
                    {
                        JSONObject followedUser = (JSONObject)getContactsArray.get(a);
                        String name = followedUser.getString("name");
                        String location = followedUser.getString("location");
                        String description = followedUser.getString("description");
                        String url = followedUser.getString("url");
                        String network = "twitter";

                        followedUser = new JSONObject();
                        followedUser.accumulate(name, location);
                        followedUser.accumulate(name, description);
                        followedUser.accumulate(name, url);
                        followedUser.accumulate(name, network);

                        followedUsers.put(followedUser);
                    }

                    contacts.put("followedUsers", followedUsers);
                }

                return false;
            }
            else if(basicApiPath.equals(BasicAPIPath.linkedIn))
            {
            }

        return false;
    }

    @Override
    public String toString()
    {
        String model = new String();

        JSONObject completeModel = new JSONObject();

        completeModel.append("generalInformation", generalInformation);
        completeModel.append("contactInformation", contactInformation);
        completeModel.append("imageInformation", imageInformation);
        completeModel.append("careerInformation", careerInformation);
        completeModel.append("furtherInformation", furtherInformation);
        completeModel.append("contacts", contacts);
        completeModel.append("activitys", activitys);
        completeModel.append("interests", interests);

        return completeModel.toString();
    }

}
