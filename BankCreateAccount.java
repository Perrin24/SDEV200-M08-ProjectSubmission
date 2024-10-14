import java.io.*;
import java.nio.file.*;
import static java.nio.file.StandardOpenOption.*;
import java.util.*;
import javax.swing.*;

public class BankCreateAccount extends JPanel
{
    // declarations of Labels, Text Fields, and Buttons
    final static private JLabel[] LABELS = {new JLabel("----------Account Creation----------"), new JLabel("        Name:"), new JLabel("Password:")};
    final private JTextField[] FIELDS = {new JTextField(12), new JTextField(12)};
    final static private JButton CONFIRM_BUTTON = new JButton("Confirm"), RETURN_BUTTON = new JButton("Return");

    public BankCreateAccount() // basic constructor that runs seperate function for setting up to panel
    {
        setup();
    }

    private void setup() // pretty much the "real constructor." I just wanted to be able to reset things easily
    {
        for (int i = 0; i < LABELS.length; ++i) // for loop to add labels and text fields to panel
        {
            add(LABELS[i]);
            if(i > 0)
            {
                add(FIELDS[i-1]);
                FIELDS[i-1].setText("");
            }
        }
        // adds buttons and gives them ActionListeners
        add(CONFIRM_BUTTON);
        add(RETURN_BUTTON);
        CONFIRM_BUTTON.addActionListener(e -> confirmAction());
        RETURN_BUTTON.addActionListener(e -> returnAction()); // Return needs ActionListeners in both BankApp and this to clean up the panel for the next use
    }

    private void confirmAction()
    {
        // checks if the required Name and Password fields have been filled and outputs error if not
        if (!FIELDS[0].getText().equals("") && !FIELDS[1].getText().equals(""))
        {

            String id = getID(); // generates id and creates account file
            if (id.equals("\u0000")) // if getID() failed to generate an id or file it will return "\u0000" to output error message
            {
                JOptionPane.showMessageDialog(null, "Internal Account Creation Error", "Error", 0);
                return;
            }

            // displays account information
            removeAll();

            add(new JLabel("Name: " + FIELDS[0].getText()));
            add(new JLabel("Password: " + FIELDS[1].getText()));
            add(new JLabel("ID: " + id));

            add(RETURN_BUTTON);
            validate();
            repaint();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Error: Missing Name or Password", "Error", 0);
        }
    }

    private void returnAction() // cleans up pane for next use
    {
        removeAll();

        setup();
    }

    public JButton getReturn() // sends RETURN_BUTTON to BankApp so it can give it an ActionListener
    {
        return RETURN_BUTTON;
    }

    private String getID()
    {
        StringBuilder id = new StringBuilder(""); // StringBuilder to set up Account ID
        Random rand = new Random(); // Random Number Generator
        int counter = 0; // counter to prevent infinite loops
        
        do{
            for (int i = 0; i < 4; ++i) // for loop to generate 4 digit ID
                id.replace(i, 4, String.valueOf(rand.nextInt(10))); // if this were to be run on a larger scale using this Random method would be inefficient
                                                                              // but at this scale it is acceptable

            File test = new File(".\\Accounts\\" + id.toString() + ".txt");

            if (!test.exists()) // tests if account with generated ID exists. if yes it goes back through the loop and generates a new one.
            {
                new File(".\\Accounts\\").mkdir(); // makes sure the directory exists

                //  file output stuff
                Path file = Paths.get(".\\Accounts\\" + id.toString() + ".txt");
                String s = FIELDS[0].getText() + "\u0000" + FIELDS[1].getText() + "\u0000" + "0.00"; // prepares string with account info.
                                                                                                     // I use nulls as dividers so I don't worry if the user has commas in their inputs
                byte[] data = s.getBytes();
                OutputStream output;
                try
                {
                    output = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
                    output.write(data);
                    output.flush();
                    output.close();
                }
                catch (IOException e) // if an error occurs with file output it will return "\u0000" to indicate something went wrong
                {
                    return "\u0000";
                }

                return id.toString();
            }
            
            if (++counter > 9999) // keeps track of how many times it's generated a new ID number and will return with error if it goes over 9999 attempts
                return "\u0000";
        } while (true);
    }
}