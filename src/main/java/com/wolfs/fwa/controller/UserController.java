package com.wolfs.fwa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.microsoft.sqlserver.jdbc.StringUtils;
import com.wolfs.fwa.form.UserLogin;
import com.wolfs.fwa.model.User;

@Controller
@RequestMapping("/user")
public class UserController {

	private static List<User> users = new ArrayList<>();

	@Value("${welcome.message}")
	private String message;

	@Value("${error.message}")
	private String errorMessage;

	@GetMapping(value = { "/", "/index" })
	public String index(Model model) {
		model.addAttribute("message", message);
		return "index";
	}

	@GetMapping("/userList")
	public String userList(Model model) {
		model.addAttribute("users", users);
		return "userList";
	}

	@GetMapping("/addUser")
	public String showAddUserPage(Model model) {
		User userForm = new User();
		model.addAttribute("userForm", userForm);
		return "addUser";
	}

	@PostMapping("/addUser")
	public String saveUser(Model model, //
			@ModelAttribute("userForm") UserLogin userLogin) {
		String userName = userLogin.getUserName();
		String userPassword = userLogin.getUserPassword();

		if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(userPassword)) {
			User newUser = new User(userName, userPassword);
			users.add(newUser);
			return "redirect:/userList";
		}

		model.addAttribute("errorMessage", errorMessage);
		return "addUser";
	}
}