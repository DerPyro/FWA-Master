package com.wolfs.fwa.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wolfs.fwa.model.Position;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class PositionDaoImpl implements PositionDao {

	private static final String QUERY_GETALL = "SELECT * FROM [dbo].[Position]";
	private static final String QUERY_INSERT = "INSERT INTO [dbo].[Position]([NAME])VALUES('%s')";
	private static final String QUERY_DELETE = "DELETE FROM [dbo].[Position] WHERE NAME='%s'";

	@Override
	public List<Position> getAllPositions() throws SQLException {
		String query = QUERY_GETALL;
		log.debug("All Positions loaded: " + query);
		ResultSet rs = GeneralDatabaseStuff.doDatabaseShit(query);
		return getPositionsFromResultSet(rs);
	}

	private List<Position> getPositionsFromResultSet(ResultSet rs) throws SQLException {
		List<Position> positions = new ArrayList<>();
		while (rs.next()) {
			String name = rs.getString("NAME");
			positions.add(new Position(name));
		}
		return positions;
	}

	@Override
	public void deletePositionByName(String name) throws SQLException {
		String query = String.format(QUERY_DELETE, name);
		log.warn("Position " + name + " was deleted: " + query);
		GeneralDatabaseStuff.doDatabaseShitWithoutReturn(query);
	}

	@Override
	public void createNewPosition(Position position) throws SQLException {
		boolean exsist = false;
		List<Position> positions = getAllPositions();
		for (Position exsistingPosition : positions) {
			if (exsistingPosition.getName() == position.getName()) {
				exsist = true;
			}
		}

		if (!exsist) {
			String query = String.format(QUERY_INSERT, position.getName());
			log.warn("Position " + position.getName() + " was deleted: " + query);
			GeneralDatabaseStuff.doDatabaseShitWithoutReturn(query);
		}
	}

}
