package backupLocations;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

public class BlController{

    @FXML
    private TextFlow sideIcon;

    @FXML
    private VBox sidebarNav;

    @FXML
    private HBox userItem;

    @FXML
    private HBox auditItem;

    @FXML
    private HBox backupItem;

    @FXML
    private HBox settingsItem;

    @FXML
    private HBox logoutItem;

    @FXML
    private JFXTextArea taAudit;

    @FXML
    private JFXTextArea taCloud;

    @FXML
    private JFXTextArea taMessage;

    @FXML
    private JFXTextArea taUser;

    @FXML
    private JFXButton btnFindAudit;

    @FXML
    private JFXButton btnFindCloud;

    @FXML
    private JFXButton btnFindMessage;

    @FXML
    private JFXButton btnDoFindUser;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnAccept;

    @FXML
    void changePage(MouseEvent event) {

    }

    @FXML
    void doAccept(ActionEvent event) {

    }

    @FXML
    void doFindAudit(ActionEvent event) {

    }

    @FXML
    void doFindCloud(ActionEvent event) {

    }

    @FXML
    void doFindMessage(ActionEvent event) {

    }

    @FXML
    void doFindUser(ActionEvent event) {

    }

    @FXML
    void gotoBack(ActionEvent event) {

    }

    @FXML
    void hideHoverColor(MouseEvent event) {

    }

    @FXML
    void showHoverColor(MouseEvent event) {

    }

    @FXML
    void showSidebar(MouseEvent event) {

    }

}
