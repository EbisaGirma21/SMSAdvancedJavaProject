package Model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BaseClass.Teacher;

public class TeacherQueries {
    DatabaseAccess database = new DatabaseAccess();
    CallableStatement selectAll;
    CallableStatement insertInfo;
    CallableStatement updtateInfo;
    CallableStatement deleteInfo;
    CallableStatement changeUser;
    CallableStatement changepassword;
    CallableStatement selectUsassigned;

    public TeacherQueries() {
        try {
            Connection connection = database.Connection();
            selectAll = connection.prepareCall("CALL get_teacher();");
            selectUsassigned = connection.prepareCall("CALL get_unassigned_teacher();");
            insertInfo = connection.prepareCall("CALL add_teacher(?,?,?,?,?,?,?,?);");
            deleteInfo = connection.prepareCall("CALL delete_teacher(?);");
            updtateInfo = connection.prepareCall("CALL update_teacher(?,?,?,?,?);");
            changeUser = connection.prepareCall("Call change_user_teacher(?,?,?)");
            changepassword = connection.prepareCall("Call change_password_teacher(?,?,?)");

        } catch (SQLException | ClassNotFoundException e) {

            e.printStackTrace();
        }
    }

    public List<Teacher> getTeacher() {
        try (ResultSet resultSet = selectAll.executeQuery()) {
            List<Teacher> results = new ArrayList<>();

            while (resultSet.next()) {
                results.add(new Teacher(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8)));
            }
            return results;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public int addTeacher(String userId, String firstName, String middleName, String lastName, String gender,
            String subject, String username, String password) {
        try {
            insertInfo.setString(1, userId);
            insertInfo.setString(2, firstName);
            insertInfo.setString(3, middleName);
            insertInfo.setString(4, lastName);
            insertInfo.setString(5, gender);
            insertInfo.setString(6, subject);
            insertInfo.setString(7, username);
            insertInfo.setString(8, password);

            return insertInfo.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    public int updateTeacher(String teachID, String FirstName, String MiddleName, String LastName,
            String subject) {
        try {
            updtateInfo.setString(1, teachID);
            updtateInfo.setString(2, FirstName);
            updtateInfo.setString(3, MiddleName);
            updtateInfo.setString(4, LastName);
            updtateInfo.setString(5, subject);

            return updtateInfo.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    public int deleteTeacher(String teachID) {
        try {
            deleteInfo.setString(1, teachID);

            return deleteInfo.executeUpdate();
        } catch (SQLException e) {

            e.printStackTrace();
            return 0;
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

    public List<Teacher> getAdminByEmail(String username) {
        List<Teacher> results = new ArrayList<>();
        try (ResultSet resultSet = selectAll.executeQuery()) {

            while (resultSet.next()) {
                if (resultSet.getString(7).equals(username)) {
                    results.add(new Teacher(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getString(8)));
                    break;
                }

            }
            return results;
        } catch (Exception e) {
            return null;
        }
    }

    public int authenticate(String email, String password, String Passwordfield) {
        try (ResultSet resultSet = selectAll.executeQuery()) {
            int returned = 0;
            while (resultSet.next()) {
                if (resultSet.getString(7).equals(email)
                        && (resultSet.getString(8).equals(password) || resultSet.getString(8).equals(Passwordfield))) {
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

    public int isThere(String secId) {
        try (ResultSet resultSet = selectAll.executeQuery()) {
            int returned = 0;
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(secId)) {
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

    public List<Teacher> getUnassignedTeacher() {
        try (ResultSet resultSet = selectUsassigned.executeQuery()) {
            List<Teacher> results = new ArrayList<>();

            while (resultSet.next()) {
                results.add(new Teacher(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8)));
            }
            return results;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
