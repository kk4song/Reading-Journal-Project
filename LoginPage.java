import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
public class LoginPage implements ActionListener {

    JFrame frame = new JFrame();
    JButton loginButton = new JButton("Login");
    JButton resetButton = new JButton("Reset");
    JTextField userIDField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();
    JLabel userIDLabel = new JLabel("Username:");
    JLabel userPasswordLabel = new JLabel("Password:");
    JLabel messageLabel = new JLabel();
    HashMap<String,String> logininfo = new HashMap<String,String>();

    LoginPage(HashMap<String,String> loginInfoOriginal){

        logininfo = loginInfoOriginal;

        // Setting the founds of the different textfields.
        userIDLabel.setBounds(50,100,75,25);
        userPasswordLabel.setBounds(50,150,75,25);

        messageLabel.setBounds(125,250,250,35);
        messageLabel.setFont(new Font(null,Font.ITALIC,25));

        userIDField.setBounds(125,100,200,25);
        userPasswordField.setBounds(125,150,200,25);

        loginButton.setBounds(125,200,100,25);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);

        resetButton.setBounds(225,200,100,25);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);

        // Adding all the components.
        frame.add(userIDLabel);
        frame.add(userPasswordLabel);
        frame.add(messageLabel);
        frame.add(userIDField);
        frame.add(userPasswordField);
        frame.add(loginButton);
        frame.add(resetButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420,420);
        frame.setLayout(null); // No layout manager needed.
        frame.setLocationRelativeTo(null); // Shows up in the middle.
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Checking if the reset button was pressed.
        if(e.getSource()==resetButton) {
            userIDField.setText("");
            userPasswordField.setText(""); // Set both fields to nothing.
        }

        if(e.getSource()==loginButton) { // If the login button was pressed...

            String userID = userIDField.getText();
            String password = String.valueOf(userPasswordField.getPassword());

            // If the username exists.
            if(logininfo.containsKey(userID)) {
                if(logininfo.get(userID).equals(password)) { // If the password is correct.
                    messageLabel.setForeground(Color.green);
                    messageLabel.setText("Login successful!");
                    frame.dispose();
                    MainWindow welcomePage = new MainWindow(userID); // Creating MainWindow object.
                    //showInstructions(); // Showing instructions to the program.
                }
                else {
                    messageLabel.setForeground(Color.red); // If the password does not match.
                    messageLabel.setText("Wrong password");
                }

            }
            else {
                messageLabel.setForeground(Color.red); // If the username does not exist.
                messageLabel.setText("Username not found");
            }
        }
    }

    public static void showInstructions() // Show instructions.
    {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout(1, 1));
        frame.setSize(800, 300);
        frame.setLayout(new GridLayout(1, 1));
        frame.setLocationRelativeTo(null);
        frame.setTitle("Instructions");

        JPanel panel = new JPanel();

        // Using HTML to ensure all the information is displayed.
        /*
        JLabel instructions = new JLabel("<html>Welcome to your virtual partner database<br>Click the ADD PARTNER button to add a partner<br>After your business partner is added, click REFRESH CATALOG to refresh your catalog!<br>You'll be able to sort/filter data in various ways<br>You will also be able to see a visual representation of your business/partners below below");
        panel.add(BorderLayout.CENTER, instructions);
        Font instructionsFont = new Font("Serif", Font.PLAIN, 18);
        instructions.setFont(instructionsFont);
        frame.add(panel);*/

        frame.setVisible(true);
    }
}
