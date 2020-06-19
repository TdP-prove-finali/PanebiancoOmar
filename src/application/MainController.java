package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.Match;
import model.Model;
import model.Team;

public class MainController {
	
	private Model model;

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imgSerieALogoTitle;

    @FXML
    private CheckBox checkHomeFactor;

    @FXML
    private CheckBox checkPointsFactor;

    @FXML
    private Slider sliderPointsFactor;

    @FXML
    private Slider sliderRedCardMultiplier;

    @FXML
    private RadioButton radioStandardSimulation;

    @FXML
    private ToggleGroup toggleSimulation;

    @FXML
    private RadioButton radioQuickSimulation;

    @FXML
    private RadioButton radioMultipleQuickSimulation;

    @FXML
    private VBox boxMultipleSimulation;

    @FXML
    private TextField textMultipleSimulationNumber;

    @FXML
    private VBox boxResult;

    @FXML
    private GridPane gridLeagueTable;

    @FXML
    private HBox boxDays;

    @FXML
    private VBox boxSimulatedDay;

    @FXML
    private Label labelSimulatedDay;

    @FXML
    private GridPane gridSimulatedDay;

    @FXML
    private Label labelNextDay;

    @FXML
    private GridPane gridNextDay;

    @FXML
    private Button buttonSimulateNextDay;

    @FXML
    void doDisableMultipleSimulationBox(ActionEvent event) {
    	this.boxMultipleSimulation.setDisable(true);
    }

    @FXML
    void doEnableMultipleSimulationBox(ActionEvent event) {
    	this.boxMultipleSimulation.setDisable(false);
    }

    @FXML
    void doSimulateNextDay(ActionEvent event) {

    }

    @FXML
    void doStartSimulation(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert imgSerieALogoTitle != null : "fx:id=\"imgSerieALogoTitle\" was not injected: check your FXML file 'main.fxml'.";
        assert checkHomeFactor != null : "fx:id=\"checkHomeFactor\" was not injected: check your FXML file 'main.fxml'.";
        assert checkPointsFactor != null : "fx:id=\"checkPointsFactor\" was not injected: check your FXML file 'main.fxml'.";
        assert sliderPointsFactor != null : "fx:id=\"sliderPointsFactor\" was not injected: check your FXML file 'main.fxml'.";
        assert sliderRedCardMultiplier != null : "fx:id=\"sliderRedCardMultiplier\" was not injected: check your FXML file 'main.fxml'.";
        assert radioStandardSimulation != null : "fx:id=\"radioStandardSimulation\" was not injected: check your FXML file 'main.fxml'.";
        assert toggleSimulation != null : "fx:id=\"toggleSimulation\" was not injected: check your FXML file 'main.fxml'.";
        assert radioQuickSimulation != null : "fx:id=\"radioQuickSimulation\" was not injected: check your FXML file 'main.fxml'.";
        assert radioMultipleQuickSimulation != null : "fx:id=\"radioMultipleQuickSimulation\" was not injected: check your FXML file 'main.fxml'.";
        assert boxMultipleSimulation != null : "fx:id=\"boxMultipleSimulation\" was not injected: check your FXML file 'main.fxml'.";
        assert textMultipleSimulationNumber != null : "fx:id=\"textMultipleSimulationNumber\" was not injected: check your FXML file 'main.fxml'.";
        assert boxResult != null : "fx:id=\"boxResult\" was not injected: check your FXML file 'main.fxml'.";
        assert gridLeagueTable != null : "fx:id=\"gridLeagueTable\" was not injected: check your FXML file 'main.fxml'.";
        assert boxDays != null : "fx:id=\"boxDays\" was not injected: check your FXML file 'main.fxml'.";
        assert boxSimulatedDay != null : "fx:id=\"boxSimulatedDay\" was not injected: check your FXML file 'main.fxml'.";
        assert labelSimulatedDay != null : "fx:id=\"labelSimulatedDay\" was not injected: check your FXML file 'main.fxml'.";
        assert gridSimulatedDay != null : "fx:id=\"gridSimulatedDay\" was not injected: check your FXML file 'main.fxml'.";
        assert labelNextDay != null : "fx:id=\"labelNextDay\" was not injected: check your FXML file 'main.fxml'.";
        assert gridNextDay != null : "fx:id=\"gridNextDay\" was not injected: check your FXML file 'main.fxml'.";
        assert buttonSimulateNextDay != null : "fx:id=\"buttonSimulateNextDay\" was not injected: check your FXML file 'main.fxml'.";

    }
    
	public boolean loadHomeFactorSetting() {
		if(this.checkHomeFactor.isSelected())
			return true;
		else
			return false;
	}

	public boolean loadPointsFactorSetting() {
		if(this.checkPointsFactor.isSelected())
			return true;
		else
			return false;
	}

	public double loadPointsFactorValue() {
		return this.sliderPointsFactor.getValue();
	}

	public double loadRedCardMultiplierValue() {
		return this.sliderRedCardMultiplier.getValue();
	}

	public boolean loadStandardSimulationSetting() {
		if(this.radioStandardSimulation.isSelected())
			return true;
		else
			return false;
	}

	public boolean loadQuickSimulationSetting() {
		if(this.radioQuickSimulation.isSelected())
			return true;
		else
			return false;
	}

	public boolean loadMultipleQuickSimulationSetting() {
		if(this.radioMultipleQuickSimulation.isSelected())
			return true;
		else
			return false;
	}

	public int loadMultipleQuickSimulationValue() {
		return Integer.parseInt(this.textMultipleSimulationNumber.getText());
	}

	public void setModel(Model model) {
		this.model = model;
		
		setImages();
		setInitialLeagueTable();
		setInitialNextDay();
	}

	private void setInitialNextDay() {
		this.labelNextDay.setText("DAY " + model.getFirstDayToSimulate());
		
		List<Match> nextMatch = model.getMatchesListByDay(model.getFirstDayToSimulate());
		for(int i = 1; i <= nextMatch.size(); i ++) {
			HBox boxMatch = new HBox();
			boxMatch.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			boxMatch.setAlignment(Pos.CENTER);
			boxMatch.setSpacing(5);
			ImageView homeLogo = new ImageView(new Image(getLogoPathFromTeam(nextMatch.get(i - 1).getHomeTeam())));
			homeLogo.setFitHeight(15);
			homeLogo.setFitWidth(-1);;
			boxMatch.getChildren().add(homeLogo);
			boxMatch.getChildren().add(new Label(nextMatch.get(i - 1).getHomeTeam().getTeamName()));
			boxMatch.getChildren().add(new Label("-"));
			
			ImageView awayLogo = new ImageView(new Image(getLogoPathFromTeam(nextMatch.get(i - 1).getAwayTeam())));
			awayLogo.setFitHeight(15);
			awayLogo.setFitWidth(-1);;
			boxMatch.getChildren().add(new Label(nextMatch.get(i - 1).getAwayTeam().getTeamName()));
			boxMatch.getChildren().add(awayLogo);
			
			this.gridNextDay.add(boxMatch, 0, i);
		}	
	}

	private void setInitialLeagueTable() {
		List<Team> orderedTeamsList = model.getOrderedTeamsList();
		
		for(int i = 2; i <= orderedTeamsList.size() + 1; i ++) {
			Team team = orderedTeamsList.get(i - 2);
			HBox boxTeam = new HBox();
			boxTeam.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			boxTeam.setAlignment(Pos.CENTER);
			boxTeam.setSpacing(5);
			ImageView logo = new ImageView(new Image(getLogoPathFromTeam(team)));
			logo.setFitHeight(15);
			logo.setFitWidth(-1);;
			boxTeam.getChildren().add(logo);
			boxTeam.getChildren().add(new Label(team.getTeamName()));
			
			int pm = team.getHomeMatches() + team.getAwayMatches();
			int w = team.getHomeWins() + team.getAwayWins();
			int d = team.getHomeDraws() + team.getAwayDraws();
			int l = team.getHomeLosses() + team.getAwayLosses();
			int gf = team.getHomeMadeGoals() + team.getAwayMadeGoals();
			int ga = team.getHomeConcededGoals() + team.getAwayConcededGoals();
			int pts = (w * 3) + d;
			
			this.gridLeagueTable.add(new Label((i - 1) + ""), 0, i);
			this.gridLeagueTable.add(boxTeam, 1, i);
			this.gridLeagueTable.add(new Label(pts + ""), 2, i);
			this.gridLeagueTable.add(new Label(pm + ""), 3, i);
			this.gridLeagueTable.add(new Label(w + ""), 4, i);
			this.gridLeagueTable.add(new Label(d + ""), 5, i);
			this.gridLeagueTable.add(new Label(l + ""), 6, i);
			this.gridLeagueTable.add(new Label(gf + ""), 7, i);
			this.gridLeagueTable.add(new Label(ga + ""), 8, i);
			this.gridLeagueTable.add(new Label(team.getHomeMatches() + ""), 9, i);
			this.gridLeagueTable.add(new Label(team.getHomeWins() + ""), 10, i);
			this.gridLeagueTable.add(new Label(team.getHomeDraws() + ""), 11, i);
			this.gridLeagueTable.add(new Label(team.getHomeLosses() + ""), 12, i);
			this.gridLeagueTable.add(new Label(team.getHomeMadeGoals() + ""), 13, i);
			this.gridLeagueTable.add(new Label(team.getHomeConcededGoals() + ""), 14, i);
			this.gridLeagueTable.add(new Label(team.getAwayMatches() + ""), 15, i);
			this.gridLeagueTable.add(new Label(team.getAwayWins() + ""), 16, i);
			this.gridLeagueTable.add(new Label(team.getAwayDraws() + ""), 17, i);
			this.gridLeagueTable.add(new Label(team.getAwayLosses() + ""), 18, i);
			this.gridLeagueTable.add(new Label(team.getAwayMadeGoals() + ""), 19, i);
			this.gridLeagueTable.add(new Label(team.getAwayConcededGoals() + ""), 20, i);
		}
	}

	private String getLogoPathFromTeam(Team team) {
		return "img/" + team.getTeamName().toLowerCase() + ".png";
	}

	private void setImages() {
		this.imgSerieALogoTitle.setImage(new Image("img/seriea.png"));
	}
	
}
