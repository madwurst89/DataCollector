package Stoecker.Karsten;

import Stoecker.Karsten.Clients.FacebookClient;
import Stoecker.Karsten.Clients.LinkedInClient;
import Stoecker.Karsten.Clients.TwitterClient;
import Stoecker.Karsten.GUI.MainFrame;
import Stoecker.Karsten.Helper.FileHelper;

/**
 *
 *
 * https://dev.twitter.com/oauth/pin-based
 *
 * @author Karsten Stoecker
 * @date 15.01.2016
 * @version 0.1
 *
 */
public class Main {

    private static FacebookClient facebookClient;
    private static TwitterClient twitterClient;
    private static LinkedInClient linkedInClient;


    public static void main(String[] args)
    {
        String[] tokens = FileHelper.readFromFile("/Volumes/Daten/Desktop/tokens.txt");

        facebookClient = new FacebookClient(tokens[0]);
        twitterClient = new TwitterClient(tokens[1], tokens[2]);
        linkedInClient = new LinkedInClient(args[3], args[4]);

        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
