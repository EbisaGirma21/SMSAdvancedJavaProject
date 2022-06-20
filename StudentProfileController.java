package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import BaseClass.Student;
import Model.StudentQueries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StudentProfileController implements Initializable {

    @FXML
    private Label Gender;

    @FXML
    private Label idLabel;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private JFXButton logoutBtn;

    @FXML
    private Label middleNameLabel;

    @FXML
    private JFXButton profileBtn;

    @FXML
    private JFXButton changePasswordBtn;

    @FXML
    private JFXButton changeUserNameBtn;

    @FXML
    private JFXButton studentInfoBtn;

    @FXML
    AnchorPane mainancher;

    @FXML
    private Label userNameLabel;
    StudentQueries studentQueries = new StudentQueries();
    private ObservableList<Student> studentInfo = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        studentInfo.setAll(studentQueries.getStudentByEmail(Student.getCurrentUser()));
        firstNameLabel.setText(studentInfo.get(0).getFirstName());
        middleNameLabel.setText(studentInfo.get(0).getMiddleName());
        lastNameLabel.setText(studentInfo.get(0).getLastName());
        idLabel.setText(studentInfo.get(0).getUserId());
        Gender.setText(studentInfo.get(0).getGender());
        userNameLabel.setText(studentInfo.get(0).getUsername());

        logoutBtn.setOnAction(e -> {
            AnchorPane root;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/Login.fxml"));
                Stage stage = (Stage) logoutBtn.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e1) {

                e1.printStackTrace();
            }

        });
        changeUserNameBtn.setOnAction(e -> {
            String role = "student";
            String userId = idLabel.getText();
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getClassLoader().getResource("View/ChangeUsername.fxml"));
                AnchorPane root = (AnchorPane) loader.load();
                ChangeUsernameController changeUsername = loader.getController();
                changeUsername.setRole(role);
                changeUsername.setUserId(userId);
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.show();
            } catch (IOException e1) {

                e1.printStackTrace();
            }
        });
        changePasswordBtn.setOnAction(e -> {
            String role = "student";
            String userId = idLabel.getText();
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getClassLoader().getResource("View/ChangePassword.fxml"));
                AnchorPane root = (AnchorPane) loader.load();
                ChangePasswordController changePassword = loader.getController();
                changePassword.setRole(role);
                changePassword.setUserId(userId);
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.show();
            } catch (IOException e1) {

                e1.printStackTrace();
            }
        });
        studentInfoBtn.setOnAction(e -> {
            Student.setCurrentUserId(studentInfo.get(0).getUserId());
            AnchorPane root;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/MarkView.fxml"));
                Stage stage = (Stage) studentInfoBtn.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e1) {

                e1.printStackTrace();
            }
        });
    }
}
