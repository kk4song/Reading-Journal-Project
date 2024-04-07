import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataWindow extends JFrame {
    private JTextField name = new JTextField(30);
    private JTextField email = new JTextField(30);
    private JTextArea additional = new JTextArea(4, 25);

    private JTextField dateTextField;
    private JTextField resources = new JTextField(30);
    private JLabel addPartner;
    private JLabel inputName;
    private JLabel inputPages;
    private JLabel inputEmail;
    private JLabel inputDate;
    private JLabel inputAdditional;
    private JLabel inputResources;
    private JButton updatePartner;
    private JPanel top, bottom, second, third, fourth, fifth, sixth, seventh;
    private JComboBox organizations;

    public DataWindow() {
        super("Add a Partner");
        setSize(1150, 650);
        setLayout(new GridLayout(8, 1));
        this.setLocationRelativeTo(null); // Window shows up in the middle.

        Font label = new Font("Serif", Font.BOLD, 25);
        Font text = new Font("Arial", Font.PLAIN, 16);

        top = new JPanel();
        top.setBackground(Color.pink);
        this.add(top);

        addPartner = new JLabel("Enter the information for the partner");
        addPartner.setFont(label);
        addPartner.setForeground(Color.white);

        top.add(addPartner);

        second = new JPanel();
        inputDate = new JLabel("Enter Date: ");

        inputDate.setFont(label);
        dateTextField = new JTextField(10);
        dateTextField.setText("MM/DD/YYYY");
        dateTextField.setFont(text);
        second.add(inputDate);
        second.add(dateTextField);
        this.add(second);

        // Creating JComboBox to allow use to choose an organization type.
        String[] organizationType = {"Sunny", "Cloudy", "Rainy", "Snowy", "Freezing Rain"};
        organizations = new JComboBox(organizationType);

        // Creating label and adding organization type.
        third = new JPanel();
        JLabel inputOrganization = new JLabel("Weather: ");
        inputOrganization.setFont(label);
        third.add(inputOrganization);
        organizations.setFont(text);
        third.add(organizations);

        this.add(third);

        // Area for user to enter date.

        // Creating labels and initializing JTextFields.
        fourth = new JPanel();
        inputName = new JLabel("Electricity Consumption: ");
        inputName.setFont(label);
        fourth.add(inputName);
        name.setFont(text);
        fourth.add(name);

        this.add(fourth);

        fifth = new JPanel();
        inputEmail = new JLabel("Heat Consumption: ");
        inputEmail.setFont(label);
        fifth.add(inputEmail);
        email.setFont(text);
        fifth.add(email);

        this.add(fifth);

        // Allow user to enter resources.
        sixth = new JPanel();
        inputResources = new JLabel("Water Consumption: ");
        inputResources.setFont(label);
        sixth.add(inputResources);
        resources.setFont(text);
        sixth.add(resources);
        this.add(sixth);

        // Additional notes for user to enter about each business/partner formed.
        seventh = new JPanel();
        inputAdditional = new JLabel("Additional Notes: ");
        additional.setLineWrap(true);
        additional.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(additional); // Scroll pane to display full text.
        additional.setFont(text);
        inputAdditional.setFont(label);
        seventh.add(inputAdditional);
        seventh.add(scrollPane);

        this.add(seventh);

        // Button to add a new partner.
        bottom = new JPanel();
        bottom.setBackground(Color.pink);
        updatePartner = new JButton("Add Date");
        updatePartner.setFont(label);
        updatePartner.setBackground(Color.pink);
        bottom.add(updatePartner);

        this.add(bottom);

        updatePartner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!checkEmpty() && isValidDate_(dateTextField.getText())) { // Making sure all textfields/components are full.
                        // Create add image object.
                        AddImage a1 = new AddImage(getDate(), getOrganization(), getName(), getEmail(), getResources(), getAdditional());
                        DataWindow.this.dispose(); // Closing window once informaiton is entered.
                }
            }
        });
        bottom = new JPanel(new BorderLayout());
        setVisible(true);
    }

    // Getter methods.
    public String getName() {
        return name.getText();
    }

    public String getOrganization() {
        return (String) organizations.getSelectedItem();
    }
    public String getEmail() {
        return email.getText();
    }

    public String getResources()
    {
        return resources.getText();
    }
    public String getAdditional() {
        return additional.getText();
    }
    public boolean checkEmpty() // Check if any areas are empty.
    {
        boolean isEmpty = true;
        String checkName = name.getText();
        String checkOrganization = (String) organizations.getSelectedItem();
        String checkDate = dateTextField.getText();
        String checkEmail = email.getText();
        String checkResources = resources.getText();
        String checkAdditional = additional.getText();

        while (isEmpty) {
            if (!checkName.isEmpty() && !checkOrganization.equalsIgnoreCase("") && !checkEmail.isEmpty() && !checkResources.isEmpty() && !checkAdditional.isEmpty() && !checkDate.isEmpty())
            {
                return false;
            }
            else {
                JOptionPane.showMessageDialog(null,"Please make sure to fill in all the text boxes!");
                return true;
            }
        }
        return true;
    }

    // Method to allow user to only enter a valid date.
    public boolean isValidDate_(String inputDate)
    {
        String temp = dateTextField.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy"); // Setting the format of the date.
        dateFormat.setLenient(false);

        do { // Making sure the user enters an integer.
            if (!temp.isEmpty()) {
                try {
                    Date date = dateFormat.parse(inputDate); // if the program is able to parse the date, we know it is valid.
                    return true;
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid date");
                    dateTextField.setText(""); // Clearing text field to allow user to re-enter.
                }
            }
        } while (temp.isEmpty());
        return false;
    }
    public String getDate()
    {
        return dateTextField.getText();
    }
}