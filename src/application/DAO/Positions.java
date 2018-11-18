package application.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import application.models.Position;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Component("Positions")
public class Positions extends MainModel {
	
	public ObservableList<Position> get() {		
		 String sql = "SELECT * FROM positions";
	
			 List<Position> result = jdbcTemplate.query(sql, new RowMapper<Position>() {
		            public Position mapRow(ResultSet result, int rowNum) throws SQLException {
		                Position contact = new Position(result.getString("id"), result.getString("name"));
		                return contact;
		            }   
		        });	
			 ObservableList<Position> profiles = FXCollections.observableArrayList(result);
		return profiles;
	}
}
