package com.wolfs.fwa.sql;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.microsoft.sqlserver.jdbc.StringUtils;
import com.wolfs.fwa.model.Event;
import com.wolfs.fwa.model.Firework;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class FireworkDaoImpl implements FireworkDao {

	@Resource
	private EventDao eventDao;

	private static final String QUERY_INSERT = "INSERT INTO [dbo].[Firework]([ID],[Name],[PINS],[Events],[Songs],[Completed])VALUES(NEWID(),'%s','%s','%s','%s','%s')";
	private static final String QUERY_DELETE = "DELETE FROM [dbo].[Firework] WHERE ID='%s'";
	private static final String QUERY_SAVE = "UPDATE [dbo].[Firework] SET [Name] = '%s', [PINS] = '%s', [Events] = '%s', [Songs] = '%s', [Completed] = '%s' WHERE [ID] = '%s'";

	@Override
	public Firework getFireworkById(String id) throws SQLException {
		ResultSet rs = GeneralDatabaseStuff.doDatabaseShit("SELECT * FROM FIREWORK WHERE ID='" + id + "'");
		return getFireworkFromResultSet(rs).get(0);
	}

	@Override
	public List<Firework> getFireworks() throws SQLException {
		ResultSet rs = GeneralDatabaseStuff.doDatabaseShit("SELECT * FROM FIREWORK");
		return getFireworkFromResultSet(rs);
	}

	private List<Firework> getFireworkFromResultSet(ResultSet rs) throws SQLException {
		List<Firework> fireworks = new ArrayList<>();
		while (rs.next()) {
			String id = rs.getString("ID");
			String name = rs.getString("Name");
			int pins = rs.getInt("PINS");
			String eventRaw = rs.getString("Events");
			String songRaw = rs.getString("Songs");
			boolean completed = rs.getBoolean("Completed");

			List<String> eventList = processRawStrings(eventRaw);
			List<String> songList = processRawStrings(songRaw);

			Firework temp = new Firework(id, name, pins, eventList, songList, completed);
			fireworks.add(temp);
		}
		return fireworks;
	}

	private List<String> processRawStrings(String raw) {
		List<String> processedStrings = new ArrayList<>();
		if (!StringUtils.isEmpty(raw)) {
			if (raw.contains(",")) {
				processedStrings = Arrays.asList(raw.split("\\s*,\\s*"));
			} else {
				processedStrings.add(raw);
			}
		}
		return processedStrings;
	}

	@Override
	public void deleteFireworkById(String id) throws SQLException {
		String query = String.format(QUERY_DELETE, id);
		log.warn("Firework " + id + " was deleted: " + query);
		GeneralDatabaseStuff.doDatabaseShit(query);
	}

	@Override
	public List<File> parseFireworkToScripts(Firework firework) throws SQLException {
		List<Event> events = eventDao.getAllEventsToFirework(firework.getId());
		return null;
	}

	@Override
	public void createNewFireworkInDatabase(Firework firework) throws SQLException {
		String query = String.format(QUERY_INSERT, firework.getFireworkName(), firework.getPins(), firework.getEvents(),
				firework.getSongs(), firework.getCompleted());
		log.warn("New firework created: " + query);
		GeneralDatabaseStuff.doDatabaseShitWithoutReturn(query);
	}

	@Override
	public void countPins(Firework firework) throws SQLException {
		List<Integer> pins = new ArrayList<>();
		List<Event> events = eventDao.getAllEventsToFirework(firework.getId());

		for (Event event : events) {
			if (!pins.contains(event.getGpioPin())) {
				pins.add(event.getGpioPin());
			}
		}
		firework.setPins(pins.size());
	}

	@Override
	public void saveFirework(Firework firework) throws SQLException {
		String query = String.format(QUERY_SAVE, firework.getFireworkName(), firework.getPins(), firework.getEvents(),
				firework.getSongs(), firework.getCompleted(), firework.getId());
		log.warn("Firework saved: " + query);
		GeneralDatabaseStuff.doDatabaseShit(query);

	}

}
