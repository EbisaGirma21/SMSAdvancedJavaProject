package Model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BaseClass.Student;

public class StudentQueries {
    DatabaseAccess database = new DatabaseAccess();
    private CallableStatement selectAll;
    private CallableStatement insertInfo;
    private CallableStatement updtateInfo;
    private CallableStatement deleteInfo;
    private CallableStatement changeUser;
    CallableStatement changepassword;

    public StudentQueries() {
        try {
            Connection connection = database.Connection();
            selectAll = connection.prepareCall("CALL get_all_student();");
            insertInfo = connection.prepareCall("CALL add_student(?,?,?,?,?,?,?,?,?);");
            deleteInfo = connection.prepareCall("CALL delete_student(?);");
            updtateInfo = connection.prepareCall("CALL update_student(?,?,?,?,?);");
            changeUser = connection.prepareCall("Call change_user_student(?,?,?)");
            changepassword = connection.prepareCall("Call change_password_student(?,?,?)");

        } catch (SQLException | ClassNotFoundException e) {

            e.printStackTrace();
        }
    }

    public List<Student> getStudent() {
        try (ResultSet resultSet = selectAll.executeQuery()) {
            List<Student> results = new ArrayList<>();

            while (resultSet.next()) {
                results.add(new Student(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)));
            }
            return results;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public int addStudent(String studID, String FirstName, String MiddleName, String LastName, String Gender,
            String SectionID,
            String Year, String UserName, String Password) {
        try {
            insertInfo.setString(1, studID);
            insertInfo.setString(2, FirstName);
            insertInfo.setString(3, MiddleName);
            insertInfo.setString(4, LastName);
            insertInfo.setString(5, Gender);
            insertInfo.setString(6, SectionID);
            insertInfo.setString(7, Year);
            insertInfo.setString(8, UserName);
            insertInfo.setString(9, Password);

            return insertInfo.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    public int updateStudent(String studID, String FirstName, String MiddleName, String LastName,
            String SectionID) {
        try {
            updtateInfo.setString(1, studID);
            updtateInfo.setString(2, FirstName);
            updtateInfo.setString(3, MiddleName);
            updtateInfo.setString(4, LastName);
            updtateInfo.setString(5, SectionID);

            return updtateInfo.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    public int deleteStudent(String studID) {
        try {
            deleteInfo.setString(1, studID);

            return deleteInfo.executeUpdate();
        } catch (SQLException e) {

            e.printStackTrace();
            return 0;
        }

    }

    public int authenticate(String email, String password, String Passwordfield) {
        try (ResultSet resultSet = selectAll.executeQuery()) {
            int returned = 0;
            while (resultSet.next()) {
                if (resultSet.getString(8).equals(email)
                        && (resultSet.getString(9).equals(password) || resultSet.getString(9).equals(Passwordfield))) {
                    returned = 1;
                    break;
                } else {
                    returned = 0;
                }

            }
            return returned;
        } catch (Exception e) {
            return 0;
        }
    }

    public List<Student> getStudentByEmail(String username) {
        List<Student> results = new ArrayList<>();
        try (ResultSet resultSet = selectAll.executeQuery()) {

            while (resultSet.next()) {
                if (resultSet.getString(8).equals(username)) {
                    results.add(new Student(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getString(8),
                            resultSet.getString(9)));
                    break;
                }

            }
            return results;
        } catch (Exception e) {
            return null;
        }
    }

    public int changeUsername(String userId, String oldUsername, String newUsername) {
        try {
            changeUser.setString(1, userId);
            changeUser.setString(2, oldUsername);
            changeUser.setString(3, newUsername);

            return changeUser.executeUpdate();
        } catch (SQLException e) {

            e.printStackTrace();
            return 0;
        }

    }

    public int ChangePassword(String userId, String oldpassword, String newpassword) {
        try {
            changepassword.setString(1, userId);
            changepassword.setString(2, oldpassword);
            changepassword.setString(3, newpassword);

            return changepassword.executeUpdate();
        } catch (SQLException e) {

            e.printStackTrace();
            return 0;
        }

    }
}
