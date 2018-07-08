package com.wolfs.fwa.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Composition {

	private String id;
	private String CompositionName;
	private int pins;

	private List<String> events;
	private String song;

	private Boolean completed;


}
