package Stoecker.Karsten.GUI.Panel;

import Stoecker.Karsten.Clients.Client;
import Stoecker.Karsten.Helper.FileHelper;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.LinkedHashMap;

public class ClientPanel extends JPanel
{
    Client client;
    LinkedHashMap<String, JTextField> tokenTextFields = new LinkedHashMap<>();
    JTextArea textArea = new JTextArea();
    JLabel queryLabel;
    JTextField queryTextField = new JTextField();

    public ClientPanel(Client client)
    {
        this.client = client;
        this.setLayout(new BorderLayout());
        this.add(initializeTokenPanel(client.getTokenTypes()), BorderLayout.PAGE_START);
        textArea.setWrapStyleWord( true );
        textArea.setLineWrap(true);
        this.add(textArea, BorderLayout.CENTER);
        this.add(initializeQueryPanel(), BorderLayout.PAGE_END);
    }

    private JPanel initializeTokenPanel(String[] token)
    {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(token.length, 1));

        JPanel textFieldPanel = new JPanel();
        textFieldPanel.setLayout(new GridLayout(token.length, 1));

        for(String tokenFieldString : token)
        {
            JTextField tokenTextField = new JTextField();

            tokenTextField.setEditable(false);
            tokenTextField.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileFilter(new FileNameExtensionFilter("Token files (.tok)","tok"));

                    fileChooser.showOpenDialog(null);

                    File selectedFile = fileChooser.getSelectedFile();

                    JSONObject tokens = FileHelper.readJSONObjectFromFile(selectedFile);


                    for(String string : tokenTextFields.keySet()) // laufe über alle Token-Textfelder
                    {
                        if(tokens.has(string.toLowerCase())) // Key für Textfield in JSONObject (gelesen aus Datei)?
                        {
                            ((JTextField) tokenTextFields.get(string)).setText(tokens.getString(string.toLowerCase()));
                            client.setToken(string, tokens.getString(string.toLowerCase()));
                        }
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            this.tokenTextFields.put(tokenFieldString, tokenTextField);
            labelPanel.add(new JLabel(tokenFieldString));
            textFieldPanel.add(tokenTextField);
        }

        topPanel.add(labelPanel, BorderLayout.LINE_START);
        topPanel.add(textFieldPanel, BorderLayout.CENTER);
        return topPanel;
    }

    private JPanel initializeQueryPanel()
    {
        queryLabel = new JLabel(client.getBasicAPIPath());

        JPanel queryPanel = new JPanel();
        queryPanel.setLayout(new BorderLayout());

        queryPanel.add(queryLabel, BorderLayout.LINE_START);
        queryPanel.add(queryTextField, BorderLayout.CENTER);

        JButton queryButton = new JButton("Send");
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(queryTextField.getText() != "")
                {
                    JSONObject result = client.queryNode(queryTextField.getText().trim());
                    textArea.setText(result.toString());
                }
            }
        });

        queryPanel.add(queryButton, BorderLayout.LINE_END);

        return queryPanel;
    }
}
