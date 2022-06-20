package Model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BaseClass.Admin;

public class AdminQueries {
    DatabaseAccess database = new DatabaseAccess();
    CallableStatement selectAll;
    CallableStatement insertInfo;
    CallableStatement updtateInfo;
    CallableStatement deleteInfo;
    CallableStatement changeUser;
    CallableStatement changepassword;

    public AdminQueries() {
        try {
            Connection connection = database.Connection();
            selectAll = connection.prepareCall("Call get_admin()");
            changeUser = connection.prepareCall("Call change_user_admin(?,?,?)");
            changepassword = connection.prepareCall("Call change_password_admin(?,?,?)");
            insertInfo = connection.prepareCall("Call add_admin(?,?,?,?,?,?,?)");

        } catch (SQLException | ClassNotFoundException e) {

            e.printStackTrace();
        }
    }

    public List<Admin> getAdmin() {
        try (ResultSet resultSet = selectAll.executeQuery()) {
            List<Admin> results = new ArrayList<>();

            while (resultSet.next()) {
                results.add(new Admin(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)));
            }
            return results;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public int addAdmin(String FirstName, String MiddleName, String LastName, String SSN, String Gender,
            String Username, String password) {

        try {
            insertInfo.setString(1, FirstName);
            insertInfo.setString(2, MiddleName);
            insertInfo.setString(3, LastName);
            insertInfo.setString(4, SSN);
            insertInfo.setString(5, Gender);
            insertInfo.setString(6, Username);
            insertInfo.setString(7, password);

            return insertInfo.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    public int deleteAdmin(String fname) {
        try {
            deleteInfo.setString(1, fname);

            return deleteInfo.executeUpdate();
        } catch (SQLException e) {

            e.printStackTrace();
            return 0;
        }

    }

    public int authenticate(String username, String password, String Passwordfield) {
        try (ResultSet resultSet = selectAll.executeQuery()) {
            int returned = 0;
            while (resultSet.next()) {
                if (resultSet.getString(6).equals(username)
                        && (resultSet.getString(7).equals(password) || resultSet.getString(7).equals(Passwordfield))) {
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

    public String getId(String username, String password) {
        try (ResultSet resultSet = selectAll.executeQuery()) {
            String returned = "";
            while (resultSet.next()) {
                if (resultSet.getString(6).equals(username) && resultSet.getString(7).equals(password)) {
                    returned = resultSet.getString(4);
                    break;
                } else {
                    returned = "";
                }

            }
            return returned;
        } catch (Exception e) {
            return "";
        }
    }

    public List<Admin> getAdminByEmail(String username) {
        List<Admin> results = new ArrayList<>();
        try (ResultSet resultSet = selectAll.executeQuery()) {

            while (resultSet.next()) {
                if (resultSet.getString(6).equals(username)) {
                    results.add(new Admin(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7)));
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
