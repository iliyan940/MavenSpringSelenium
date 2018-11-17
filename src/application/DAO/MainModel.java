package application.DAO;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class MainModel {
	@Autowired
	protected DataSource dataSource;
	@Autowired
	protected JdbcTemplate jdbcTemplate;
}
