package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import application.models.Position;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
@Component("Positions")
public class Positions {
	
	@Autowired
	private DataSource dataSource;
	
	public ObservableList<Position> getPositions() {
		ObservableList<Position> positions = FXCollections.observableArrayList();
		Connection conn = null;
		
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM positions");
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				positions.add(new Position(rs.getString("id"), rs.getString("name")));
			}
			rs.close();
			ps.close();
			
			return positions;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			}catch (SQLException e) {}
		}
		return positions;
	}
	
	public Position getPosition(int positionId) {
		Connection conn = null;
		Position position = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM positions WHERE id = ?");
			ps.setInt(1,positionId);
			
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
//				position = new Position(positionId, rs.getString("name"));
			}
			rs.close();
			ps.close();
			
			return position;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			}catch (SQLException e) {}
		}
		return position;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
