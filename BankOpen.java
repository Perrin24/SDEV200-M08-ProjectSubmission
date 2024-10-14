import javax.swing.*;

public class BankOpen extends JPanel
{
    // array of JButtons 
    final static private JButton[] BUTTONS = {new JButton("Login"), new JButton("Create Account"), new JButton("Exit")};

    public BankOpen() // BankOpen constructor. Sets up the panel
    {
        add(new JLabel("Welcome to the"));
        add(new JLabel("Bank of Banking"));

        for (JButton b : BUTTONS)
            add(b);

    }

    public JButton[] getButtons() // method for returning BUTTONS to BankApp. Important because BankApp needs to create ActionListeners for them
    {
        return BUTTONS;
    }
}