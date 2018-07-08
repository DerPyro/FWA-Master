package com.wolfs.fwa.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

	private String id;
	private String fireworkId;
	private String eventName;
	private float time;
	private String song;
	private int gpioPin;
	private List<String> positions;
}
