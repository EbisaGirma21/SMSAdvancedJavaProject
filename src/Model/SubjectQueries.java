package Model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BaseClass.Subject;

public class SubjectQueries {
    DatabaseAccess database = new DatabaseAccess();
    Connection connection = database.connection;
    CallableStatement selectAll;
    CallableStatement insertInfo;
    CallableStatement updateInfo;
    CallableStatement updateSubInfo;
    CallableStatement deleteInfo;
    CallableStatement selectByTeacherId;

    public SubjectQueries() {
        try {
            selectAll = connection.prepareCall("call get_all_subject()");
            insertInfo = connection.prepareCall("call insert_subject(?,?,?,?)");
            updateInfo = connection.prepareCall("call update_subject(?,?,?)");
            updateSubInfo = connection.prepareCall("call update_subject_info(?,?,?,?)");
            deleteInfo = connection.prepareCall("call delete_subject(?)");
            selectByTeacherId = connection.prepareCall("call get_subject_by_teacher_Id(?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Subject> getSubjects() {
        try (ResultSet resultSet = selectAll.executeQuery()) {
            List<Subject> results = new ArrayList<>();

            while (resultSet.next()) {
                results.add(new Subject(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)));
            }
            return results;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public int addSubjects(String subjectCode, String subjectName, String teacherId, String sectionId) {
        try {
            insertInfo.setString(1, subjectCode);
            insertInfo.setString(2, subjectName);
            insertInfo.setString(3, teacherId);
            insertInfo.setString(4, sectionId);
            return insertInfo.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    public int updateInfo(String subjectCode, String subjectName, String teacherId, String sectionId) {
        try {
            updateSubInfo.setString(1, subjectCode);
            updateSubInfo.setString(2, subjectName);
            updateSubInfo.setString(3, teacherId);
            updateSubInfo.setString(4, sectionId);
            return updateSubInfo.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    public int deleteSubjects(String subCode) {
        try {
            deleteInfo.setString(1, subCode);

            return deleteInfo.executeUpdate();
        } catch (SQLException e) {

            e.printStackTrace();
            return 0;
        }

    }

    public int updateSubjects(String subCode, String selectedSection, String teachId) {
        try {
            updateInfo.setString(1, subCode);
            updateInfo.setString(2, selectedSection);
            updateInfo.setString(3, teachId);

            return updateInfo.executeUpdate();
        } catch (SQLException e) {

            e.printStackTrace();
            return 0;
        }

    }

    public List<Subject> getSubjectsByTeacherId(String currentUserId) {
        try {
            selectByTeacherId.setString(1, currentUserId);
            ResultSet resultSet = selectByTeacherId.executeQuery();
            List<Subject> results = new ArrayList<>();

            while (resultSet.next()) {
                results.add(new Subject(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)));
            }
            return results;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
