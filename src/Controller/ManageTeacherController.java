package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import BaseClass.Teacher;
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

public class ManageTeacherController implements Initializable {

    @FXML
    private JFXButton addTeacherBtn;

    @FXML
    private JFXButton deleteTeacherBtn;

    @FXML
    private JFXButton editTeacherBtn;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXRadioButton femaleRadioBtn;

    @FXML
    private TableColumn<Teacher, String> firstNameColumn;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TableColumn<Teacher, String> genderColumn;

    @FXML
    private TableColumn<Teacher, String> lastNameColumn;

    @FXML
    private JFXButton submitBtn;

    @FXML
    private TextField subjectTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private JFXRadioButton maleRadioBtn;

    @FXML
    private TableColumn<Teacher, String> middleNameColumn;

    @FXML
    private TextField middleNameTextField;

    @FXML
    private TextField searchTextField;

    @FXML
    private Label selectedRowLabel;

    @FXML
    private TableColumn<Teacher, String> teacherIdColumn;

    @FXML
    private TextField teacherIdTextField;

    @FXML
    private TableView<Teacher> teacherManagementTable;

    @FXML
    private TableColumn<Teacher, String> majorSubjectColumn;

    @FXML
    private TextField usernameTextField;

    ToggleGroup group = new ToggleGroup();

    final ObservableList<Teacher> data = FXCollections.observableArrayList();
    private TeacherQueries teacherQueries = new TeacherQueries();

    String deletedRow, teacherId, firstName, middleName, lastName, subject;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        submitBtn.setVisible(false);
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
        maleRadioBtn.setToggleGroup(group);
        femaleRadioBtn.setToggleGroup(group);

        data.setAll(teacherQueries.getTeacher());

        teacherManagementTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Teacher>() {

            @Override
            public void changed(ObservableValue<? extends Teacher> observable, Teacher oldValue, Teacher newValue) {
                if (newValue != null) {
                    selectedRowLabel.setText((newValue.getUserId() + "    " + newValue.getFirstName() + "    "
                            + newValue.getMiddleName() + "     "
                            + "   " + newValue.getLastName()));
                    deletedRow = newValue.getUserId();
                    teacherId = newValue.getUserId();
                    firstName = newValue.getFirstName();
                    middleName = newValue.getMiddleName();
                    lastName = newValue.getLastName();
                    subject = newValue.getSubject();

                } else {
                    selectedRowLabel.setText("No rows has been selected");
                }

            }

        });
        teacherIdColumn.setCellValueFactory(new PropertyValueFactory<Teacher, String>("userId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Teacher, String>("firstName"));
        middleNameColumn.setCellValueFactory(new PropertyValueFactory<Teacher, String>("middleName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Teacher, String>("lastName"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<Teacher, String>("gender"));
        majorSubjectColumn.setCellValueFactory(new PropertyValueFactory<Teacher, String>("subject"));

        teacherManagementTable.setItems(data);

        addTeacherBtn.setOnAction(e -> {
            //
            if (firstNameTextField.getText() == "" || middleNameTextField.getText() == ""
                    || lastNameTextField.getText() == "" || teacherIdTextField.getText() == ""
                    || subjectTextField.getText() == "" || group.getSelectedToggle() == null)

            {
                Alert alert = new Alert(AlertType.WARNING, "Field is Empty");
                alert.show();
            } else {

                int response = teacherQueries.addTeacher(
                        teacherIdTextField.getText(),
                        firstNameTextField.getText(),
                        middleNameTextField.getText(),
                        lastNameTextField.getText(),
                        ((Labeled) group.getSelectedToggle()).getText(),
                        subjectTextField.getText(),
                        firstNameTextField.getText(),
                        lastNameTextField.getText());

                if (response == 1) {
                    data.setAll(teacherQueries.getTeacher());
                }
                teacherIdTextField.clear();
                firstNameTextField.clear();
                middleNameTextField.clear();
                lastNameTextField.clear();
                subjectTextField.clear();

            }
        });

        editTeacherBtn.setOnAction(e -> {
            submitBtn.setVisible(true);
            teacherIdTextField.setText(teacherId);
            firstNameTextField.setText(firstName);
            middleNameTextField.setText(middleName);
            lastNameTextField.setText(lastName);
            subjectTextField.setText(subject);
        });
        submitBtn.setOnAction(e -> {
            //
            if (firstNameTextField.getText() == "" || middleNameTextField.getText() == ""
                    || lastNameTextField.getText() == "" || teacherIdTextField.getText() == ""
                    || subjectTextField.getText() == "")

            {
                Alert alert = new Alert(AlertType.WARNING, "Field is Empty");
                alert.show();
            } else {

                int response = teacherQueries.updateTeacher(
                        teacherIdTextField.getText(),
                        firstNameTextField.getText(),
                        middleNameTextField.getText(),
                        lastNameTextField.getText(),
                        subjectTextField.getText());

                if (response == 1) {
                    data.setAll(teacherQueries.getTeacher());
                }
                teacherIdTextField.clear();
                firstNameTextField.clear();
                middleNameTextField.clear();
                lastNameTextField.clear();
                subjectTextField.clear();
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
            int response = teacherQueries.deleteTeacher(deletedRow);
            System.out.println(response);
            if (response == 1) {
                data.setAll(teacherQueries.getTeacher());
                window.close();
            }
        });
        btnNo.setOnAction(e -> {
            window.close();
        });
        Scene scene = new Scene(vBox, 350, 100);
        window.setTitle("Delete Information");
        window.setScene(scene);

        deleteTeacherBtn.setOnAction(e -> {
            if (!selectedRowLabel.getText().equals("No rows has been selected")) {
                window.show();
            }
        });

        FilteredList<Teacher> filteredTeacher = new FilteredList<>(data, b -> true);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredTeacher.setPredicate(Teacher -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Teacher.getFirstName().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Teacher.getLastName().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Teacher.getMiddleName().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Teacher.getUserId().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else
                    return false;
            });
        });
        SortedList<Teacher> sortedTeacher = new SortedList<>(filteredTeacher);
        sortedTeacher.comparatorProperty().bind(teacherManagementTable.comparatorProperty());
        teacherManagementTable.setItems(sortedTeacher);

    }

}
