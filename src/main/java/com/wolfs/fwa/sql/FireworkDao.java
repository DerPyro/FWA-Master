package com.wolfs.fwa.sql;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import com.wolfs.fwa.model.Firework;

public interface FireworkDao {

	Firework getFireworkById(String id) throws SQLException;

	List<Firework> getFireworks() throws SQLException;

	void createNewFireworkInDatabase(Firework firework) throws SQLException;

	void deleteFireworkById(String id) throws SQLException;

	List<File> parseFireworkToScripts(Firework firework) throws SQLException;

	void countPins(Firework firework) throws SQLException;

	void saveFirework(Firework firework) throws SQLException;

}
