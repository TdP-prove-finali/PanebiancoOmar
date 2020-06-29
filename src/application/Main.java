package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.Model;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
			BorderPane root = loader.load();
			
			MainController controller = loader.getController();
			Model model = new Model(controller.loadHomeFactorSetting(), controller.loadPointsFactorSetting(), controller.loadPointsFactorValue(),
					controller.loadRedCardMultiplierValue(), controller.loadStandardSimulationSetting(), controller.loadQuickSimulationSetting(),
					controller.loadMultipleQuickSimulationSetting(), controller.loadMultipleQuickSimulationValue());
			controller.setModel(model);
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.setMinWidth(1208);
			primaryStage.setMinHeight(748);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
