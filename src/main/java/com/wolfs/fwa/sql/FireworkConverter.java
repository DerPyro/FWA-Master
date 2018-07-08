package com.wolfs.fwa.sql;

import java.util.List;

import com.wolfs.fwa.model.Firework;

public interface FireworkConverter {
	
	String convertFireworkToShellCommand(Firework firework);
	
	List <String> convertFireworkPositionsToShellCommand(Firework firework);

}
