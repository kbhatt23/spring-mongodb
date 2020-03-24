package com.learning.legostore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.legostore.document.AverageRatingModel;
import com.learning.legostore.repository.LegoReportingService;

@RestController
@RequestMapping("/reporting")
public class ReportingController {

	@Autowired
	private LegoReportingService legoReportingService;
	
	@GetMapping("/averageRating")
	public List<AverageRatingModel> findAllByAverageRating(){
		return legoReportingService.generateAverageRatingReport();
	}
}
