package Model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BaseClass.Mark;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MarkQueries {
    DatabaseAccess database = new DatabaseAccess();
    Connection connection = database.connection;
    CallableStatement selectAll;
    CallableStatement insertInfo;
    CallableStatement updtateInfo;
    CallableStatement deleteInfo;
    CallableStatement getMarkById;
    CallableStatement getMarkSect;
    CallableStatement addnew;

    public MarkQueries() {

        try {
            selectAll = connection.prepareCall("call get_all_mark()");
            insertInfo = connection.prepareCall("call insert_mark(?,?,?,?,?,?)");
            updtateInfo = connection.prepareCall("call update_mark(?,?,?,?,?,?)");
            deleteInfo = connection.prepareCall("call delete_mark(?)");
            getMarkById = connection.prepareCall("call get_mark_by_id(?)");
            getMarkSect = connection.prepareCall("call get_mark_by_sec(?,?)");
            addnew = connection.prepareCall("call add_new_mark(?,?)");
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public List<Mark> getMark() {
        try (ResultSet resultSet = selectAll.executeQuery()) {
            List<Mark> results = new ArrayList<>();

            while (resultSet.next()) {
                results.add(new Mark(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7),
                        resultSet.getInt(8)));
            }
            return results;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<Mark> getMarkByID(String studenttId) {

        try {
            getMarkById.setString(1, studenttId);
            ResultSet resultSet = getMarkById.executeQuery();
            List<Mark> results = new ArrayList<>();

            while (resultSet.next()) {
                results.add(new Mark(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7),
                        resultSet.getInt(8)));
            }
            return results;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public int addMark(String studID, String subjectCode, String test, String mid, String finalExam) {
        int total = Integer.valueOf(finalExam) + Integer.valueOf(test) + Integer.valueOf(mid);
        try {
            insertInfo.setString(1, studID);
            insertInfo.setString(2, subjectCode);
            insertInfo.setInt(3, Integer.valueOf(test));
            insertInfo.setInt(4, Integer.valueOf(mid));
            insertInfo.setInt(5, Integer.valueOf(finalExam));
            insertInfo.setInt(6, total);

            return insertInfo.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    public int updateMark(String studID, String subjectCode, String test, String mid, String finalExam) {
        try {
            updtateInfo.setString(1, studID);
            updtateInfo.setString(2, subjectCode);
            updtateInfo.setInt(3, Integer.valueOf(test));
            updtateInfo.setInt(4, Integer.valueOf(mid));
            updtateInfo.setInt(5, Integer.valueOf(finalExam));
            updtateInfo.setInt(6, Integer.valueOf(finalExam) + Integer.valueOf(test) + Integer.valueOf(mid));

            return updtateInfo.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    public int deleteMark(String studID) {
        try {
            deleteInfo.setString(1, studID);

            return deleteInfo.executeUpdate();
        } catch (SQLException e) {

            e.printStackTrace();
            return 0;
        }
    }

    public ObservableList<Mark> getMarkBySubject(String sectionCode, String subjectCode) {
        try {
            getMarkSect.setString(1, sectionCode);
            getMarkSect.setString(2, subjectCode);
            ResultSet resultSet = getMarkSect.executeQuery();
            ObservableList<Mark> results = FXCollections.observableArrayList();

            while (resultSet.next()) {
                results.add(new Mark(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7),
                        resultSet.getInt(8)));
            }
            return results;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public int addNewMark(String selectedStudent, String text) {

        try {
            addnew.setString(1, selectedStudent);
            addnew.setString(2, text);
            return addnew.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    public int isThereStudent(String student, String subCode) {
        try (ResultSet resultSet = selectAll.executeQuery()) {
            int returned = 0;
            while (resultSet.next()) {
                if (resultSet.getString(3).equals(student) && resultSet.getString(4).equals(subCode)) {
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

}
