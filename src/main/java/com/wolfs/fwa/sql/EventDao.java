package com.wolfs.fwa.sql;

import java.sql.SQLException;
import java.util.List;

import com.wolfs.fwa.model.Event;

public interface EventDao {
	
	List<Event> getEventsByQuery(String queryParam) throws SQLException;

	List<Event> getAllEventsToFirework(String fireworkId) throws SQLException;

	void createNewEventInDatabase(Event event) throws SQLException;

	void deleteEvent(Event event) throws SQLException;

	void deleteEventById(String id) throws SQLException;

	void deleteAllEventsOnFirework(String fireworkId, boolean prove);
	

}
