package com.wolfs.fwa.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wolfs.fwa.model.Position;
import com.wolfs.fwa.sql.PositionDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/positions")
public class PositionController {

	@Resource
	private PositionDao positionDao;

	@GetMapping
	public String overview(Model model) {
		model.addAttribute("positions", getPositions());
		model.addAttribute("newPosition", new Position());
		return "firework/positionEdit";
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

	@PostMapping
	public String saveNewPositionInDatabase(@ModelAttribute("newPosition") Position newPosition) {
		try {
			positionDao.createNewPosition(newPosition);
		} catch (SQLException e) {
			log.debug("While creating new Position " + newPosition.getName() + "an error occured: " + e);
			e.printStackTrace();
		}
		return "redirect:/positions";
	}

	@GetMapping("/{name}/delete")
	public String deletePosition(@PathVariable String name) {
		try {
			positionDao.deletePositionByName(name);
		} catch (SQLException e) {
			log.debug("While deleting Position " + name + "an error occured: " + e);
		}
		return "redirect:/positions";
	}

}
