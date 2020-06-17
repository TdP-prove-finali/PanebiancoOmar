package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Match;
import model.Team;
import model.TeamStats;

public class SerieADao {
	
	public List<Team> getTeamsList() {
		String sql = "SELECT * FROM squadre s, statistiche_casa sc, statistiche_trasferta st "
				+ "WHERE s.id_squadra = sc.id_squadra AND s.id_squadra = st.id_squadra";
		List<Team> teamsList = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);	
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				try {
					double homeTotalMadeShots = res.getDouble("sc.tiri_totali_fatti");
					double homeOpenPlayMadeShots = res.getDouble("sc.tiri_azione_fatti");
					double homeFreeKickMadeShots = res.getDouble("sc.tiri_piazzato_fatti");
					double homePenaltyMadeShots = res.getDouble("sc.tiri_rigore_fatti");
					double homeOpenPlayMadeGoals = res.getDouble("sc.gol_azione_realizzati");
					double homeFreeKickMadeGoals = res.getDouble("sc.gol_piazzato_realizzati");
					double homePenaltyMadeGoals = res.getDouble("sc.gol_rigore_realizzati");
					double homeTotalConcededShots = res.getDouble("sc.tiri_totali_concessi");
					double homeOpenPlayConcededShots = res.getDouble("sc.tiri_azione_concessi");
					double homeFreeKickConcededShots = res.getDouble("sc.tiri_piazzato_concessi");
					double homePenaltyConcededShots = res.getDouble("sc.tiri_rigore_concessi");
					double homeOpenPlayConcededGoals = res.getDouble("sc.gol_azione_subiti");
					double homeFreeKickConcededGoals = res.getDouble("sc.gol_piazzato_subiti");
					double homePenaltyConcededGoals = res.getDouble("sc.gol_rigore_subiti");
					
					TeamStats homeStats = new TeamStats(homeTotalMadeShots, homeOpenPlayMadeShots, homeFreeKickMadeShots, homePenaltyMadeShots,
							homeOpenPlayMadeGoals, homeFreeKickMadeGoals, homePenaltyMadeGoals, 
							homeTotalConcededShots, homeOpenPlayConcededShots, homeFreeKickConcededShots, homePenaltyConcededShots,
							homeOpenPlayConcededGoals, homeFreeKickConcededGoals, homePenaltyConcededGoals);
					
					double awayTotalMadeShots = res.getDouble("st.tiri_totali_fatti");
					double awayOpenPlayMadeShots = res.getDouble("st.tiri_azione_fatti");
					double awayFreeKickMadeShots = res.getDouble("st.tiri_piazzato_fatti");
					double awayPenaltyMadeShots = res.getDouble("st.tiri_rigore_fatti");
					double awayOpenPlayMadeGoals = res.getDouble("st.gol_azione_realizzati");
					double awayFreeKickMadeGoals = res.getDouble("st.gol_piazzato_realizzati");
					double awayPenaltyMadeGoals = res.getDouble("st.gol_rigore_realizzati");
					double awayTotalConcededShots = res.getDouble("st.tiri_totali_concessi");
					double awayOpenPlayConcededShots = res.getDouble("st.tiri_azione_concessi");
					double awayFreeKickConcededShots = res.getDouble("st.tiri_piazzato_concessi");
					double awayPenaltyConcededShots = res.getDouble("st.tiri_rigore_concessi");
					double awayOpenPlayConcededGoals = res.getDouble("st.gol_azione_subiti");
					double awayFreeKickConcededGoals = res.getDouble("st.gol_piazzato_subiti");
					double awayPenaltyConcededGoals = res.getDouble("st.gol_rigore_subiti");
					
					TeamStats awayStats = new TeamStats(awayTotalMadeShots, awayOpenPlayMadeShots, awayFreeKickMadeShots, awayPenaltyMadeShots,
							awayOpenPlayMadeGoals, awayFreeKickMadeGoals, awayPenaltyMadeGoals, 
							awayTotalConcededShots, awayOpenPlayConcededShots, awayFreeKickConcededShots, awayPenaltyConcededShots,
							awayOpenPlayConcededGoals, awayFreeKickConcededGoals, awayPenaltyConcededGoals);
				
					int teamId = res.getInt("s.id_squadra");
					String teamName = res.getString("s.nome_squadra").toUpperCase();
					int homeMatches = res.getInt("s.pg_casa");
					int homeWins = res.getInt("s.v_casa");
					int homeDraws = res.getInt("s.n_casa");
					int homeLosses = res.getInt("s.p_casa");
					int homeMadeGoals = res.getInt("s.gf_casa");
					int homeConcededGoals = res.getInt("s.gs_casa");
					int awayMatches	= res.getInt("s.pg_trasferta");
					int awayWins = res.getInt("s.v_trasferta");
					int awayDraws = res.getInt("s.n_trasferta");
					int awayLosses = res.getInt("s.p_trasferta");
					int awayMadeGoals = res.getInt("s.gf_trasferta");
					int awayConcededGoals = res.getInt("s.gs_trasferta");
					
					Team team = new Team(teamId, teamName, homeMatches, homeWins, homeDraws, homeLosses, homeMadeGoals, homeConcededGoals, 
							awayMatches, awayWins, awayDraws, awayLosses, awayMadeGoals, awayConcededGoals, homeStats, awayStats);
					
					teamsList.add(team);
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return teamsList;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Match> getMatchesList() {
		List<Team> teamsList = getTeamsList();
		Map<Integer, Team> teamsMap = new HashMap<>();
		for(Team team : teamsList)
			teamsMap.put(team.getTeamId(), team);
		
		String sql = "SELECT * FROM partite";
		List<Match> matchesList = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);	
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				try {
					int matchId = res.getInt("id_partita");
					int day = res.getInt("giornata");
					int homeTeamId = res.getInt("id_squadra_casa");
					int awayTeamId = res.getInt("id_squadra_trasferta");
					
					Match match = new Match(matchId, day, teamsMap.get(homeTeamId), teamsMap.get(awayTeamId), -1, -1);
					
					matchesList.add(match);
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return matchesList;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
