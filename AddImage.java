import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AddImage extends JFrame
{
    JLabel imageLabel;
    JButton browseButton;
    JPanel center = new JPanel();
    JPanel bottom = new JPanel();
    JButton addImage = new JButton();
    String selectedImagePath;
    ImageIcon imageIcon;
    Image image;
    public AddImage(String name, String organization, String date, String email, String resources, String additional)
    {
        super("Add Image Of Logo");
        setSize(600, 500);
        setLayout(new BorderLayout(7, 1));
        this.setLocationRelativeTo(null);

        imageLabel = new JLabel(); // Label to display image.
        imageLabel.setPreferredSize(new Dimension(300, 300)); // This will scale the image.

        browseButton = new JButton("Browse your images"); // Browse button.
        setDefaultButton(browseButton);
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(); // JFileChooser to allow user to choose a file from their device.
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif"); // Filtering all valid types of files.
                fileChooser.setFileFilter(filter);

                int returnValue = fileChooser.showOpenDialog(null); // Getting the return value.

                if (returnValue == JFileChooser.APPROVE_OPTION) { // If they choose a valid file.
                    selectedImagePath = fileChooser.getSelectedFile().getPath(); // Obtain file path.
                    imageIcon = new ImageIcon(selectedImagePath);
                    image = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH); // Scaling the image.
                    imageIcon = new ImageIcon(image);
                    imageLabel.setIcon(imageIcon); // Adding the ImageIcon to the label.
                }
            }
        });

        center.add(imageLabel); // Adding components.
        center.add(browseButton);

        bottom.setBackground(Color.pink);
        setDefaultButton(addImage);
        addImage.setText("Add Image");
        bottom.add(addImage);
        addImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedImagePath != null) { // If there is a selected file path.
                    // Create Partner object once we gather all the information.
                    Partner p1 = new Partner(name, organization, date, email, resources, additional, selectedImagePath);
                    AddImage.this.dispose(); // Close the window after selecting an image
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an image by clicking the BROWSE button", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.add(center, BorderLayout.CENTER);
        this.add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }
    public void setDefaultButton(JButton button) // Default button format.
    {
        button.setPreferredSize(new Dimension(300, 60));
        Font buttonFont = new Font("Serif", Font.BOLD, 25);
        button.setFont(buttonFont);
        button.setBackground(Color.pink);
        button.setForeground(Color.gray);
    }
}