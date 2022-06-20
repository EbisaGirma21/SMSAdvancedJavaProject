package Controller;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import BaseClass.Admin;
import BaseClass.Student;
import BaseClass.Teacher;
import Model.AdminQueries;
import Model.StudentQueries;
import Model.TeacherQueries;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private ChoiceBox<String> accountTypeCB;

    @FXML
    private AnchorPane anchor;

    @FXML
    private ImageView imageViewer;

    @FXML
    private TextField passwordField;

    @FXML
    private Label incorrectLabel;

    @FXML
    private JFXButton loginButton;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private JFXCheckBox showPasswordCheckBox;

    @FXML
    private TextField userNameTextFeild;

    private AdminQueries adminQueries = new AdminQueries();
    private StudentQueries studentQueries = new StudentQueries();
    private TeacherQueries teacherQueries = new TeacherQueries();

    int confirm;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        passwordField.setVisible(false);

        String[] account = {
                "Admin Portal", "Teacher Portal", "Student Portal"
        };
        accountTypeCB.getItems().addAll(account);
        accountTypeCB.setValue(account[0]);

        loginButton.setOnAction(e -> {

            if (accountTypeCB.getValue() == "Admin Portal") {
                Admin.setCurrentUser(userNameTextFeild.getText());
                confirm = adminQueries.authenticate(userNameTextFeild.getText(), passwordTextField.getText(),
                        passwordField.getText());
                if (confirm == 1) {
                    try {
                        AnchorPane root = FXMLLoader
                                .load(getClass().getClassLoader().getResource("View/AdminProfile.fxml"));
                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    userNameTextFeild.setStyle("-fx-border-color:RED");
                    passwordTextField.setStyle("-fx-border-color:RED");
                    passwordField.setStyle("-fx-border-color:RED");

                }

            } else if (accountTypeCB.getValue() == "Student Portal") {
                confirm = studentQueries.authenticate(userNameTextFeild.getText(), passwordTextField.getText(),
                        passwordField.getText());
                if (confirm == 1) {
                    Student.setCurrentUser(userNameTextFeild.getText());
                    try {
                        AnchorPane root = FXMLLoader
                                .load(getClass().getClassLoader().getResource("View/StudentProfile.fxml"));
                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    userNameTextFeild.setStyle("-fx-border-color:RED");
                    passwordTextField.setStyle("-fx-border-color:RED");
                    passwordField.setStyle("-fx-border-color:RED");

                }

            } else if (accountTypeCB.getValue() == "Teacher Portal") {
                Teacher.setCurrentUser(userNameTextFeild.getText());
                confirm = teacherQueries.authenticate(userNameTextFeild.getText(), passwordTextField.getText(),
                        passwordField.getText());
                if (confirm == 1) {
                    try {
                        AnchorPane root = FXMLLoader
                                .load(getClass().getClassLoader().getResource("View/TeacherProfile.fxml"));
                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    userNameTextFeild.setStyle("-fx-border-color:RED");
                    passwordTextField.setStyle("-fx-border-color:RED");
                    passwordField.setStyle("-fx-border-color:RED");

                }

            } else {
                userNameTextFeild.setStyle("-fx-border-color:RED");
                passwordTextField.setStyle("-fx-border-color:RED");
                passwordField.setStyle("-fx-border-color:RED");
            }
        });
        showPasswordCheckBox.selectedProperty().addListener((ov, old_Value, new_Value) -> {
            if (new_Value) {
                passwordField.setVisible(true);
                passwordField.setText(passwordTextField.getText());
                passwordTextField.setText("");

            } else {
                passwordField.setVisible(false);
                passwordTextField.setText(passwordField.getText());
                passwordField.setText("");
            }
        });
    }

}
