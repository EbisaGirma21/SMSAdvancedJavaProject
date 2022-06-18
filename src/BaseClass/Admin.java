package BaseClass;

public class Admin extends User {
    static String currentUser;

    public Admin(String firstName, String middleName, String lastName, String userId, String gender,
            String username, String password) {
        super(firstName, middleName, lastName, userId, gender, username, password);

    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String User) {
        currentUser = User;
    }

}