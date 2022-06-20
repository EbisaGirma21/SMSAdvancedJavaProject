package Model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BaseClass.Section;

public class SectionQueries {
    DatabaseAccess database = new DatabaseAccess();
    Connection connection = database.connection;
    CallableStatement selectAll;
    CallableStatement insertInfo;
    CallableStatement updtateInfo;
    CallableStatement deleteInfo;

    public SectionQueries() {
        try {
            selectAll = connection.prepareCall("call get_all_section()");
            insertInfo = connection.prepareCall("call insert_section(?,?,?,?)");
            updtateInfo = connection.prepareCall("call update_section(?,?,?,?)");
            deleteInfo = connection.prepareCall("call delete_section(?)");
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public List<Section> getSection() {
        try (ResultSet resultSet = selectAll.executeQuery()) {
            List<Section> results = new ArrayList<>();

            while (resultSet.next()) {
                results.add(new Section(
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

    public int addSection(String sectionId, String teacherId, String numberOfStudent, String blockNummber) {
        try {
            insertInfo.setString(1, sectionId);
            insertInfo.setString(2, teacherId);
            insertInfo.setString(3, numberOfStudent);
            insertInfo.setString(4, blockNummber);
            return insertInfo.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    public int updateSection(String sectionId, String teacherId, String numberOfStudent, String blockNummber) {
        try {
            updtateInfo.setString(1, sectionId);
            updtateInfo.setString(2, teacherId);
            updtateInfo.setString(3, numberOfStudent);
            updtateInfo.setString(4, blockNummber);
            return updtateInfo.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    public int deleteSection(String SectID) {
        try {
            deleteInfo.setString(1, SectID);

            return deleteInfo.executeUpdate();
        } catch (SQLException e) {

            e.printStackTrace();
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

    public int isThereTeacher(String teach) {
        try (ResultSet resultSet = selectAll.executeQuery()) {
            int returned = 0;
            while (resultSet.next()) {
                if (resultSet.getString(2).equals(teach)) {
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
