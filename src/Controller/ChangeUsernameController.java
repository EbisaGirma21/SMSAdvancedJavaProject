package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import BaseClass.Admin;
import BaseClass.Student;
import Model.AdminQueries;
import Model.StudentQueries;
import Model.TeacherQueries;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

public class ChangeUsernameController implements Initializable {

    @FXML
    private TextField newUsernameTextField;

    @FXML
    private TextField oldUsernameTextField;

    @FXML
    private JFXButton submitBtn;

    String role;
    String userId;
    AdminQueries adminQueries = new AdminQueries();
    TeacherQueries teacherQueries = new TeacherQueries();
    StudentQueries studentQueries = new StudentQueries();

    AdminProfileController adminProfileController = new AdminProfileController();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        submitBtn.setOnAction(e -> {

            if (getRole().equals("admin") && (!oldUsernameTextField.getText().equals("")
                    && !newUsernameTextField.getText().equals(""))) {
                int confirm = adminQueries.changeUsername(getUserId(), oldUsernameTextField.getText(),
                        newUsernameTextField.getText());

                if (confirm == 1) {

                    Admin.setCurrentUser(newUsernameTextField.getText());
                    Stage stage = (Stage) oldUsernameTextField.getScene().getWindow();
                    stage.close();
                } else {
                    oldUsernameTextField.setStyle("-fx-border-color:RED");
                    newUsernameTextField.setStyle("-fx-border-color:RED");
                }
            } else if (getRole().equals("student") && (!oldUsernameTextField.getText().equals("")
                    && !newUsernameTextField.getText().equals(""))) {
                int confirm = studentQueries.changeUsername(getUserId(), oldUsernameTextField.getText(),
                        newUsernameTextField.getText());
                System.out.println(getUserId() + oldUsernameTextField.getText() +
                        newUsernameTextField.getText());
                if (confirm == 1) {
                    Student.setCurrentUser(newUsernameTextField.getText());
                    Stage stage = (Stage) oldUsernameTextField.getScene().getWindow();
                    stage.close();
                } else {
                    oldUsernameTextField.setStyle("-fx-border-color:RED");
                    newUsernameTextField.setStyle("-fx-border-color:RED");
                }
            } else if (getRole().equals("teacher") && (!oldUsernameTextField.getText().equals("")
                    && !newUsernameTextField.getText().equals(""))) {
                int confirm = teacherQueries.changeUsername(getUserId(), oldUsernameTextField.getText(),
                        newUsernameTextField.getText());
                System.out.println(getUserId() + oldUsernameTextField.getText() +
                        newUsernameTextField.getText());
                if (confirm == 1) {
                    Student.setCurrentUser(newUsernameTextField.getText());
                    Stage stage = (Stage) oldUsernameTextField.getScene().getWindow();
                    stage.close();
                } else {
                    oldUsernameTextField.setStyle("-fx-border-color:RED");
                    newUsernameTextField.setStyle("-fx-border-color:RED");
                }
            }

        });

    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
