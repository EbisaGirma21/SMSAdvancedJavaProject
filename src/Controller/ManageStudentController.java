package Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import javafx.beans.value.ChangeListener;
import BaseClass.Student;
import Model.MarkQueries;
import Model.SectionQueries;
import Model.StudentQueries;
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
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManageStudentController implements Initializable {

    @FXML
    private JFXButton addStudentBtn;

    @FXML
    private JFXButton deleteStudentBtn;

    @FXML
    private JFXButton editStudentBtn;

    @FXML
    private JFXRadioButton femaleRadioBtn;

    @FXML
    private TableColumn<Student, String> firstNameColumn;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TableColumn<Student, String> genderColumn;

    @FXML
    private TableColumn<Student, String> lastNameColumn;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private JFXRadioButton maleRadioBtn;

    @FXML
    private TableColumn<Student, String> middleNameColumn;

    @FXML
    private TextField middleNameTextField;

    @FXML
    private TableColumn<Student, String> yearColumn;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableColumn<Student, String> sectionColumn;

    @FXML
    private TextField sectionIdTextField;

    @FXML
    private Label selectedRowLabel;

    @FXML
    private JFXButton submitBtn;

    @FXML
    private TableColumn<Student, String> studentIdColumn;
    @FXML
    private JFXButton backBtn;

    @FXML
    private TextField studentIdTextField;

    @FXML
    private TableView<Student> studentManagmentTable;

    @FXML
    void back(ActionEvent event) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getClassLoader().getResource("View/AdminProfile.fxml"));
            Stage stage = (Stage) backBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    SectionQueries sectionQueries = new SectionQueries();
    ToggleGroup group = new ToggleGroup();

    final ObservableList<Student> data = FXCollections.observableArrayList();
    private StudentQueries studentQueries = new StudentQueries();
    MarkQueries markQueries = new MarkQueries();

    String deletedRow, studentId, firstName, middleName, lastName, section;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        maleRadioBtn.setToggleGroup(group);
        femaleRadioBtn.setToggleGroup(group);
        submitBtn.setVisible(false);

        data.setAll(studentQueries.getStudent());

        studentManagmentTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {

            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                if (newValue != null) {
                    selectedRowLabel.setText((newValue.getUserId() + "    " + newValue.getFirstName() + "    "
                            + newValue.getMiddleName() + "     "
                            + "   " + newValue.getLastName()));
                    deletedRow = newValue.getUserId();
                    studentId = newValue.getUserId();
                    firstName = newValue.getFirstName();
                    middleName = newValue.getMiddleName();
                    lastName = newValue.getLastName();
                    section = newValue.getSectionId();

                } else {
                    selectedRowLabel.setText("No rows has been selected");
                }

            }

        });
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("userId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        middleNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("middleName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("gender"));
        sectionColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("sectionId"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("year"));
        studentManagmentTable.setItems(data);

        addStudentBtn.setOnAction(e -> {
            int confirm = sectionQueries.isThere(sectionIdTextField.getText());

            //
            if (firstNameTextField.getText() == "" || middleNameTextField.getText() == ""
                    || lastNameTextField.getText() == "" || studentIdTextField.getText() == ""
                    || sectionIdTextField.getText() == "" || group.getSelectedToggle() == null)

            {
                Alert alert = new Alert(AlertType.WARNING, "Field is Empty");
                alert.show();
            } else {
                if (confirm == 1) {
                    int response = studentQueries.addStudent(
                            studentIdTextField.getText(),
                            firstNameTextField.getText(),
                            middleNameTextField.getText(),
                            lastNameTextField.getText(),
                            ((Labeled) group.getSelectedToggle()).getText(),
                            sectionIdTextField.getText(),
                            LocalDate.now().toString(),
                            firstNameTextField.getText(),
                            lastNameTextField.getText());

                    if (response == 1) {
                        data.setAll(studentQueries.getStudent());
                    }
                    studentIdTextField.clear();
                    firstNameTextField.clear();
                    middleNameTextField.clear();
                    lastNameTextField.clear();
                    sectionIdTextField.clear();

                } else {
                    sectionIdTextField.setStyle("-fx-border-color:RED");
                }

            }
        });

        editStudentBtn.setOnAction(e -> {
            submitBtn.setVisible(true);
            studentIdTextField.setText(studentId);
            firstNameTextField.setText(firstName);
            middleNameTextField.setText(middleName);
            lastNameTextField.setText(lastName);
            sectionIdTextField.setText(section);
        });
        submitBtn.setOnAction(e -> {
            //
            if (firstNameTextField.getText() == "" || middleNameTextField.getText() == ""
                    || lastNameTextField.getText() == "" || studentIdTextField.getText() == ""
                    || sectionIdTextField.getText() == "")

            {
                Alert alert = new Alert(AlertType.WARNING, "Field is Empty");
                alert.show();
            } else {

                int response = studentQueries.updateStudent(
                        studentIdTextField.getText(),
                        firstNameTextField.getText(),
                        middleNameTextField.getText(),
                        lastNameTextField.getText(),
                        sectionIdTextField.getText());

                if (response == 1) {
                    data.setAll(studentQueries.getStudent());
                }
                studentIdTextField.clear();
                firstNameTextField.clear();
                middleNameTextField.clear();
                lastNameTextField.clear();
                sectionIdTextField.clear();
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
            markQueries.deleteMark(deletedRow);
            int response = studentQueries.deleteStudent(deletedRow);

            if (response == 1) {
                data.setAll(studentQueries.getStudent());
                window.close();
            }
        });
        btnNo.setOnAction(e -> {
            window.close();
        });
        Scene scene = new Scene(vBox, 350, 100);
        window.setTitle("Delete Information");
        window.setScene(scene);

        deleteStudentBtn.setOnAction(e -> {
            if (!selectedRowLabel.getText().equals("No rows has been selected")) {
                window.show();
            }
        });

        FilteredList<Student> filteredStudent = new FilteredList<>(data, b -> true);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredStudent.setPredicate(Student -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Student.getFirstName().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Student.getLastName().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Student.getMiddleName().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Student.getSectionId().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Student.getUserId().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Student.getGender().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else
                    return false;
            });
        });
        SortedList<Student> sortedStudent = new SortedList<>(filteredStudent);
        sortedStudent.comparatorProperty().bind(studentManagmentTable.comparatorProperty());
        studentManagmentTable.setItems(sortedStudent);

    }

}
