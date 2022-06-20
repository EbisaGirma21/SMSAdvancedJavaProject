package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.beans.value.ChangeListener;
import BaseClass.Section;
import Model.SectionQueries;
import Model.TeacherQueries;
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

public class ManageSectionController implements Initializable {

    @FXML
    private JFXButton addSectionBtn;

    @FXML
    private JFXButton backBtn;

    @FXML
    private TableColumn<Section, String> blockNumberColumn;

    @FXML
    private TextField blockNumberTextField;

    @FXML
    private JFXButton deleteSectionBtn;

    @FXML
    private JFXButton editSectionBtn;

    @FXML
    private TableColumn<Section, String> gradeNumberColumn;

    @FXML
    private TextField gradeNumberTextField;

    @FXML
    private TableColumn<Section, String> numberOfStudentColumn;

    @FXML
    private TextField numberOfStudentTextField;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableColumn<Section, String> sectionIdColumn;

    @FXML
    private TextField sectionIdTextField;

    @FXML
    private TableView<Section> sectionManagmentTable;

    @FXML
    private Label selectedRowLabel;

    @FXML
    private JFXButton submitBtn;

    @FXML
    private TableColumn<Section, String> teacherIdColumn;

    @FXML
    private TextField teacherIdTextField;

    final ObservableList<Section> data = FXCollections.observableArrayList();
    private SectionQueries sectioQueries = new SectionQueries();
    private TeacherQueries teacherQueries = new TeacherQueries();

    String deletedRow, sectionId, teacherId, numberOfstudent, blockNumber;

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

        data.setAll(sectioQueries.getSection());

        sectionManagmentTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Section>() {

            @Override
            public void changed(ObservableValue<? extends Section> observable, Section oldValue, Section newValue) {
                if (newValue != null) {
                    selectedRowLabel.setText((newValue.getSectionId() + "    " + newValue.getTeacherId()));
                    deletedRow = newValue.getSectionId();
                    sectionId = newValue.getSectionId();
                    teacherId = newValue.getTeacherId();
                    numberOfstudent = newValue.getNumberOfStudent();
                    blockNumber = newValue.getBlockNumber();

                } else {
                    selectedRowLabel.setText("No Row Selected By Default");
                }

            }

        });
        sectionIdColumn.setCellValueFactory(new PropertyValueFactory<Section, String>("sectionId"));
        teacherIdColumn.setCellValueFactory(new PropertyValueFactory<Section, String>("teacherId"));
        numberOfStudentColumn.setCellValueFactory(new PropertyValueFactory<Section, String>("numberOfStudent"));
        blockNumberColumn.setCellValueFactory(new PropertyValueFactory<Section, String>("blockNumber"));

        sectionManagmentTable.setItems(data);

        addSectionBtn.setOnAction(e -> {
            int confirm = teacherQueries.isThere(teacherIdTextField.getText());
            int confirm1 = sectioQueries.isThereTeacher(teacherIdTextField.getText());
            int confirm2 = sectioQueries.isThere(sectionIdTextField.getText());
            //
            if (sectionIdTextField.getText() == "" || teacherIdTextField.getText() == ""
                    || numberOfStudentTextField.getText() == "" || blockNumberTextField.getText() == "")

            {
                Alert alert = new Alert(AlertType.WARNING, "Field is Empty");
                alert.show();
            } else {
                if (confirm == 1 && confirm1 == 0 && confirm2 == 0) {
                    int response = sectioQueries.addSection(
                            sectionIdTextField.getText(),
                            teacherIdTextField.getText(),
                            numberOfStudentTextField.getText(),
                            blockNumberTextField.getText());

                    if (response == 1) {
                        data.setAll(sectioQueries.getSection());
                    }
                    sectionIdTextField.clear();
                    teacherIdTextField.clear();
                    numberOfStudentTextField.clear();
                    blockNumberTextField.clear();
                } else if (confirm1 == 1 && confirm2 == 0) {
                    teacherIdTextField.setStyle("-fx-border-color:RED");

                } else if (confirm2 == 1 && confirm1 == 0) {
                    sectionIdTextField.setStyle("-fx-border-color:RED");
                } else {
                    teacherIdTextField.setStyle("-fx-border-color:RED");
                    sectionIdTextField.setStyle("-fx-border-color:RED");
                }

            }
        });
        editSectionBtn.setOnAction(e -> {
            submitBtn.setVisible(true);
            sectionIdTextField.setText(sectionId);
            teacherIdTextField.setText(teacherId);
            numberOfStudentTextField.setText(numberOfstudent);
            blockNumberTextField.setText(blockNumber);
        });
        submitBtn.setOnAction(e -> {
            //
            if (sectionIdTextField.getText() == "" || teacherIdTextField.getText() == ""
                    || numberOfStudentTextField.getText() == "" || blockNumberTextField.getText() == "")

            {
                Alert alert = new Alert(AlertType.WARNING, "Field is Empty");
                alert.show();
            } else {

                int response = sectioQueries.updateSection(
                        sectionIdTextField.getText(),
                        teacherIdTextField.getText(),
                        numberOfStudentTextField.getText(),
                        blockNumberTextField.getText());

                if (response == 1) {
                    data.setAll(sectioQueries.getSection());
                }
                sectionIdTextField.clear();
                teacherIdTextField.clear();
                numberOfStudentTextField.clear();
                blockNumberTextField.clear();
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
            int response = sectioQueries.deleteSection(deletedRow);
            System.out.println(response);
            if (response == 1) {
                data.setAll(sectioQueries.getSection());
                window.close();
            }
        });
        btnNo.setOnAction(e -> {
            window.close();
        });
        Scene scene = new Scene(vBox, 350, 100);
        window.setTitle("Delete Information");
        window.setScene(scene);

        deleteSectionBtn.setOnAction(e -> {
            if (!selectedRowLabel.getText().equals("No Row Selected By Default")) {
                window.show();
            }
        });

        FilteredList<Section> filteredSection = new FilteredList<>(data, b -> true);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredSection.setPredicate(Section -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Section.getSectionId().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Section.getTeacherId().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else
                    return false;
            });
        });
        SortedList<Section> sortedSection = new SortedList<>(filteredSection);
        sortedSection.comparatorProperty().bind(sectionManagmentTable.comparatorProperty());
        sectionManagmentTable.setItems(sortedSection);

    }

}
