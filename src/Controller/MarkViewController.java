package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import BaseClass.Mark;
import BaseClass.Student;
import Model.MarkQueries;
import Model.StudentQueries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MarkViewController implements Initializable {

    @FXML
    private TableColumn<Mark, Integer> finalColumn;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private TableColumn<Mark, Integer> midExamColumn;

    @FXML
    private Label middleNameLabel;

    @FXML
    private TextField searchTextField;

    @FXML
    private Label studentIdLabel;

    @FXML
    private Button backBtn;

    @FXML
    private TableView<Mark> studentMarkTable;

    @FXML
    private TableColumn<Mark, String> subjectCodeColumn;

    @FXML
    private TableColumn<Mark, Integer> testColumn;

    @FXML
    private TableColumn<Mark, Integer> totalColumn;

    @FXML
    void back(ActionEvent event) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getClassLoader().getResource("View/StudentProfile.fxml"));
            Stage stage = (Stage) backBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    private final ObservableList<Mark> data = FXCollections.observableArrayList();
    private ObservableList<Student> studentInfo = FXCollections.observableArrayList();
    MarkQueries markQueries = new MarkQueries();
    StudentQueries studentQueries = new StudentQueries();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        studentInfo.setAll(studentQueries.getStudentByEmail(Student.getCurrentUser()));
        firstNameLabel.setText(studentInfo.get(0).getFirstName());
        middleNameLabel.setText(studentInfo.get(0).getMiddleName());
        lastNameLabel.setText(studentInfo.get(0).getLastName());
        studentIdLabel.setText(studentInfo.get(0).getUserId());
        data.setAll(markQueries.getMarkByID(Student.getCurrentUserId()));

        subjectCodeColumn.setCellValueFactory(new PropertyValueFactory<Mark, String>("subjectId"));
        testColumn.setCellValueFactory(new PropertyValueFactory<Mark, Integer>("test"));
        midExamColumn.setCellValueFactory(new PropertyValueFactory<Mark, Integer>("midexam"));
        finalColumn.setCellValueFactory(new PropertyValueFactory<Mark, Integer>("finalexam"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<Mark, Integer>("total"));
        studentMarkTable.setItems(data);

        FilteredList<Mark> filteredMarks = new FilteredList<>(data, b -> true);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredMarks.setPredicate(Mark -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Mark.getSubjectId().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else
                    return false;
            });
        });
        SortedList<Mark> sortedMark = new SortedList<>(filteredMarks);
        sortedMark.comparatorProperty().bind(studentMarkTable.comparatorProperty());
        studentMarkTable.setItems(sortedMark);

    }

}
