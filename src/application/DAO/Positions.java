package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
		                Position contact = new Position(result.getInt("id"), result.getString("name"));
		                return contact;
		            }   
		        });	
			 ObservableList<Position> profiles = FXCollections.observableArrayList(result);
		return profiles;
	}
	
	public int Insert(String position) {

		 final PreparedStatementCreator psc = new PreparedStatementCreator() {
		      @Override
		      public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
		        final PreparedStatement ps = connection.prepareStatement("INSERT INTO positions (name) VALUES(?)",
		            Statement.RETURN_GENERATED_KEYS);
		        ps.setString(1, position);
		        return ps;
		      }
		    };

		    final KeyHolder holder = new GeneratedKeyHolder();
		    jdbcTemplate.update(psc, holder);
		    final int newNameId = holder.getKey().intValue();
		    return newNameId;
	}
	
	public void delete(int id) {
		String sql = "DELETE FROM positions WHERE id=?";
		Object[] params = {id};
		jdbcTemplate.update(sql, params);
	}
}
