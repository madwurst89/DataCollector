package Stoecker.Karsten.GUI;

import javax.swing.*;
import java.awt.*;


public class MainFrame extends JFrame
{
    private ClientPanel facebookClientPanel;
    private ClientPanel twitterClientPanel;
    private ClientPanel linkedinClientPanel;
    private JTabbedPane clientsTabbedPane;

    public MainFrame()
    {
        clientsTabbedPane = new JTabbedPane();

        ImageIcon facebookIcon = new ImageIcon("/Volumes/Daten/Programmierung/Java/DataCollector/src/Stoecker/Karsten/GUI/ico/facebookIcon512.png");
        facebookClientPanel = new ClientPanel();
        clientsTabbedPane.addTab("facebook", facebookIcon, facebookClientPanel, "Tab for querying the Facebook REST API.");

        ImageIcon twitterIcon = new ImageIcon("/Volumes/Daten/Programmierung/Java/DataCollector/src/Stoecker/Karsten/GUI/ico/twitterIcon512.png");
        twitterClientPanel = new ClientPanel();
        clientsTabbedPane.addTab("twitter", twitterIcon, twitterClientPanel, "Tab for querying the Twitter API.");

        ImageIcon linkedinIcon = new ImageIcon("/Volumes/Daten/Programmierung/Java/DataCollector/src/Stoecker/Karsten/GUI/ico/linkedinIcon512.png");
        linkedinClientPanel = new ClientPanel();
        clientsTabbedPane.addTab("LinkedIn", linkedinIcon, linkedinClientPanel, "Tab for querying the LinkedIn API.");

        clientsTabbedPane.setSelectedIndex(0);

        this.setLayout(new GridLayout(1,1));
        this.add(clientsTabbedPane);
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
    }
}

