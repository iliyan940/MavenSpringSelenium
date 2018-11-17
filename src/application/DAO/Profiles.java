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

import application.models.Profile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Component("Profiles")
public class Profiles extends MainModel{
	
	public ObservableList<Profile> get() {		
		 String sql = "SELECT * FROM profiles";
		 
			 List<Profile> result = jdbcTemplate.query(sql, new RowMapper<Profile>() {
		            public Profile mapRow(ResultSet result, int rowNum) throws SQLException {
		                
		            	Profile contact = new Profile(
		                		result.getInt("id"), 
		                		result.getString("email"), 
		                		result.getString("password")
		                		);
		            	
		                return contact;
		            }
		        });	
			 ObservableList<Profile> profiles = FXCollections.observableArrayList(result);
		return profiles;
	}
	
	public int Insert(String email, String password) {

		 final PreparedStatementCreator psc = new PreparedStatementCreator() {
		      @Override
		      public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
		        final PreparedStatement ps = connection.prepareStatement("INSERT INTO profiles (email, password, date_created) VALUES(?, ?, ?)",
		            Statement.RETURN_GENERATED_KEYS);
		        ps.setString(1, email);
		        ps.setString(2, password);
		        ps.setString(3, "2018-11-12");
		        return ps;
		      }
		    };

		    final KeyHolder holder = new GeneratedKeyHolder();
		    jdbcTemplate.update(psc, holder);
		    final int newNameId = holder.getKey().intValue();
		    return newNameId;
	}
	
	public void delete(int id) {
		String sql = "DELETE FROM profiles WHERE id=?";
		Object[] params = {id};
		jdbcTemplate.update(sql, params);
	}
	
	public void update(Profile updatedProfile) {
		String sql="UPDATE profiles SET email=?, password=?, date_updated=? WHERE id=?";
		Object[] params = { updatedProfile.getEmail(), updatedProfile.getPassword(), "2018-11-12", updatedProfile.getId()  };
		jdbcTemplate.update(sql, params);
	}
	
	public void setActive(int id) {
		String sql="UPDATE profiles SET active=1 WHERE id=?";
		Object[] params = {id};
		jdbcTemplate.update(sql, params);
		
		String sql2="UPDATE profiles SET active=0 WHERE id!=?";
		Object[] params2 = {id};
		jdbcTemplate.update(sql2, params2);
	}
	
}
