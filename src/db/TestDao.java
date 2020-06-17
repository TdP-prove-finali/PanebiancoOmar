package db;

import model.Match;
import model.Team;

public class TestDao {

	public static void main(String[] args) {

		SerieADao dao = new SerieADao();
		
		for(Team team : dao.getTeamsList())
			System.out.println(team);
		
		for(Match match : dao.getMatchesList())
			System.out.println(match);
		
	}

}
