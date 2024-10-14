import java.io.*;
import java.nio.file.*;
import javax.swing.*;

public class BankLogin extends JPanel
{
    // declares labels, fields, and buttons
    final static private JLabel[] LABELS = {new JLabel("-------------------- Login --------------------"), new JLabel("                ID:"), new JLabel("Password:")};
    final private JTextField[] FIELDS = {new JTextField(12), new JTextField(12)};
    final static private JButton[] BUTTONS = {new JButton("Confirm"), new JButton("Back")};

    // declares global id variable. need this for transfering id to account's main menu
    private String id;

    public BankLogin() // constructor to set up panel
    {
        for (int i = 0; i < LABELS.length; ++i)
        {
            add(LABELS[i]);
            if(i > 0)
            {
                add(FIELDS[i-1]);
                FIELDS[i-1].setText("");
            }
        }
        for (JButton b : BUTTONS) // adds buttons to panel and gives them cleanup duty
        {
            add(b);
            b.addActionListener(e -> cleanup());
        }
    }

    public Boolean confirmAction() // method that checks if login credentials are correct
    {
        Boolean valid = false; // by default it will assume it is wrong until it sees it is correct

        if(new File(".\\Accounts\\" + FIELDS[0].getText() + ".txt").exists()) // tests if id is correct. if no it is considered wrong
        {
            Path file = Paths.get(".\\Accounts\\" + FIELDS[0].getText() + ".txt");
            InputStream input;
            try
            {
                // file reading stuff
                input = Files.newInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String s;
                String[] array;
                s = reader.readLine();
                array = s.split("\u0000"); // sets up array with account info
                input.close();
                
                if (array[1].equals(FIELDS[1].getText())) // if account password = inputted password then it will proceed to next menu. if not it is considered wrong
                {
                    valid = true;
                    id = FIELDS[0].getText();
                }
            }
            catch (IOException e) // io error message
            {
                JOptionPane.showMessageDialog(null, "Internal Account Login Error", "Error", 0);
            }
        }
        if (valid == false) // if either ID or Password were incorrect it will output this error message
            JOptionPane.showMessageDialog(null, "Invalid ID or Password", "Error", 0);

        return valid; // returns valid's value for use in BankApp
    }

    // gets BUTTONS
    public JButton[] getConfirm()
    {
        return BUTTONS;
    }

    public String getID() // gets ID to transfer to BankMain
    {
        return id;
    }

    private void cleanup() // cleanups up panel for next use
    {
        for (JTextField f : FIELDS)
            f.setText("");
    }
}