package BaseClass;

public class Student extends User {
    String year;
    String sectionId;
    static String currentUser;
    static String currentUserId;

    public Student(String userId, String firstName, String middleName, String lastName, String gender,
            String sectionId, String year, String username, String password) {
        super(firstName, middleName, lastName, userId, gender, username, password);
        this.sectionId = sectionId;
        this.year = year;

    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String User) {
        currentUser = User;
    }

    public static String getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(String currentUserId) {
        Student.currentUserId = currentUserId;
    }

    @Override
    public String toString() {

        return getUserId() + "   " + getFirstName() + "   " + getMiddleName();
    }

}