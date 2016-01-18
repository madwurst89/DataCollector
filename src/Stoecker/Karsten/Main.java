package Stoecker.Karsten;

import Stoecker.Karsten.Clients.FacebookClient;
import Stoecker.Karsten.Clients.TwitterClient;
import Stoecker.Karsten.Helper.FileHelper;
import Stoecker.Karsten.Helper.XMLHelper;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 *
 *
 * https://dev.twitter.com/oauth/pin-based
 *
 * @author Karsten Stöcker
 * @date 15.01.2016
 * @version 0.1
 *
 */
public class Main {

    public static void main(String[] args) throws IOException, ParseException
    {

        initializationCheck(args);

        FacebookClient facebookClient = new FacebookClient(args[0]);
        JSONObject facebookQueryResponse = facebookClient.queryNode(args[1]);

        String xmlString = XMLHelper.getXMLString(facebookQueryResponse);
        xmlString = XMLHelper.checkForRootElement(xmlString);
        xmlString = XMLHelper.checkForXMLInformations(xmlString);
        FileHelper.writeStringToFile(xmlString, "/Volumes/Daten/Desktop/facebook.xml");

        TwitterClient twitterClient = new TwitterClient(args[2], args[3]);
        JSONObject twitterQueryResponse = twitterClient.queryNode(args[4]);

        xmlString = XMLHelper.getXMLString(twitterQueryResponse);
        xmlString = XMLHelper.checkForRootElement(xmlString);
        xmlString = XMLHelper.checkForXMLInformations("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + xmlString);
        FileHelper.writeStringToFile(xmlString, "/Volumes/Daten/Desktop/twitter.xml");

    }

    private static void initializationCheck(String[] args)
    {
        if(args.length != 5)
        {
            System.out.println("Argument is missing. Please check if the below listed arguments are passed during start.");
            System.out.println("1. Facebook access token - available at https://developers.facebook.com/tools/explorer.");
            System.out.println("2. Facebook query path (e.g. me");
            System.out.println("3. Twitter consumer key - available at https://apps.twitter.com.");
            System.out.println("4. Twitter consumer secret");
            System.out.println("5. Twitter query path");

            System.exit(0);
        }
    }
}
