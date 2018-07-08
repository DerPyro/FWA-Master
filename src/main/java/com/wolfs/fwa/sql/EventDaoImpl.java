package com.wolfs.fwa.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.microsoft.sqlserver.jdbc.StringUtils;
import com.wolfs.fwa.model.Event;
import com.wolfs.fwa.model.Firework;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class EventDaoImpl implements EventDao {
	
	private static final String QUERY_INSERT = "INSERT INTO [dbo].[Event]([ID],[FIREWORKID],[NAME],[ZEIT],[LIED],[GPIO_PIN],[POSITION])VALUES(NEWID(),'%s','%s','%s','%s','%s','%s')";
	private static final String QUERY_DELETE = "DELETE FROM [dbo].[Event] WHERE ID='%s'";
	private static final String QUERY_GETALL = "SELECT * FROM [dbo].[Event] WHERE FIREWORKID='%s'";

	@Override
	public List<Event> getAllEventsToFirework(String fireworkId) throws SQLException {
		String query = String.format(QUERY_GETALL, fireworkId);
		log.debug("All Events loaded: " + query);
		ResultSet rs = GeneralDatabaseStuff.doDatabaseShit(query);
		return getEventsFromResultSet(rs);
	}
	
	private List<Event> getEventsFromResultSet(ResultSet rs) throws SQLException {
		List<Event> events = new ArrayList<Event>();
		while (rs.next()) {
			String id = rs.getString("ID");
			String fireworkId = rs.getString("FIREWORKID");
			String name = rs.getString("NAME");
			float time = rs.getFloat("ZEIT");
			String song = rs.getString("LIED");
			int gpioPin = rs.getInt("GPIO_PIN");
			List<String> positions = processRawStrings(rs.getString("POSITION"));
			events.add(new Event(id, fireworkId, name, time, song, gpioPin, positions));
		}
		return events;
	}
	
	private List<String> processRawStrings(String raw) {
		List<String> processedStrings = new ArrayList<String>();
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
	public void deleteEvent(Event event) throws SQLException {
		deleteEventById(event.getId());
		
	}

	@Override
	public void deleteEventById(String id) throws SQLException {
		String query = String.format(QUERY_DELETE, id);
		log.warn("Event " + id + " was deleted: " + query);
		GeneralDatabaseStuff.doDatabaseShitWithoutReturn(query);
	}

	@Override
	public void deleteAllEventsOnFirework(String fireworkId, boolean prove) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createNewEventInDatabase(Event event) throws SQLException {
		String query = String.format(QUERY_INSERT,
				event.getFireworkId(),
				event.getEventName(),
				event.getTime(),
				event.getSong(),
				event.getGpioPin(),
				event.getPositions());
		log.warn("New Event created: " + query);
		GeneralDatabaseStuff.doDatabaseShitWithoutReturn(query);
	}

	@Override
	public List<Event> getEventsByQuery(String query) throws SQLException {
		log.warn("Query executed: " + query);
		ResultSet rs = GeneralDatabaseStuff.doDatabaseShit(query);
		return getEventsFromResultSet(rs);
	}
}
