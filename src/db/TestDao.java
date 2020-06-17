package db;

import model.Team;

public class TestDao {

	public static void main(String[] args) {

		SerieADao dao = new SerieADao();
		
		for(Team team : dao.getTeamList())
			System.out.println(team);
		
	}

}
