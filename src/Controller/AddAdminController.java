package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import Model.AdminQueries;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Labeled;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddAdminController implements Initializable {

    @FXML
    private JFXButton cancelButton;

    @FXML
    private AnchorPane amchor;

    @FXML
    private JFXButton registerButton;

    @FXML
    private PasswordField registerConfirmPassword;

    @FXML
    private JFXRadioButton registerFemale;

    @FXML
    private TextField registerFirstName;

    @FXML
    private TextField registerLastName;

    @FXML
    private JFXRadioButton registerMale;

    @FXML
    private TextField registerMiddleName;

    @FXML
    private PasswordField registerPassword;

    @FXML
    private TextField registerSSn;

    @FXML
    private TextField registerUsername;

    ToggleGroup group = new ToggleGroup();
    private AdminQueries adminQueries = new AdminQueries();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        registerMale.setToggleGroup(group);
        registerFemale.setToggleGroup(group);

        registerButton.setOnAction(e -> {

            if (registerFirstName.getText() == "" || registerSSn.getText() == ""
                    || registerLastName.getText() == ""
                    || registerUsername.getText() == ""
                    || registerUsername.getText() == "" || group.getSelectedToggle() == null
                    || registerPassword.getText() == "" || registerConfirmPassword.getText() == ""
                    || !registerConfirmPassword.getText().equals(registerPassword.getText()))

            {
                Alert alert = new Alert(AlertType.WARNING, "Wrong Input");
                alert.show();
            } else {

                int response = adminQueries.addAdmin(
                        registerFirstName.getText(),
                        registerMiddleName.getText(),
                        registerLastName.getText(),
                        registerSSn.getText(),
                        ((Labeled) group.getSelectedToggle()).getText(),
                        registerUsername.getText(),
                        registerPassword.getText());
                if (response == 1) {
                    registerFirstName.clear();
                    registerLastName.clear();
                    registerUsername.clear();
                    registerSSn.clear();
                    registerPassword.clear();
                    registerConfirmPassword.clear();

                    Alert alert = new Alert(AlertType.INFORMATION, "YOU HAVA SUCCESSFULLY REGISTERED");
                    alert.show();
                    Stage stage = (Stage) cancelButton.getScene().getWindow();
                    stage.close();
                } else {
                    Alert alert = new Alert(AlertType.WARNING, "Wrong Input");
                    alert.show();
                }

            }

        });
        cancelButton.setOnAction(e -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        });
    }
}
