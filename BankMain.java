import java.io.*;
import java.nio.file.*;
import static java.nio.file.StandardOpenOption.*;
import javax.swing.*;


public class BankMain extends JPanel
{
    // declares labels, buttons, and spinners
    final private JLabel[] LABELS = {new JLabel("---------- Account Menu ----------"), new JLabel("Name: "),
                                                new JLabel("Error"), new JLabel("Balance: $"), new JLabel("Error")};
    final static private JButton OUT_BUTTON = new JButton("Logout"), DEP_BUTTON = new JButton("Deposit"), WITH_BUTTON = new JButton("Withdraw");
    final static private SpinnerModel T_MODEL = new SpinnerNumberModel(0.00, 0.00, 9999.99, 0.01);
    final private JSpinner TRANSACTION = new JSpinner(T_MODEL);

    // declares global variables
    private double balance;
    private String id;
    private String[] array = {"", "", ""};

    // there is no modified constructor

    public void setup(String i)
    {
        id = i; // sets the id to inputted string

        // file reading stuff
        Path file = Paths.get(".\\Accounts\\" + id + ".txt");
        InputStream input;
        try
        {
            input = Files.newInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String s;
            s = reader.readLine();
            array = s.split("\u0000");
            input.close();

            LABELS[2].setText(array[0]); // sets name label to account holder's name
            balance = Double.parseDouble(array[2]); // sets balance to the accounts balance
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Internal Account Reading Error", "Error", 0);
            return;
        }

        for (JLabel l : LABELS)
            add(l);
        LABELS[4].setText(String.format("%.2f", balance));
        add(TRANSACTION); // spinner for $ selection
        add(DEP_BUTTON);
        add(WITH_BUTTON);
        add(OUT_BUTTON);

        // grabs value from TRANSACTION and turns it into a double before sending it to deposit or withdraw respectively
        DEP_BUTTON.addActionListener(e -> deposit(((Number) TRANSACTION.getValue()).doubleValue()));
        WITH_BUTTON.addActionListener(e -> withdraw(((Number) TRANSACTION.getValue()).doubleValue()));
        // logout button cleanup
        OUT_BUTTON.addActionListener(e -> cleanup());
    }

    public JButton getOut()
    {
        return OUT_BUTTON;
    }

    private void deposit(double d) // adds to account's balance by amount in TRANSACTION
    {
        balance += d;

        array[2] = String.format("%.2f", balance);

        updateBalance(); // updates the balance

        LABELS[4].setText(String.format("%.2f", balance));

        setVisible(true);
    }
    private void withdraw(double w) // withdraws from account's balance by amount in TRANSACTION
    {
        balance -= w;

        array[2] = String.format("%.2f", balance);

        updateBalance(); // updates the balance

        LABELS[4].setText(String.format("%.2f", balance));

        setVisible(true);
    }

    private void updateBalance()
    {
        // file output stuff
        Path file = Paths.get(".\\Accounts\\" + id + ".txt");
        String s = array[0] + "\u0000" + array[1] + "\u0000" + array[2];
        byte[] data = s.getBytes();
        OutputStream out;
        try
        {
            Files.newBufferedWriter(file, TRUNCATE_EXISTING); // wipes file to replace the info

            out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            out.write(data);
            out.flush();
            out.close();
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }

    private void cleanup() // cleanup for next use
    {
        LABELS[2].setText("Error");
        LABELS[4].setText("Error");
        TRANSACTION.setValue(0.00);
        balance = 0;
        id = "";
    }
}