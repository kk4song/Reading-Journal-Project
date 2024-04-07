
public class Main {
    public static void main(String[] args) {
        UserAndPass user = new UserAndPass(); // Create UserAndPass object.
        LoginPage loginPage = new LoginPage(user.getLoginInfo()); // Create LoginPage object by using the UserAndPass object.
    }
}