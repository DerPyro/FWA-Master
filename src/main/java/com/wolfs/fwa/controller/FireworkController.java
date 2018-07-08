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

import com.wolfs.fwa.model.Firework;
import com.wolfs.fwa.sql.FireworkDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/firework")
public class FireworkController {

	private static final String REDIRECT_FIREWORK_OVERVIEW = "redirect:/firework";

	@Resource
	private FireworkDao fireworkDao;

	@GetMapping
	public String overview(Model model) {
		model.addAttribute("fireworks", prepareFireworks());
		model.addAttribute("newFirework", new Firework());
		return "firework/fireworkOverview";
	}
	
	//Test
	@GetMapping("/test")
	public String test() {
		return "firework/test";
	}

	private List<Firework> prepareFireworks() {
		List<Firework> result = new ArrayList<>();
		for (Firework firework : getFireworks()) {
			try {
				fireworkDao.countPins(firework);
				fireworkDao.saveFirework(firework);
			} catch (SQLException e) {
				log.debug("While updating pins of Firework: " + firework.getId() + " an error occured: " + e);
			}
			result.add(firework);
		}
		return result;
	}

	@PostMapping
	public String createFirework(@ModelAttribute("newFirework") Firework newFirework, BindingResult bindingResult,
			Model model) {
		try {
			newFirework.setCompleted(false);
			fireworkDao.createNewFireworkInDatabase(newFirework);
			model.addAttribute("firework", newFirework);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return REDIRECT_FIREWORK_OVERVIEW;
	}

	public List<Firework> getFireworks() {
		try {
			return fireworkDao.getFireworks();
		} catch (SQLException e) {
			log.error("While filling Model with all Fireworks an error occured" + e);
			return new ArrayList<>();
		}
	}

	@GetMapping("/delete/{id}")
	public String deleteFirework(@PathVariable String id) {
		try {
			fireworkDao.deleteFireworkById(id);
		} catch (SQLException e) {
			log.error("While deleting Firework with id " + id + " an error occured" + e);
		}
		return REDIRECT_FIREWORK_OVERVIEW;

	}

}
