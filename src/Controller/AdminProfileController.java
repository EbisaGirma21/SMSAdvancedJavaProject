package Controller;

import Model.AdminQueries;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;

import BaseClass.Admin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminProfileController implements Initializable {

    @FXML
    Label SSNLabel;

    @FXML
    JFXButton addAccountBtn;

    @FXML
    JFXButton assignBtn;

    @FXML
    Label firstNameLabel;

    @FXML
    Label genderLabel;

    @FXML
    JFXButton gradeInfoBtn;

    @FXML
    Label lastNameLabel;

    @FXML
    JFXButton logoutBtn;

    @FXML
    Label middleNameLabel;

    @FXML
    JFXButton changePasswordBtn;

    @FXML
    JFXButton changeUserNameBtn;

    @FXML
    JFXButton profileBtn;

    @FXML
    JFXButton studentInfoBtn;

    @FXML
    JFXButton subjectInfoBtn;

    @FXML
    JFXButton teacherInfoBtn;

    @FXML
    Label userNameLabel;
    String email;

    private ObservableList<Admin> adminInfo = FXCollections.observableArrayList();

    AdminQueries adminQueries = new AdminQueries();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        adminInfo.setAll(adminQueries.getAdminByEmail(Admin.getCurrentUser()));
        firstNameLabel.setText(adminInfo.get(0).getFirstName());
        middleNameLabel.setText(adminInfo.get(0).getMiddleName());
        lastNameLabel.setText(adminInfo.get(0).getLastName());
        SSNLabel.setText(adminInfo.get(0).getUserId());
        genderLabel.setText(adminInfo.get(0).getGender());
        userNameLabel.setText(adminInfo.get(0).getUsername());

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
        studentInfoBtn.setOnAction(e -> {
            AnchorPane root;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/ManageStudent.fxml"));

                Stage stage = (Stage) logoutBtn.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e1) {

                e1.printStackTrace();
            }
        });
        teacherInfoBtn.setOnAction(e -> {
            AnchorPane root;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/ManageTeacher.fxml"));
                Stage stage = (Stage) logoutBtn.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e1) {

                e1.printStackTrace();
            }
        });
        gradeInfoBtn.setOnAction(e -> {
            AnchorPane root;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/ManageSection.fxml"));
                Stage stage = (Stage) logoutBtn.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e1) {

                e1.printStackTrace();
            }
        });
        subjectInfoBtn.setOnAction(e -> {
            AnchorPane root;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/ManageSubject.fxml"));
                Stage stage = (Stage) logoutBtn.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e1) {

                e1.printStackTrace();
            }
        });
        assignBtn.setOnAction(e -> {
            try {
                AnchorPane root = FXMLLoader
                        .load(getClass().getClassLoader().getResource("View/AssigningTeacher.fxml"));
                Stage stage = (Stage) logoutBtn.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e1) {

                e1.printStackTrace();
            }

        });
        addAccountBtn.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getClassLoader().getResource("View/AddAdmin.fxml"));
                AnchorPane root = (AnchorPane) loader.load();

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
            String role = "admin";
            String userId = SSNLabel.getText();
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getClassLoader().getResource("View/ChangePassword.fxml"));
                AnchorPane root = (AnchorPane) loader.load();
                ChangePasswordController changePassword = loader.getController();
                changePassword.setRole(role);
                changePassword.setUserId(userId);
                changePassword.setRole(role);
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

        Stage window = new Stage();
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        TextField oldUserNameTextField = new TextField();
        TextField newUserNameTextField = new TextField();
        oldUserNameTextField.setPromptText("Old Username");
        newUserNameTextField.setPromptText("New Username");
        JFXButton submit = new JFXButton("Submit");
        submit.setTextFill(Color.WHITE);
        submit.setStyle("-fx-background-color: #4d77ff; ");
        submit.setButtonType(ButtonType.RAISED);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(submit);
        hBox.setSpacing(55);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(oldUserNameTextField, newUserNameTextField, hBox);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));

        submit.setOnAction(e -> {
            if ((!oldUserNameTextField.getText().equals("")
                    && !newUserNameTextField.getText().equals(""))) {

                int confirm = adminQueries.changeUsername(SSNLabel.getText(), oldUserNameTextField.getText(),
                        newUserNameTextField.getText());
                System.out.println(confirm);
                if (confirm == 1) {
                    Admin.setCurrentUser(newUserNameTextField.getText());
                    userNameLabel.setText(Admin.getCurrentUser());
                    window.close();
                } else {
                    oldUserNameTextField.setStyle("-fx-border-color:RED");
                    newUserNameTextField.setStyle("-fx-border-color:RED");
                }
            }

        });

        Scene scene = new Scene(vBox, 350, 200);
        window.setTitle("Delete Information");
        window.setScene(scene);

        changeUserNameBtn.setOnAction(e -> {

            window.show();

        });
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
