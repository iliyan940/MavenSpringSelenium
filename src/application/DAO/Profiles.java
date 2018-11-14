package application.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import application.models.Profile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Component("Profiles")
public class Profiles extends MainModel{
	
	public ObservableList<Profile> getProfiles() {		
		 String sql = "SELECT * FROM profiles";
		 
			 List<Profile> result = jdbcTemplate.query(sql, new RowMapper<Profile>() {
		            public Profile mapRow(ResultSet result, int rowNum) throws SQLException {
		                Profile contact = new Profile(result.getString("email"));
		                return contact;
		            }
		        });	
			 ObservableList<Profile> profiles = FXCollections.observableArrayList(result);
		return profiles;
	}
	
	public void Insert(String username, String password) {
		String sql = "INSERT INTO profiles (email, password, date_created) VALUES(?, ?, ?)";
		jdbcTemplate.update(sql, username, password, "2018-11-12");
	}
	
}
