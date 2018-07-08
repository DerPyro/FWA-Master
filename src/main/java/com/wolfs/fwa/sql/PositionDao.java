package com.wolfs.fwa.sql;

import java.sql.SQLException;
import java.util.List;

import com.wolfs.fwa.model.Position;

public interface PositionDao {
	
	List<Position> getAllPositions() throws SQLException;

	void deletePositionByName(String name) throws SQLException;

	void createNewPosition(Position position) throws SQLException;

}
