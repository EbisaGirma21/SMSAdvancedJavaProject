package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import BaseClass.Subject;
import BaseClass.Teacher;

import Model.SubjectQueries;
import Model.TeacherQueries;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AssigningTeacherController implements Initializable {

    @FXML
    private TextField searchTextField;
    @FXML
    private JFXButton backBtn;

    @FXML
    private TextField sectionIdTextField;

    @FXML
    private TableView<Subject> sectionTable;

    @FXML
    private TableColumn<Subject, String> subjectCodeColumn;

    @FXML
    private TableColumn<Subject, String> subjectNameColumn;

    @FXML
    private TableColumn<Subject, String> teacherIdColumn;

    @FXML
    private ListView<Teacher> unassignedTeachersList;

    @FXML
    void touchHandler(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            subjectQueries.updateSubjects(selectedCode, selectedSection, selectedTeacher);
            sectionData.setAll(subjectQueries.getSubjects());
        }
    }

    String selected;
    String selectedTeacher;
    String selectedCode;
    String selectedSection;
    final ObservableList<Subject> sectionData = FXCollections.observableArrayList();

    final ObservableList<Teacher> teacherData = FXCollections.observableArrayList();

    SubjectQueries subjectQueries = new SubjectQueries();

    TeacherQueries teacherQueries = new TeacherQueries();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        searchTextField.setVisible(false);
        backBtn.setOnAction(e -> {
            try {
                AnchorPane root = FXMLLoader.load(getClass().getClassLoader().getResource("View/AdminProfile.fxml"));
                Stage stage = (Stage) backBtn.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });

        sectionTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Subject>() {

            @Override
            public void changed(ObservableValue<? extends Subject> observable, Subject oldValue, Subject newValue) {
                if (newValue != null) {
                    selected = newValue.getSubjectName();
                    selectedCode = newValue.getSubjectCode();
                    selectedSection = newValue.getSectionCode();
                    searchTextField.setText(selected);
                }
            }

        });

        unassignedTeachersList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Teacher>() {

            @Override
            public void changed(ObservableValue<? extends Teacher> observable, Teacher oldValue, Teacher newValue) {
                if (newValue != null) {
                    selectedTeacher = newValue.getUserId();
                }
            }

        });

        sectionData.setAll(subjectQueries.getSubjects());
        teacherData.setAll(teacherQueries.getTeacher());
        unassignedTeachersList.setItems(teacherData);

        subjectCodeColumn.setCellValueFactory(new PropertyValueFactory<Subject, String>("subjectCode"));
        subjectNameColumn.setCellValueFactory(new PropertyValueFactory<Subject, String>("subjectName"));
        teacherIdColumn.setCellValueFactory(new PropertyValueFactory<Subject, String>("teacherId"));
        sectionTable.setItems(sectionData);

        FilteredList<Subject> filteredSection = new FilteredList<>(sectionData, b -> true);
        sectionIdTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredSection.setPredicate(Subject -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Subject.getSectionCode().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else
                    return false;
            });
        });
        SortedList<Subject> sortedSection = new SortedList<>(filteredSection);
        sortedSection.comparatorProperty().bind(sectionTable.comparatorProperty());
        sectionTable.setItems(sortedSection);

        FilteredList<Teacher> filteredTeacher = new FilteredList<>(teacherData, b -> true);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredTeacher.setPredicate(Teacher -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Teacher.getSubject().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else
                    return false;
            });
        });
        unassignedTeachersList.setItems(filteredTeacher);
    }

}
