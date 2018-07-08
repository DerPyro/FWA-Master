package com.wolfs.fwa.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wolfs.fwa.model.Event;
import com.wolfs.fwa.model.Firework;
import com.wolfs.fwa.model.Position;
import com.wolfs.fwa.sql.EventDao;
import com.wolfs.fwa.sql.FireworkDao;
import com.wolfs.fwa.sql.PositionDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/firework/{id}")
public class FireworkDetailsController {

	@Resource
	private FireworkDao fireworkDao;
	@Resource
	private EventDao eventDao;
	@Resource
	private PositionDao positionDao;

	private static final String REDIRECT_FIREWORK_EDIT = "redirect:/firework/%s";

	@GetMapping
	public String overview(@PathVariable String id, Model model) {
		refreshModelFromFirework(model, id);
		return "firework/fireworkEdit";
	}

	private void refreshModelFromFirework(Model model, String id) {
		model.addAttribute("firework", getFirework(id));
		model.addAttribute("events", getEvents(id));
		model.addAttribute("newEvent", new Event());
		model.addAttribute("positions", getPositions());
	}

	private List<Position> getPositions() {
		List<Position> result = new ArrayList<>();
		try {
			result = positionDao.getAllPositions();
		} catch (SQLException e) {
			log.error("While getting all Positions an error occured: " + e);
		}
		return result;
	}

	private Firework getFirework(String id) {
		try {
			return fireworkDao.getFireworkById(id);
		} catch (SQLException e) {
			log.error("While getting requested Firework (" + id + ") an error occured" + e);
			return new Firework();
		}
	}

	private List<Event> getEvents(String id) {
		try {
			return eventDao.getAllEventsToFirework(id);
		} catch (SQLException e) {
			log.error("While getting requested Events from Firework (" + id + ") an error occured" + e);
			return new ArrayList<>();
		}
	}

	@PostMapping
	public String createEvent(@PathVariable String id, @ModelAttribute("newEvent") Event newEvent,
			BindingResult bindingResult, Model model) {
		refreshModelFromFirework(model, id);
		try {
			newEvent.setFireworkId(id);
			eventDao.createNewEventInDatabase(newEvent);
		} catch (SQLException e) {
			log.debug("While deleting Event with id " + id + " an error occured" + e);
		}
		return String.format(REDIRECT_FIREWORK_EDIT, id);
	}

	// @ModelAttribute
	// public Event getEventFromPathVariables(@PathVariable Map<String, String>
	// pathVariables) {
	// String x = pathVariables.get("newEvent");
	// return new Event();
	// }
	
	@GetMapping("/save")
	public String saveFirework(@PathVariable String id) {
		try {
			fireworkDao.saveFirework(fireworkDao.getFireworkById(id));
		} catch (SQLException e) {
			log.error("While saving firework with id " + id + "an error occured: " + e);
		}
		return String.format(REDIRECT_FIREWORK_EDIT, id);
	}

	@GetMapping("/delete/{id}")
	public String deleteEvent(@PathVariable String id) {
		try {
			eventDao.deleteEventById(id);
		} catch (SQLException e) {
			log.debug("While deleting Event with id " + id + " an error occured" + e);
		}

		return "firework/fireworkEdit";
	}

}
