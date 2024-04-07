import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;

public class MainWindow extends JFrame {
    private DefaultListModel<String> partnerListModel; // Display list model.
    private JList<String> partnerList;
    private JPanel namePanel, partnerPanel, buttonPanel, bottomPanel; // 4 panels.
    private JLabel name; // Small name.
    private JLabel mainName, numPartners, search; // Larger name
    private JButton refresh, addPartner, logOut; // Different buttons
    JScrollPane scrollPane; // Scroll pane for the JList.
    int count = countLines(); // Count how many lines there are initially.
    ArrayList<String> partnerTitle; // ArrayList of all partnerTitle to display.
    private JTextField searchTextField;
    private JComboBox sortTypes;
    private JComboBox filterOrganization;

    public MainWindow(String userID) {
        super("Energy Consumption Journal");
        setSize(1650, 1080); // Full screen.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // Set as BorderLayout.

        // Top panel.
        namePanel = new JPanel();
        namePanel.setBackground(Color.pink);
        namePanel.setPreferredSize(new Dimension(1650, 200));

        // Top label creation.
        name = new JLabel();
        name.setText("Lunar Hacks Project");
        Font initial = new Font("Serif", Font.PLAIN, 20);
        ImageIcon logo = new ImageIcon("MainWindow/Screen Shot 2024-02-25 at 3.07.28 PM.png");
        // Scale image size.
        Image scaleImage = logo.getImage().getScaledInstance(50, 45, Image.SCALE_DEFAULT);
        logo = new ImageIcon(scaleImage);

        // Fixing visuals of name label.
        name.setFont(initial);
        name.setIcon(logo);
        name.setForeground(Color.white);

        // Larger name creation.
        mainName = new JLabel();
        JLabel filler = new JLabel("");
        mainName.setText("                     Energy Consumption Journal                     "); // Add space for formatting purposes.
        Font main = new Font("Serif", Font.BOLD, 70);
        mainName.setBounds(100, 100, 70, 70);
        mainName.setFont(main);
        mainName.setForeground(Color.white);

        // Add both names.
        namePanel.add(name, BorderLayout.NORTH);
        namePanel.add(filler);
        namePanel.add(mainName, BorderLayout.NORTH);

        // Panel for JList of names.
        partnerPanel = new JPanel();
        partnerPanel.setBackground(Color.white);
        partnerPanel.setPreferredSize(new Dimension(1000, 680));

        // Create JList.
        partnerList = new JList<>();
        scrollPane = new JScrollPane();

        // Right side of panel.
        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.white);
        buttonPanel.setPreferredSize(new Dimension(650, 680));

        // Create bottom panel.
        bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.pink);
        bottomPanel.setPreferredSize(new Dimension(1650, 200));

        // Adding top three panels.
        this.add(namePanel, BorderLayout.NORTH);
        this.add(partnerPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.EAST);

        // Add partner JButton.
        addPartner = new JButton("Add New Day");
        setDefaultButton(addPartner);
        addPartner.setPreferredSize(new Dimension(275, 60));
        addPartner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataWindow w1 = new DataWindow(); // DataWindow object creation.
                int lines = countLines();
            }
        });

        // Create JList.
        partnerListModel = new DefaultListModel<>();
        partnerList = new JList<>(partnerListModel);
        scrollPane = new JScrollPane(partnerList);
        showCatalog(); // Displaying the partner catalog (partners already entered).
        partnerList.setFont(initial);

        getContentPane().add(scrollPane); // Adding scroll pane.

        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Setting bottomPanel layout.
        JScrollPane pane = new JScrollPane(bottomPanel); // Adding a scroll pane to bottomPanel.

        // Adding both horizontal & vertical scrollbar.
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(pane, BorderLayout.SOUTH); // Adding bottom panel.

        // Creating the ArrayList.
        partnerTitle = new ArrayList<>();

        // Adding the elements from the JList into the ArrayList.
        for (int i = 0; i < partnerListModel.getSize(); i++) {
            String name = partnerListModel.getElementAt(i).trim();
            partnerTitle.add(name);
        }

        // Creating the partner files in the bottom panel; adding the names.
        for (String name : partnerTitle) {
            JPanel filePanel = createFilePanel(name);
            bottomPanel.add(filePanel);
        }

        // MouseListener to detect double click on partnerList.
        partnerList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JList<String> list = (JList<String>) evt.getSource();
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    String selectedFile = list.getModel().getElementAt(index);
                    showData(selectedFile); // Show rating for the selected partner
                }
            }
        });

        // Sorting methods combo box creation.
        String[] sortingMethods = {"Choose Sort", "Sort By Date"}; // The ways in which the user can sort.
        sortTypes = new JComboBox(sortingMethods);
        sortTypes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == sortTypes) {
                    if (sortTypes.getSelectedItem() == "Sort By Name") {
                        String[] files = getArray(); // Creating an array.
                        nameSort(files); // Sorting the array.
                        partnerListModel.removeAllElements(); // Resetting/removing all JList elements.

                        for (int i = 0; i < files.length; i++) {
                            String temp = files[i];
                            String name = temp.substring(0, temp.indexOf(","));
                            partnerListModel.addElement("      " + name); // Re-adding all the sorted elements.
                        }
                    } else if (sortTypes.getSelectedItem() == "Sort By Date") {
                        String[] dates = getArray();

                        dateSort(dates);
                        partnerListModel.removeAllElements(); // Resetting/removing all JList elements.

                        for (int i = 0; i < dates.length; i++) {
                            String temp = dates[i];
                            String name = temp.substring(0, temp.indexOf(","));
                            partnerListModel.addElement("      " + name); // Re-adding all the sorted elements.
                        }
                    }
                }
            }
        });

        // Creating combo box to allow user to filter by type of organization.
        String[] filterType = {"Filter by Type", "Sunny", "Cloudy", "Rainy", "Snowy", "Freezing Rain"};
        filterOrganization = new JComboBox(filterType);
        filterOrganization.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Getting the box selected by user.
                String selectedType = (String) filterOrganization.getSelectedItem();
                if (selectedType.equals("Filter by Type")) // Checking if user selected the base "Filter by Type"
                {
                    // If this is selected, we simply clear all the names and rewrite all the names.
                    String[] showArr = getArray();
                    partnerListModel.removeAllElements(); // Resetting/removing all JList elements.
                    for (int i = 0; i < showArr.length; i++) {
                        String temp = showArr[i];
                        String name = temp.substring(0, temp.indexOf(","));
                        partnerListModel.addElement("      " + name); // Re-adding all the sorted elements.
                    }
                }
                else { // ELse an actual organization type is selected...
                    String type = (String) filterOrganization.getSelectedItem();
                    String[] data = showType(getArray(), type);
                    partnerListModel.removeAllElements(); // Resetting/removing all JList elements.

                    for (int i = 0; i < data.length; i++) {
                        String temp = data[i];
                        partnerListModel.addElement("      " + temp); // Re-adding all the sorted elements.
                    }
                }
            }
        });

        // Refresh Catalog button.
        refresh = new JButton("Refresh Catalog");
        setDefaultButton(refresh);
        refresh.setPreferredSize(new Dimension(275, 60));
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int lines = countLines();

                if (lines != count) { // Getting difference between original lines and new number of lines.
                    int difference = lines - count;
                    updateCatalog(difference); // Update the catalog.
                    count = lines; // Updating the count.
                    numPartners.setText("Total Days Recorded: " + count); // Update number of partners label.
                }
            }
        });

        // Log out button.
        logOut = new JButton("Log Out");
        setDefaultButton(logOut);
        logOut.setPreferredSize(new Dimension(350, 60));
        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // End program.
            }
        });

        // Searching text field creation.
        searchTextField = new JTextField(20);
        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterList();
            }
        });

        // Number of partners label.
        numPartners = new JLabel("Total Number of Days: " + count);
        search = new JLabel("Search");
        // Additional labels for formatting reasons.
        JLabel blank = new JLabel("------------------------------------------------------------------------------");
        JLabel blank1 = new JLabel("------------------------------       --------------");
        JLabel blank2 = new JLabel("----------              -----------------------");
        JLabel blank3 = new JLabel("------------                     --------------------");
        blank.setForeground(Color.white);
        blank1.setForeground(Color.white);
        blank2.setForeground(Color.white);
        blank3.setForeground(Color.white);

        // Setting font, size, etc.
        Font label = new Font("Serif", Font.BOLD, 30);
        search.setFont(label);
        numPartners.setFont(label);
        searchTextField.setFont(label);
        search.setForeground(Color.gray);
        numPartners.setForeground(Color.gray);
        searchTextField.setForeground(Color.gray);
        Font comboB = new Font("Serif", Font.PLAIN, 25);
        sortTypes.setFont(comboB);
        sortTypes.setPreferredSize(new Dimension(200, 30));
        filterOrganization.setFont(comboB);
        filterOrganization.setPreferredSize(new Dimension(200, 30));
        buttonPanel.add(sortTypes);
        buttonPanel.add(filterOrganization);
        buttonPanel.add(blank3);
        buttonPanel.add(numPartners);
        buttonPanel.add(blank);
        buttonPanel.add(search);
        buttonPanel.add(searchTextField);
        buttonPanel.add(blank1);
        buttonPanel.add(refresh);
        buttonPanel.add(addPartner);
        buttonPanel.add(blank2);
        buttonPanel.add(logOut);

        setVisible(true);
    }

    public void setDefaultButton(JButton button) // Default button for efficiency.
    {
        button.setPreferredSize(new Dimension(200, 60));
        Font buttonFont = new Font("Serif", Font.BOLD, 30);
        button.setFont(buttonFont);
        button.setBackground(Color.pink);
        button.setOpaque(true);
        button.setForeground(Color.gray);
    }

    public void showCatalog() // Show JList catalog when program begins.
    {
        try {
            // Reading to program.
            FileReader fr = new FileReader("PartnerData.txt");
            BufferedReader br = new BufferedReader(fr);

            String temp;

            for (int i = 0; i < count; i++) {
                temp = br.readLine();

                String name = temp.substring(0, temp.indexOf(",")); // Only taking the name portion of the line.
                partnerListModel.addElement("      " + name); // Adding the spaces for formatting/aesthetic reasons.
            }
            fr.close();
            br.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCatalog(int difference) // Updating the catalog and partner files.
    {
        try {
            FileReader fr = new FileReader("PartnerData.txt");
            BufferedReader br = new BufferedReader(fr);

            String temp;

            for (int i = 0; i < countLines() - difference; i++) {
                temp = br.readLine(); // Reading the lines that are already displayed.
            }

            for (int i = 0; i < difference; i++) // Reading to the lines we need to add.
            {
                temp = br.readLine();
                String name = temp.substring(0, temp.indexOf(","));
                partnerListModel.addElement("      " + name); // Adding the lines we don't have.
                JPanel filePanel = createFilePanel(name); // Updating the partner files.
                bottomPanel.add(filePanel); // Adding the panel to the bottom panel.
            }
            fr.close();
            br.close();
        } catch (IOException e) {
            System.out.println(e + "ERROR occured");
            JOptionPane.showMessageDialog(null, "Something went wrong. The program is going to end once you click the X");
            System.exit(0); // Ending the program if something goes wrong.
        }
    }

    public int countLines() // Count the number of lines.
    {
        int counter = 0;
        try {
            FileReader fr = new FileReader("PartnerData.txt");
            BufferedReader br = new BufferedReader(fr);

            String temp = br.readLine();

            while (temp != null) {
                if (!temp.trim().isEmpty()) { // Make sure line isn't empty.
                    counter++; // Counting the lines.
                }
                temp = br.readLine();
            }

            fr.close();
            br.close();
        } catch (IOException e) {
            System.out.println(e + "ERROR occured");
            JOptionPane.showMessageDialog(null, "Something went wrong. The program is going to end once you click the X");
            System.exit(0);
        }
        return counter; // Return number of lines.
    }

    public String[] getArray() {
        int counter = 0;
        String[] files;
        try {
            FileReader fr = new FileReader("PartnerData.txt");
            BufferedReader br = new BufferedReader(fr);

            br.mark(1000); // Book marking first line.

            String temp = br.readLine();

            while (temp != null) {
                counter++;
                temp = br.readLine();
            }
            br.reset(); // Going back to first line.

            files = new String[counter]; // Setting the size of the array based off the amount of lines.

            for (int i = 0; i < counter; i++) {
                temp = br.readLine();
                files[i] = temp; // Setting each element of the array to a line.
            }

            br.close();
            fr.close();

            return files;
        } catch (IOException e) {
            System.out.println(e + "ERROR occured");
            JOptionPane.showMessageDialog(null, "Something went wrong. The program is going to end once you click the X");
            System.exit(0); // Ending program if error occurs.
        }

        return null; // Returning nothing if something goes wrong.
    }

    public String[] nameSort(String[] arr) // Sorting by name.
    {
        // Sorting method: Selection Sort
        int indexOfMin;
        String temp;
        for (int i = 0; i < arr.length - 1; i++) {
            indexOfMin = i; // Check with each index.
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j].compareToIgnoreCase(arr[indexOfMin]) < 0) // If another element is smaller.
                {
                    indexOfMin = j; // Index of minimum changes.
                }
            }
            temp = arr[i]; // Temporary place-holder.
            arr[i] = arr[indexOfMin];
            arr[indexOfMin] = temp;
        }
        return arr;
    }

    // Method to sort the date.
    public String dateSort(String[] lines) {
        Arrays.sort(lines, new Comparator<String>() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

            @Override
            public int compare(String line1, String line2) {
                try {
                    // Get the date strings from the lines
                    String[] parts1 = line1.split(",");
                    String[] parts2 = line2.split(",");
                    String dateStr1 = parts1[0]; // Assuming date is always at index 2
                    String dateStr2 = parts2[0];

                    // Parse the dates
                    long time1 = dateFormat.parse(dateStr1).getTime();
                    long time2 = dateFormat.parse(dateStr2).getTime();

                    // Compare the dates
                    return Long.compare(time1, time2);
                } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                    // Handle parsing exception or array index out of bounds
                    e.printStackTrace();
                    return 0; // Default to no change in order
                }
            }
        });
        return null;
    }

    public void showData(String name) // Showing the data when a name is double clicked.
    {
        name = name.trim(); // Trim each name to avoid errors.
        String information = "";
        try {
            FileReader fr = new FileReader("PartnerData.txt");
            BufferedReader br = new BufferedReader(fr);

            String temp = br.readLine();
            while (temp != null) {
                String tempTitle = temp.substring(0, temp.indexOf(",")); // Extract name from data.
                if (tempTitle.compareTo(name) == 0) // Compare if it is equal.
                {
                    information = temp; // If it is, break from the loop.
                    break;
                } else {
                    temp = br.readLine();
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        String[] data = information.split(","); // Splitting each part of data.
        String dataName = data[0];
        String dataOrganization = data[1];
        String dataDate = data[2];
        String dataEmail = data[3];
        String dataResources = data[4];
        String dataThoughts = data[5];
        String dataImage = data[6];

        Font text = new Font("Serif", Font.PLAIN, 25);
        // Creating the frame we will open.
        JFrame frame = new JFrame();
        frame.setSize(600, 800);
        frame.setLayout(new GridLayout(2, 1));
        frame.setLocationRelativeTo(null);

        // Top panel will be the image of the partner.
        JPanel top = new JPanel();
        JLabel imageLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon(dataImage);
        Image scaleImage = logoIcon.getImage().getScaledInstance(300, 350, Image.SCALE_DEFAULT);

        logoIcon = new ImageIcon(scaleImage);
        imageLabel.setIcon(logoIcon);
        top.add(imageLabel);
        top.setBackground(Color.pink);

        // Adding a Grid Layout within a Grid Layout (panel).
        JPanel bottom = new JPanel(new GridLayout(6, 1));
        bottom.setBackground(Color.white);

        // Creating the label for each piece of information and adding it.
        JLabel nameLabel = new JLabel("      Date: " + dataName);
        nameLabel.setFont(text);
        nameLabel.setForeground(Color.gray);
        bottom.add(nameLabel);

        JLabel organizationLabel = new JLabel("      Weather: " + dataOrganization);
        organizationLabel.setFont(text);
        organizationLabel.setForeground(Color.gray);
        bottom.add(organizationLabel);

        JLabel dateLabel = new JLabel("      Electricity Consumption: " + dataDate);
        dateLabel.setFont(text);
        dateLabel.setForeground(Color.gray);
        bottom.add(dateLabel);

        JLabel emailLabel = new JLabel("      Heat Consumption: " + dataEmail);
        emailLabel.setFont(text);
        emailLabel.setForeground(Color.gray);
        bottom.add(emailLabel);

        JLabel resourcesLabel = new JLabel("      Water Consumption: " + dataResources);
        resourcesLabel.setFont(text);
        resourcesLabel.setForeground(Color.gray);
        bottom.add(resourcesLabel);

        JLabel notesLabel = new JLabel("      Additional Notes: " + dataThoughts);
        notesLabel.setFont(text);
        notesLabel.setForeground(Color.gray);
        JScrollPane pane = new JScrollPane(notesLabel); // Scroll pane to display all the thoughts.
        bottom.add(pane);

        frame.add(top);
        frame.add(bottom);

        frame.setVisible(true);
    }

    private JPanel createFilePanel(String name) { // Create new panel for partner when the Refresh Catalog button is pressed.
        JPanel panel = new JPanel(); // Panel for each partner.
        panel.setPreferredSize(new Dimension(80, 100)); // Size of partner.
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Setting border to black in order to distinguish between partners.
        panel.setLayout(new BorderLayout());

        JLabel nameLabel = new JLabel("<html><div style='transform: rotate(-90deg);'>" + name + "</div></html>"); // This line allows the whole name to be seen.
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(nameLabel, BorderLayout.CENTER);

        return panel;
    }

    // For filtering the organization types.
    private void filterList() {
        String searchText = searchTextField.getText().toLowerCase();
        DefaultListModel<String> filteredModel = new DefaultListModel<>();

        // Looping through the list of partners.
        for (int i = 0; i < partnerListModel.getSize(); i++) {
            String item = partnerListModel.getElementAt(i);
            if (item.toLowerCase().contains(searchText)) { // Checking for equality.
                filteredModel.addElement(item); // If it is the right type of organization, add the element to filterModel.
            }
        }
        partnerList.setModel(filteredModel);
    }

    // Getting the array of organizations when the filter JComboBox is clicked.
    public String[] showType(String[] arr, String type) {
        String[] temp = new String[arr.length];
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            String[] data = arr[i].split(",");
            String organization = data[1];
            if (organization.equals(type)) {
                temp[count++] = data[0]; // Increment count after assigning the value
            }
        }

        String[] last = Arrays.copyOf(temp, count); // Create a copy of temp array with correct size

        return last;
    }
}