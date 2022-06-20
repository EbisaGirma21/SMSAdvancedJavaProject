package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import BaseClass.Subject;
import Model.SectionQueries;
import Model.SubjectQueries;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManageSubjectController implements Initializable {

    @FXML
    private JFXButton addSubjectBtn;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton deleteSubjectBtn;

    @FXML
    private JFXButton editSubjectBtn;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableColumn<Subject, String> subjectCodeColumn;

    @FXML
    private TextField sectionIdTextField;

    @FXML
    private Label selectedRowLabel;

    @FXML
    private TextField subjectCodeTextField;

    @FXML
    private TableColumn<Subject, String> SectionIdColumn;

    @FXML
    private TableView<Subject> subjectManagmentTable;

    @FXML
    private JFXButton submitBtn;

    @FXML
    private TableColumn<Subject, String> subjectNameColumn;

    @FXML
    private TextField subjectNameTextField;

    @FXML
    private TableColumn<Subject, String> teacherIdColumn;

    @FXML
    private TextField teacherIdTextField;

    final ObservableList<Subject> data = FXCollections.observableArrayList();
    private SubjectQueries subjectQueries = new SubjectQueries();
    private SectionQueries sectionQueries = new SectionQueries();

    String deletedRow, subjectCode, subjectName, teacherId, sectionId;

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

        data.setAll(subjectQueries.getSubjects());

        subjectManagmentTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Subject>() {

            @Override
            public void changed(ObservableValue<? extends Subject> observable, Subject oldValue, Subject newValue) {
                if (newValue != null) {
                    selectedRowLabel.setText((newValue.getSubjectCode() + "    " + newValue.getSubjectName()));
                    deletedRow = newValue.getSubjectCode();
                    sectionId = newValue.getSectionCode();
                    teacherId = newValue.getTeacherId();
                    subjectCode = newValue.getSubjectCode();
                    subjectName = newValue.getSubjectName();

                } else {
                    selectedRowLabel.setText("No Row Selected By Default");
                }

            }

        });
        subjectCodeColumn.setCellValueFactory(new PropertyValueFactory<Subject, String>("subjectCode"));
        subjectNameColumn.setCellValueFactory(new PropertyValueFactory<Subject, String>("subjectName"));
        teacherIdColumn.setCellValueFactory(new PropertyValueFactory<Subject, String>("teacherId"));
        SectionIdColumn.setCellValueFactory(new PropertyValueFactory<Subject, String>("sectionCode"));
        subjectManagmentTable.setItems(data);

        addSubjectBtn.setOnAction(e -> {
            int confirm = sectionQueries.isThere(sectionIdTextField.getText());
            //
            if (sectionIdTextField.getText() == "" || subjectCodeTextField.getText() == ""
                    || subjectNameTextField.getText() == "")

            {
                Alert alert = new Alert(AlertType.WARNING, "Field is Empty");
                alert.show();
            } else {
                if (confirm == 1) {

                    int response = subjectQueries.addSubjects(
                            subjectCodeTextField.getText(),
                            subjectNameTextField.getText(),
                            teacherIdTextField.getText(),
                            sectionIdTextField.getText());

                    if (response == 1) {
                        data.setAll(subjectQueries.getSubjects());
                    }
                    subjectCodeTextField.clear();
                    subjectNameTextField.clear();
                    teacherIdTextField.clear();
                    sectionIdTextField.clear();
                } else {
                    sectionIdTextField.setStyle("-fx-border-color:RED");
                }
            }
        });

        editSubjectBtn.setOnAction(e -> {
            submitBtn.setVisible(true);
            subjectCodeTextField.setText(subjectCode);
            subjectNameTextField.setText(subjectName);
            teacherIdTextField.setText(teacherId);
            sectionIdTextField.setText(sectionId);
        });

        submitBtn.setOnAction(e -> {
            //
            if (sectionIdTextField.getText() == "" || subjectCodeTextField.getText() == ""
                    || subjectNameTextField.getText() == "")

            {
                Alert alert = new Alert(AlertType.WARNING, "Field is Empty");
                alert.show();
            } else {

                int response = subjectQueries.updateInfo(
                        subjectCodeTextField.getText(),
                        subjectNameTextField.getText(),
                        teacherIdTextField.getText(),
                        sectionIdTextField.getText());

                if (response == 1) {
                    data.setAll(subjectQueries.getSubjects());
                }
                subjectCodeTextField.clear();
                subjectNameTextField.clear();
                teacherIdTextField.clear();
                sectionIdTextField.clear();

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
            int response = subjectQueries.deleteSubjects(deletedRow);
            System.out.println(response);
            if (response == 1) {
                data.setAll(subjectQueries.getSubjects());
                window.close();
            }
        });
        btnNo.setOnAction(e -> {
            window.close();
        });
        Scene scene = new Scene(vBox, 350, 100);
        window.setTitle("Delete Information");
        window.setScene(scene);

        deleteSubjectBtn.setOnAction(e -> {
            if (!selectedRowLabel.getText().equals("No Row Selected By Default")) {
                window.show();
            }
        });

        FilteredList<Subject> filteredSection = new FilteredList<>(data, b -> true);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredSection.setPredicate(Subject -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Subject.getSubjectCode().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Subject.getSubjectName().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else
                    return false;
            });
        });
        SortedList<Subject> sortedSection = new SortedList<>(filteredSection);
        sortedSection.comparatorProperty().bind(subjectManagmentTable.comparatorProperty());
        subjectManagmentTable.setItems(sortedSection);

    }

}
