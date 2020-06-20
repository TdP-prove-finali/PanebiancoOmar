package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class DialogController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label txtDialog;
    
    @FXML
    private ImageView imgSerieALogoTitle;

    @FXML
    void doGotIt(ActionEvent event) {
    	Node source = (Node)event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
    	assert txtDialog != null : "fx:id=\"txtDialog\" was not injected: check your FXML file 'dialog.fxml'.";
        assert imgSerieALogoTitle != null : "fx:id=\"imgSerieALogoTitle\" was not injected: check your FXML file 'dialog.fxml'.";
    }
    
    public void setTxtDialog(String dialog) {
        this.txtDialog.setText(dialog);
        setImages();
    }
    
    private void setImages() {
		this.imgSerieALogoTitle.setImage(new Image("img/seriea.png"));
	}
    
}