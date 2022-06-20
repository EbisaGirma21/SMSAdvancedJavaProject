package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import BaseClass.Mark;
import BaseClass.Student;
import BaseClass.Subject;
import BaseClass.Teacher;
import Model.MarkQueries;
import Model.StudentQueries;
import Model.SubjectQueries;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StudentMarkController implements Initializable {

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private JFXButton submitBtn;

    @FXML
    private JFXButton editBtn;

    @FXML
    private TableColumn<Mark, Integer> finalExamColumn;

    @FXML
    private TextField finalTF;

    @FXML
    private TableView<Mark> markTable;

    @FXML
    private TableColumn<Mark, Integer> midExamColumn;

    @FXML
    private TextField midTF;

    @FXML
    private TextField searchTF;

    @FXML
    private Label selectedRowLabel;

    @FXML
    private JFXButton backBtn;

    @FXML
    private TextField sectionField;

    @FXML
    private TableColumn<Mark, String> studentIdColumn;

    @FXML
    private TextField studentIdTF;

    @FXML
    private TextField subjectCodeTF;

    @FXML
    private JFXListView<Student> studentLisView;

    @FXML
    private Label subjectCodeLabel;

    @FXML
    private ChoiceBox<Subject> sectionIdchoiceBox;

    @FXML
    private TableColumn<Mark, Integer> testColumn;

    @FXML
    private TextField testTF;

    @FXML
    private TableColumn<Mark, Integer> totalColumn;

    @FXML
    void touchHandler(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            int confirm = markQueries.isThereStudent(selectedStudent, subjectCode);
            if (confirm == 0) {
                markQueries.addNewMark(selectedStudent, subjectCodeLabel.getText());
                markTable.setItems(markQueries.getMarkBySubject(sectionId, subjectCode));
            }

        }
    }

    @FXML
    void back(ActionEvent event) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getClassLoader().getResource("View/TeacherProfile.fxml"));
            Stage stage = (Stage) backBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private final ObservableList<Mark> data = FXCollections.observableArrayList();
    private final ObservableList<Student> studentData = FXCollections.observableArrayList();
    private final ObservableList<Subject> subjectId = FXCollections.observableArrayList();
    private final ObservableList<Subject> teacherData = FXCollections.observableArrayList();

    SubjectQueries subjectQueries = new SubjectQueries();
    StudentQueries studentQueries = new StudentQueries();
    MarkQueries markQueries = new MarkQueries();

    String deletedRow, studentId, finalExam, middleExam, test, subjectCode, sectionId;
    String selectedStudent;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        sectionField.setVisible(false);
        submitBtn.setVisible(false);
        subjectId.setAll(subjectQueries.getSubjects());

        teacherData.setAll(subjectQueries.getSubjectsByTeacherId(Teacher.getCurrentUserId()));
        studentData.setAll(studentQueries.getStudent());

        markTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Mark>() {

            @Override
            public void changed(ObservableValue<? extends Mark> observable, Mark aroldValueg1, Mark newValue) {
                submitBtn.setVisible(false);
                if (newValue != null) {
                    selectedRowLabel.setText((newValue.getStudentId()));
                    deletedRow = newValue.getStudentId();
                    studentId = newValue.getStudentId();
                    finalExam = String.valueOf(newValue.getFinalexam());
                    middleExam = String.valueOf(newValue.getMidexam());
                    test = String.valueOf(newValue.getTest());

                } else {
                    selectedRowLabel.setText("No Row Selected By Default");
                }

            }

        });

        studentLisView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {

            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                if (newValue != null) {
                    selectedStudent = newValue.getUserId();
                }
            }

        });

        sectionIdchoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            sectionField.setText(newValue.getSectionCode());
            subjectCodeLabel.setText(newValue.getSubjectCode());
            subjectCode = newValue.getSubjectCode();
            sectionId = newValue.getSectionCode();
            markTable.setItems(markQueries.getMarkBySubject(newValue.getSectionCode(), newValue.getSubjectCode()));
        });

        sectionIdchoiceBox.setItems(teacherData);

        studentIdColumn.setCellValueFactory(new PropertyValueFactory<Mark, String>("studentId"));
        testColumn.setCellValueFactory(new PropertyValueFactory<Mark, Integer>("test"));
        midExamColumn.setCellValueFactory(new PropertyValueFactory<Mark, Integer>("midexam"));
        finalExamColumn.setCellValueFactory(new PropertyValueFactory<Mark, Integer>("finalexam"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<Mark, Integer>("total"));
        markTable.setItems(data);

        addBtn.setOnAction(e -> {
            int confirm = markQueries.isThereStudent(selectedStudent, subjectCode);

            if (studentIdTF.getText() == "" || testTF.getText() == "" || Integer.valueOf(testTF.getText()) > 20
                    || finalTF.getText() == "" || Integer.valueOf(finalTF.getText()) > 50 || midTF.getText() == ""
                    || Integer.valueOf(midTF.getText()) > 30)

            {
                Alert alert = new Alert(AlertType.WARNING, "Field is Incorrect");
                alert.show();
            } else {
                if (confirm == 0) {
                    int response = markQueries.addMark(
                            studentIdTF.getText(),
                            subjectCodeTF.getText(),
                            testTF.getText(),
                            midTF.getText(),
                            finalTF.getText());

                    if (response == 1) {
                        data.setAll(markQueries.getMark());
                    }
                    studentIdTF.clear();
                    subjectCodeTF.clear();
                    testTF.clear();
                    midTF.clear();
                    finalTF.clear();
                } else {
                    studentIdTF.setStyle("-fx-border-color:RED");
                }
            }
        });

        editBtn.setOnAction(e -> {
            submitBtn.setVisible(true);
            studentIdTF.setText(studentId);
            subjectCodeTF.setText(subjectCode);
            finalTF.setText(finalExam);
            midTF.setText(middleExam);
            testTF.setText(test);
        });
        submitBtn.setOnAction(e -> {
            //
            if (studentIdTF.getText() == "" || testTF.getText() == "" || Integer.valueOf(testTF.getText()) > 20
                    || finalTF.getText() == "" || Integer.valueOf(finalTF.getText()) > 50 || midTF.getText() == ""
                    || Integer.valueOf(midTF.getText()) > 30)

            {
                Alert alert = new Alert(AlertType.WARNING, "Field is Empty");
                alert.show();
            } else {

                int response = markQueries.updateMark(
                        studentIdTF.getText(),
                        subjectCode,
                        testTF.getText(),
                        midTF.getText(),
                        finalTF.getText());
                if (response == 1) {
                    markTable.setItems(markQueries.getMarkBySubject(sectionId, subjectCode));
                }
                studentIdTF.clear();
                subjectCodeTF.clear();
                testTF.clear();
                midTF.clear();
                finalTF.clear();
                submitBtn.setVisible(false);
            }
        });

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Label confirm = new Label("Are you sure you want to delete these student infromation!");
        JFXButton btnYes = new JFXButton("YES");
        JFXButton btnNo = new JFXButton("NO");

        HBox hBox = new HBox();
        hBox.getChildren().addAll(btnYes, btnNo);
        hBox.setSpacing(55);
        HBox.setMargin(btnYes, new Insets(0, 0, 0, 100));

        VBox vBox = new VBox();
        vBox.getChildren().addAll(confirm, hBox);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));

        btnYes.setOnAction(e -> {
            int response = markQueries.deleteMark(deletedRow);
            System.out.println(response);
            if (response == 1) {
                markTable.setItems(markQueries.getMarkBySubject(sectionId, subjectCode));
                window.close();
            }
        });
        btnNo.setOnAction(e -> {
            window.close();
        });
        Scene scene = new Scene(vBox, 350, 100);
        window.setTitle("Delete Information");
        window.setScene(scene);

        deleteBtn.setOnAction(e -> {
            if (!selectedRowLabel.getText().equals("No rows has been selected")) {
                window.show();
            }

        });

        FilteredList<Mark> filteredMarks = new FilteredList<>(data, b -> true);
        subjectCodeLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredMarks.setPredicate(Mark -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Mark.getSubjectId().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Mark.getFirstName().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else
                    return false;
            });
        });
        SortedList<Mark> sortedMark = new SortedList<>(filteredMarks);
        sortedMark.comparatorProperty().bind(markTable.comparatorProperty());
        markTable.setItems(sortedMark);

        FilteredList<Student> filteredStudentBysection = new FilteredList<>(studentData, b -> true);
        sectionField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredStudentBysection.setPredicate(Student -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Student.getSectionId().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else
                    return false;
            });
        });
        studentLisView.setItems(filteredStudentBysection);

    }

}
