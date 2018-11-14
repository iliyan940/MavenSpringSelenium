package application.DAO;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class MainModel {
	
	@Autowired
	protected DataSource dataSource;
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
//	public DataSource getDataSource() {
//		return dataSource;
//	}
//	
//	@Autowired
//	public void setDataSource(DataSource dataSource) {
//		this.dataSource = dataSource;
//		this.jdbcTemplate = new JdbcTemplate(dataSource);
//	}
//
//	public JdbcTemplate getJdbcTemplate() {
//		return jdbcTemplate;
//	}
//	
//	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
//		this.jdbcTemplate = jdbcTemplate;
//	}

}
