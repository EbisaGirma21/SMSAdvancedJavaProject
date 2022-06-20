package BaseClass;

public class Teacher extends User {
    String subject;
    static String currentUser;
    static String currentUserId;

    public static String getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(String currentUserId) {
        Teacher.currentUserId = currentUserId;
    }

    public Teacher(String userId, String firstName, String middleName, String lastName, String gender, String subject,
            String username, String password) {
        super(firstName, middleName, lastName, userId, gender, username, password);
        this.subject = subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String User) {
        currentUser = User;
    }

    @Override
    public String toString() {

        return (getUserId() + "     " + getFirstName() + "     " + getLastName());
    }
}