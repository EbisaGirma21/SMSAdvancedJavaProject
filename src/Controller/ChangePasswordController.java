package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Model.AdminQueries;
import Model.StudentQueries;
import Model.TeacherQueries;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ChangePasswordController implements Initializable {
    @FXML
    private PasswordField confirmPasswordFiled;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private JFXButton submitBtn;

    String role;
    String userId;
    StudentQueries studentQueries = new StudentQueries();
    AdminQueries adminQueries = new AdminQueries();
    TeacherQueries teacherQueries = new TeacherQueries();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        submitBtn.setOnAction(e -> {

            if (getRole().equals("admin") && !oldPasswordField.getText().equals("")
                    && !newPasswordField.getText().equals(oldPasswordField.getText())) {
                int confirm = adminQueries.ChangePassword(getUserId(), oldPasswordField.getText(),
                        newPasswordField.getText());
                System.out.println(getUserId() + oldPasswordField.getText() +
                        newPasswordField.getText());

                if (confirm == 1) {

                    Stage stage = (Stage) oldPasswordField.getScene().getWindow();
                    stage.close();
                } else {
                    oldPasswordField.setStyle("-fx-border-color:RED");
                    newPasswordField.setStyle("-fx-border-color:RED");
                    confirmPasswordFiled.setStyle("-fx-border-color:RED");
                }
            } else if (getRole().equals("student") && !oldPasswordField.getText().equals("")
                    && !newPasswordField.getText().equals(oldPasswordField.getText())) {
                int confirm = studentQueries.ChangePassword(getUserId(), oldPasswordField.getText(),
                        newPasswordField.getText());
                System.out.println(getUserId() + oldPasswordField.getText() +
                        newPasswordField.getText());
                if (confirm == 1) {
                    Stage stage = (Stage) oldPasswordField.getScene().getWindow();
                    stage.close();
                } else {
                    oldPasswordField.setStyle("-fx-border-color:RED");
                    newPasswordField.setStyle("-fx-border-color:RED");
                    confirmPasswordFiled.setStyle("-fx-border-color:RED");
                }
            } else if (getRole().equals("teacher") && !oldPasswordField.getText().equals("")
                    && !newPasswordField.getText().equals(oldPasswordField.getText())) {
                int confirm = teacherQueries.ChangePassword(getUserId(), oldPasswordField.getText(),
                        newPasswordField.getText());
                System.out.println(getUserId() + oldPasswordField.getText() +
                        newPasswordField.getText());
                if (confirm == 1) {
                    Stage stage = (Stage) oldPasswordField.getScene().getWindow();
                    stage.close();
                } else {
                    oldPasswordField.setStyle("-fx-border-color:RED");
                    newPasswordField.setStyle("-fx-border-color:RED");
                    confirmPasswordFiled.setStyle("-fx-border-color:RED");
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
