import java.io.*;

public class Partner {

    private String name;
    private String organization;
    private String additional;
    private String combined;
    private String imagePath;
    private String email;
    private String resources;
    private String date;

    public Partner(String name, String organization, String date, String email, String resources, String additional, String imagePath) {

        // Initializing all the instance variables.
        this.name = name;
        this.organization = organization;
        this.date = date;
        this.email = email;
        this.resources = resources;
        this.additional = additional;
        this.imagePath = imagePath;

        // This line will be written to the file.
        this.combined = name + "," + organization + "," + date + "," + email + "," + resources + "," + additional + "," + imagePath;

        try {
            // Writing the combined line to the file.
            FileWriter fw = new FileWriter("PartnerData.txt", true);
            PrintWriter br = new PrintWriter(fw);
            br.println(combined);

            fw.close();
            br.close();
        }
        catch (IOException e)
        {
            System.err.println("Error occured" + e);
        }

    }
}
