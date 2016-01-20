package Stoecker.Karsten.GUI.Frames;

import Stoecker.Karsten.Clients.FacebookClient;
import Stoecker.Karsten.Clients.LinkedInClient;
import Stoecker.Karsten.Clients.TwitterClient;
import Stoecker.Karsten.GUI.Panel.ClientPanel;

import javax.swing.*;
import java.awt.*;


public class MainFrame extends JFrame
{
    private FacebookClient facebookClient;
    private TwitterClient twitterClient;
    private LinkedInClient linkedInClient;

    private ClientPanel facebookClientPanel;
    private ClientPanel twitterClientPanel;
    private ClientPanel linkedinClientPanel;

    private JTabbedPane clientsTabbedPane;

    public MainFrame()
    {
        clientsTabbedPane = new JTabbedPane();

        facebookClient = new FacebookClient();
        ImageIcon facebookIcon = new ImageIcon("/Volumes/Daten/Programmierung/Java/DataCollector/src/Stoecker/Karsten/GUI/ico/facebookIcon512.png");
        facebookClientPanel = new ClientPanel(facebookClient);
        clientsTabbedPane.addTab("facebook", facebookIcon, facebookClientPanel, "Tab for querying the Facebook REST API.");

        twitterClient = new TwitterClient();
        ImageIcon twitterIcon = new ImageIcon("/Volumes/Daten/Programmierung/Java/DataCollector/src/Stoecker/Karsten/GUI/ico/twitterIcon512.png");
        twitterClientPanel = new ClientPanel(twitterClient);
        clientsTabbedPane.addTab("twitter", twitterIcon, twitterClientPanel, "Tab for querying the Twitter API.");

        linkedInClient = new LinkedInClient();
        ImageIcon linkedinIcon = new ImageIcon("/Volumes/Daten/Programmierung/Java/DataCollector/src/Stoecker/Karsten/GUI/ico/linkedinIcon512.png");
        linkedinClientPanel = new ClientPanel(linkedInClient);
        clientsTabbedPane.addTab("LinkedIn", linkedinIcon, linkedinClientPanel, "Tab for querying the LinkedIn API.");

        clientsTabbedPane.setSelectedIndex(0);

        this.setLayout(new GridLayout(1,1));
        this.add(clientsTabbedPane);
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
    }
}

