package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;

import BaseClass.Student;
import Model.StudentQueries;
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
    Label userNameLabel;
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
        userNameLabel.setText(Student.getCurrentUser());

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

                int confirm = studentQueries.changeUsername(idLabel.getText(), oldUserNameTextField.getText(),
                        newUserNameTextField.getText());
                System.out.println(confirm);
                if (confirm == 1) {
                    Student.setCurrentUser(newUserNameTextField.getText());
                    userNameLabel.setText(Student.getCurrentUser());
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

}
