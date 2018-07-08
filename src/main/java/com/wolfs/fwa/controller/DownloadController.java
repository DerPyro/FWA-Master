package com.wolfs.fwa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/downloads")
public class DownloadController {
	
	@RequestMapping
	public String displayDownload() {
		return "firework/downloadFirework";
	}

}
