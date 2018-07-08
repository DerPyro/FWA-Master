package com.wolfs.fwa.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.wolfs.fwa.sql.EventDao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Firework {

	private String id;
	private String fireworkName;
	private int pins;

	private List<String> events;
	private List<String> songs;

	private Boolean completed;

}
