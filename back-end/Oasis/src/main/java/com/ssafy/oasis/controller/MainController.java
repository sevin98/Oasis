package com.ssafy.oasis.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.oasis.model.service.MainService;

@RestController
@RequestMapping("/")
public class MainController {
	
	private MainService mainService;

	public MainController(MainService mainService) {
		this.mainService = mainService;
	}

	@GetMapping
	public ResponseEntity<?> main() {
		Map<String, Object> map = new HashMap<>();
		map.put("mapData", mainService.getMapDatas());
		map.put("top3Data", mainService.getTop3PopularDatas());
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
}
