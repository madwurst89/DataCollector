package Stoecker.Karsten.GUI.Panel;

import Stoecker.Karsten.Client.Client;
import Stoecker.Karsten.Client.TokenType;
import Stoecker.Karsten.Helper.FileHelper;
import Stoecker.Karsten.Listener.TokenChangedListener;
import org.json.JSONObject;
import org.scribe.model.Token;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.LinkedHashMap;

public class ClientPanel extends JPanel implements TokenChangedListener
{
    Client client;
    LinkedHashMap<String, JTextField> tokenTextFields = new LinkedHashMap<>();
    JTextArea queryResultTextArea = new JTextArea();
    JLabel basicAPIPathLabel;
    JTextField queryTextField = new JTextField();

    public ClientPanel(Client client)
    {
        // general panel initialization
        this.client = client;
        client.addListener(this);
        this.setLayout(new BorderLayout());

        // token panel initialization
        this.add(initializeTokenPanel(client.getRequiredTokenTypeNames()), BorderLayout.PAGE_START);

        // query result text area initialization
        queryResultTextArea.setWrapStyleWord( true );
        queryResultTextArea.setLineWrap(true);
        queryResultTextArea.setEditable(false);
        this.add(queryResultTextArea, BorderLayout.CENTER);

        // query panel initialization
        this.add(initializeQueryPanel(), BorderLayout.PAGE_END);
    }

    private JPanel initializeTokenPanel(String[] tokenTypeNames)
    {
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new GridLayout(tokenTypeNames.length, 1));

        JPanel valuePanel = new JPanel();
        valuePanel.setLayout(new GridLayout(tokenTypeNames.length, 1));

        // initialize textfields for token values
        for(String tokenName : tokenTypeNames)
        {
            JTextField tokenValueTextField = new JTextField();
            tokenValueTextField.setEditable(false);
            tokenValueTextField.addMouseListener(new MouseListener()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    // select file with tokens
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileFilter(new FileNameExtensionFilter("Token files (.tok)","tok"));
                    fileChooser.showOpenDialog(null);
                    File selectedFile = fileChooser.getSelectedFile();

                    if(selectedFile != null)
                    {
                        // read tokens from file
                        JSONObject tokensFromFile = FileHelper.readJSONObjectFromFile(selectedFile);


                        for(String tokenType : tokenTypeNames) // laufe über alle Token-Textfelder
                        {
                            if(tokensFromFile.has(tokenType.toLowerCase())) // Tokentyp in Datei enthalten?
                            {
                                ((JTextField) tokenTextFields.get(tokenType)).setText(tokensFromFile.getString(tokenType.toLowerCase()));

                                Token token = new Token(tokensFromFile.getString(tokenType.toLowerCase()), "");

                                Integer tokenTypeAsInteger = TokenType.getIntRepresentation(tokenType);

                                client.setToken(tokenTypeAsInteger, token);
                            }
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

            // add
            this.tokenTextFields.put(tokenName, tokenValueTextField);
            namePanel.add(new JLabel(tokenName));
            valuePanel.add(tokenValueTextField);
        }

        containerPanel.add(namePanel, BorderLayout.LINE_START);
        containerPanel.add(valuePanel, BorderLayout.CENTER);

        return containerPanel;
    }

    private JPanel initializeQueryPanel()
    {
        basicAPIPathLabel = new JLabel(client.getBasicAPIPath());

        JPanel queryPanel = new JPanel();
        queryPanel.setLayout(new BorderLayout());

        queryPanel.add(basicAPIPathLabel, BorderLayout.LINE_START);
        queryPanel.add(queryTextField, BorderLayout.CENTER);

        JButton queryButton = new JButton("Send");
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(queryTextField.getText() != "")
                {
                    JSONObject result = client.queryNode(queryTextField.getText().trim());
                    queryResultTextArea.setText(result.toString());
                }
            }
        });

        queryPanel.add(queryButton, BorderLayout.LINE_END);

        return queryPanel;
    }

    @Override
    public void tokenChanged()
    {
        String[] tokenTypes = client.getRequiredTokenTypeNames();
        for(String tokenTypeName : tokenTypes)
        {
            Integer tokenNameAsInteger = TokenType.getIntRepresentation(tokenTypeName);
            tokenTextFields.get(tokenTypeName).setText(String.valueOf(client.getToken(tokenNameAsInteger).getToken()));
        }
    }
}
