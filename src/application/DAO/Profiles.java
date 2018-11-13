package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import application.models.Position;
import application.models.Profile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


@Component("Profiles")
public class Profiles {
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
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
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
//		this.dataSource = dataSource;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
