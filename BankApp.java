import java.awt.event.*;
import javax.swing.*;

public class BankApp extends JFrame
{
    // enums to help keep track of menuSwapper method
    enum Menus {OPEN, CREATE, LOGIN, MAIN, EXIT}

    // Opening Menu
    final private BankOpen OPEN_MENU = new BankOpen();
    final private JButton[] OPEN_BUTTONS = OPEN_MENU.getButtons(); // = {login, create, exit}

    // Create Account Menu
    final private BankCreateAccount CREATE_MENU = new BankCreateAccount();
    final private JButton RETURN_BUTTON = CREATE_MENU.getReturn();

    // Login Menu
    final private BankLogin LOGIN_MENU = new BankLogin();
    final private JButton[] LOGIN_BUTTONS = LOGIN_MENU.getConfirm();

    // Main Menu
    final private BankMain MAIN_MENU = new BankMain();
    final private JButton DONE_BUTTON = MAIN_MENU.getOut();

    public BankApp()
    {
        super("Bank of Banking");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuSwapper(Menus.OPEN); // starts the program in the Opening Menu

        setLocationRelativeTo(null); // sets frame in the middle of the screen

        for (int i = 0; i < OPEN_BUTTONS.length; ++i) // for loop to add ActionListeners to the buttons from BankOpen
            OPEN_BUTTONS[i].addActionListener(e -> buttonChecker(e));
        
        LOGIN_BUTTONS[0].addActionListener(e -> buttonChecker(e));
        LOGIN_BUTTONS[1].addActionListener(e -> buttonChecker(e));

        RETURN_BUTTON.addActionListener(e -> menuSwapper(Menus.OPEN));
        DONE_BUTTON.addActionListener(e -> buttonChecker(e));
    
        setVisible(true); // sets frame as visible
    }

    private void menuSwapper(Menus menu) // method for switching the frames panel
    {
        getContentPane().removeAll(); // clears the frame of everything to make room for new panel
        switch (menu)
        {
            case OPEN-> // Opening Menu
                {
                    add(OPEN_MENU);
                    setSize(180, 185);
                    setVisible(true);
                }
            case CREATE-> // Create Account Menu
                {
                    add(CREATE_MENU);
                    setSize(250,150);
                    setVisible(true);
                }
            case LOGIN-> // Login Menu
                {
                    add(LOGIN_MENU);
                    setSize(250,150);
                    setVisible(true);
                }
            case MAIN-> // Main Account Menu
                {
                    add(MAIN_MENU);
                    setSize(200,200);
                    setVisible(true);
                }
            case EXIT-> // Exit Program
                {
                    dispose();
                }
        }
    }

    private void buttonChecker(ActionEvent e)
    {
        // Opening Menu Buttons
        if (e.getSource() == OPEN_BUTTONS[2])
            menuSwapper(Menus.EXIT);
        else if (e.getSource() == OPEN_BUTTONS[1])
            menuSwapper(Menus.CREATE);
        else if (e.getSource() == OPEN_BUTTONS[0])
            menuSwapper(Menus.LOGIN);
        
        // Login Menu Confirm Button
        else if (e.getSource() == LOGIN_BUTTONS[0])
            if (LOGIN_MENU.confirmAction())
            {
                MAIN_MENU.setup(LOGIN_MENU.getID()); // sets up the main menu and transfers ID from BankLogin to BankMain
                menuSwapper(Menus.MAIN);
            }
        // idk why but if I add these as an else if it break the buttons
        if (e.getSource() == LOGIN_BUTTONS[1])
            menuSwapper(Menus.OPEN);
        
        // Main Menu Done Button
        if (e.getSource() == DONE_BUTTON)
            menuSwapper(Menus.OPEN);
    }

    // basic main used only to start the BankApp
    public static void main(String[] args)
    {
        BankApp app = new BankApp();
    }
}