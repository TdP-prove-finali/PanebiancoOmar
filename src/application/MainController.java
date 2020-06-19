package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
    private Button buttonStartSimulation;

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
    private VBox boxNextDay;

    @FXML
    private Label labelNextDay;

    @FXML
    private GridPane gridNextDay;

    @FXML
    private Button buttonSimulateNextDay;

    @FXML
    private Button buttonEndSimulation;

    @FXML
    void doDisableMultipleSimulationBox(ActionEvent event) {
    	this.boxMultipleSimulation.setDisable(true);
    }

    @FXML
    void doEnableMultipleSimulationBox(ActionEvent event) {
    	this.boxMultipleSimulation.setDisable(false);
    }
    
    @FXML
    void doEndSimulation(ActionEvent event) {
    	model.reset();
    	
    	this.gridLeagueTable.getChildren().clear();
    	this.gridNextDay.getChildren().clear();
    	this.gridSimulatedDay.getChildren().clear();
    	
    	setLeagueTableGrid();
		setNextDayGrid(model.getFirstDayToSimulate());
		
		this.buttonStartSimulation.setDisable(false);
		this.buttonSimulateNextDay.setDisable(false);
		this.buttonSimulateNextDay.setVisible(false);
		this.buttonEndSimulation.setDisable(true);
		this.buttonEndSimulation.setVisible(false);
		this.boxNextDay.setVisible(true);
		this.boxSimulatedDay.setVisible(false);
    }

    @FXML
    void doSimulateNextDay(ActionEvent event) {
    	int dayToSimulate = model.getNextDayToSimulate();
    	List<Match> simulatedDay = model.simulateDay(dayToSimulate);
    	
    	if(dayToSimulate < model.getLastDaytoSimulate()) {
    		this.gridSimulatedDay.getChildren().clear();
    		setSimulatedDayGrid(simulatedDay);
    		this.gridNextDay.getChildren().clear();
    		setNextDayGrid(dayToSimulate + 1);
    		this.gridLeagueTable.getChildren().clear();
    		setLeagueTableGrid();
    		
    		model.setNextDayToSimulate(dayToSimulate + 1);
    		
    	} else {
    		this.gridSimulatedDay.getChildren().clear();
    		setSimulatedDayGrid(simulatedDay);
    		this.gridNextDay.getChildren().clear();
    		this.gridLeagueTable.getChildren().clear();
    		setLeagueTableGrid();
    		
    		this.boxNextDay.setVisible(false);
    		this.buttonSimulateNextDay.setDisable(true);
    		this.buttonEndSimulation.setDisable(false);
    	}
    }

	@FXML
    void doStartSimulation(ActionEvent event) {
    	if(this.checkHomeFactor.isSelected())
    		model.setHomeFactor(true);
    	else
    		model.setHomeFactor(false);
    	if(this.checkPointsFactor.isSelected()) {
    		model.setPointsFactorActivated(true);
    		model.setPointsFactor(this.sliderPointsFactor.getValue());
    	} else
    		model.setPointsFactorActivated(false);
    	model.setRedCardMultiplier(this.sliderRedCardMultiplier.getValue());
    	
    	if(this.radioStandardSimulation.isSelected())
    		model.setStandardSimulation(true);
    	else
    		model.setStandardSimulation(false);
    	if(this.radioQuickSimulation.isSelected())
    		model.setQuickSimulation(true);
    	else
    		model.setQuickSimulation(false);
    	if(this.radioMultipleQuickSimulation.isSelected()) {
    		model.setMultipleQuickSimulation(true);
    		int multipleSimulationNumber = 0;
    		try {
				multipleSimulationNumber = Integer.parseInt(this.textMultipleSimulationNumber.getText().trim());
			} catch (NumberFormatException e) {
				try {
			        FXMLLoader loader = new FXMLLoader(getClass().getResource("dialog.fxml"));
			        BorderPane root = loader.<BorderPane>load();
			        DialogController controller = loader.<DialogController>getController();
			        controller.setTxtDialog("Please insert a correct number of multiple simulations.");
			        Parent content = root;
			        Scene scene = new Scene(content);
			        Stage window = new Stage();
			        window.setScene(scene);
			        window.setResizable(false);
			        window.show();
			        return;
			     } catch (IOException ex) {
			        ex.printStackTrace();
			        e.printStackTrace();
			        return;
			     } 
			}
    		model.setMultipleSimulationNumber(multipleSimulationNumber);
    	}
    	else
    		model.setMultipleQuickSimulation(false);
    	
    	if(model.isStandardSimulation()) {
    		this.buttonStartSimulation.setDisable(true);
        	this.buttonSimulateNextDay.setVisible(true);;
        	this.buttonEndSimulation.setVisible(true);;
        	this.boxSimulatedDay.setVisible(true);;
        	
        	Map<Integer, List<Match>> simulationsMap = model.startSimulation();
        	List<Match> simulatedDay = simulationsMap.get(model.getFirstDayToSimulate());
        	
        	setSimulatedDayGrid(simulatedDay);
        	
        	this.gridNextDay.getChildren().clear();
        	setNextDayGrid(model.getNextDayToSimulate());
    		
    		this.gridLeagueTable.getChildren().clear();
    		this.setLeagueTableGrid();
    		
    	} else if(model.isQuickSimulation()) {
    		return;
    		
    	} else {
    		return;
    		
    	}
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
        assert buttonStartSimulation != null : "fx:id=\"buttonStartSimulation\" was not injected: check your FXML file 'main.fxml'.";
        assert boxResult != null : "fx:id=\"boxResult\" was not injected: check your FXML file 'main.fxml'.";
        assert gridLeagueTable != null : "fx:id=\"gridLeagueTable\" was not injected: check your FXML file 'main.fxml'.";
        assert boxDays != null : "fx:id=\"boxDays\" was not injected: check your FXML file 'main.fxml'.";
        assert boxSimulatedDay != null : "fx:id=\"boxSimulatedDay\" was not injected: check your FXML file 'main.fxml'.";
        assert labelSimulatedDay != null : "fx:id=\"labelSimulatedDay\" was not injected: check your FXML file 'main.fxml'.";
        assert gridSimulatedDay != null : "fx:id=\"gridSimulatedDay\" was not injected: check your FXML file 'main.fxml'.";
        assert boxNextDay != null : "fx:id=\"boxNextDay\" was not injected: check your FXML file 'main.fxml'.";
        assert labelNextDay != null : "fx:id=\"labelNextDay\" was not injected: check your FXML file 'main.fxml'.";
        assert gridNextDay != null : "fx:id=\"gridNextDay\" was not injected: check your FXML file 'main.fxml'.";
        assert buttonSimulateNextDay != null : "fx:id=\"buttonSimulateNextDay\" was not injected: check your FXML file 'main.fxml'.";
        assert buttonEndSimulation != null : "fx:id=\"buttonEndSimulation\" was not injected: check your FXML file 'main.fxml'.";
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
		setLeagueTableGrid();
		setNextDayGrid(model.getFirstDayToSimulate());
	}
	
    private void setSimulatedDayGrid(List<Match> simulatedDay) {
    	this.labelSimulatedDay.setText("DAY " + simulatedDay.get(0).getDay());
    	for(int i = 1; i <= simulatedDay.size(); i ++) {
			HBox boxMatch = new HBox();
			boxMatch.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			boxMatch.setAlignment(Pos.CENTER);
			boxMatch.setSpacing(5);
			ImageView homeLogo = new ImageView(new Image(getLogoPathFromTeam(simulatedDay.get(i - 1).getHomeTeam())));
			homeLogo.setFitHeight(15);
			homeLogo.setFitWidth(-1);;
			boxMatch.getChildren().add(homeLogo);
			boxMatch.getChildren().add(new Label(simulatedDay.get(i - 1).getHomeTeam().getTeamName()));
			boxMatch.getChildren().add(new Label("-"));
			
			ImageView awayLogo = new ImageView(new Image(getLogoPathFromTeam(simulatedDay.get(i - 1).getAwayTeam())));
			awayLogo.setFitHeight(15);
			awayLogo.setFitWidth(-1);;
			boxMatch.getChildren().add(new Label(simulatedDay.get(i - 1).getAwayTeam().getTeamName()));
			boxMatch.getChildren().add(awayLogo);
			
			this.gridSimulatedDay.add(boxMatch, 0, i);
			this.gridSimulatedDay.add(new Label(simulatedDay.get(i - 1).getHomeTeamGoals()
					+ "-" + simulatedDay.get(i - 1).getAwayTeamGoals()), 1, i);
		}
	}

	private void setNextDayGrid(int day) {
		this.labelNextDay.setText("DAY " + day);
		
		List<Match> nextMatch = model.getMatchesListByDay(day);
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

	private void setLeagueTableGrid() {
		this.gridLeagueTable.add(getColumnTitleLabel("POS"), 0, 1);
		this.gridLeagueTable.add(getColumnTitleLabel("TEAM"), 1, 1);
		this.gridLeagueTable.add(getColumnTitleLabel("PTS"), 2, 1);
		this.gridLeagueTable.add(getColumnTitleLabel("PM"), 3, 1);
		this.gridLeagueTable.add(getColumnTitleLabel("W"), 4, 1);
		this.gridLeagueTable.add(getColumnTitleLabel("D"), 5, 1);
		this.gridLeagueTable.add(getColumnTitleLabel("L"), 6, 1);
		this.gridLeagueTable.add(getColumnTitleLabel("GF"), 7, 1);
		this.gridLeagueTable.add(getColumnTitleLabel("GA"), 8, 1);
		this.gridLeagueTable.add(getColumnTitleLabel("HPM"), 9, 1);
		this.gridLeagueTable.add(getColumnTitleLabel("HW"), 10, 1);
		this.gridLeagueTable.add(getColumnTitleLabel("HD"), 11, 1);
		this.gridLeagueTable.add(getColumnTitleLabel("HL"), 12, 1);
		this.gridLeagueTable.add(getColumnTitleLabel("HGF"), 13, 1);
		this.gridLeagueTable.add(getColumnTitleLabel("HGA"), 14, 1);
		this.gridLeagueTable.add(getColumnTitleLabel("APM"), 15, 1);
		this.gridLeagueTable.add(getColumnTitleLabel("AW"), 16, 1);
		this.gridLeagueTable.add(getColumnTitleLabel("AD"), 17, 1);
		this.gridLeagueTable.add(getColumnTitleLabel("AL"), 18, 1);
		this.gridLeagueTable.add(getColumnTitleLabel("AGF"), 19, 1);
		this.gridLeagueTable.add(getColumnTitleLabel("AGA"), 20, 1);
		
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

	private Label getColumnTitleLabel(String columnTitle) {
		Label label = new Label();
		label.setPadding(new Insets(0,0,5,0));
		label.setText(columnTitle);
		return label;
	}

	private String getLogoPathFromTeam(Team team) {
		return "img/" + team.getTeamName().toLowerCase() + ".png";
	}

	private void setImages() {
		this.imgSerieALogoTitle.setImage(new Image("img/seriea.png"));
	}
	
}
