package com.wolfs.fwa;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
public class Start {

	private static final String FIRST_PAGE = "html/login.html";

	@RequestMapping("/")
    public String redirectToLogin() {
        return "login";
    }
	
	@GetMapping("/home?pwd={pwd}")
	public String home3(@PathVariable Map<String, String>pathVariables) {
		System.out.println("g");
		return FIRST_PAGE;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Start.class, args);
	}
	
	
}