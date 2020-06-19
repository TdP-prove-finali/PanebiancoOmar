package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DialogController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label txtDialog;

    @FXML
    void doGotIt(ActionEvent event) {
    	Node source = (Node)event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
        assert txtDialog != null : "fx:id=\"txtDialog\" was not injected: check your FXML file 'dialog.fxml'.";
    }
    
    public void setTxtDialog(String dialog) {
        this.txtDialog.setText(dialog);
      }
    
}