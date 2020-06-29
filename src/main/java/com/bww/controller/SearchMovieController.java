package com.bww.controller;


import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchMovieController {
	
	static List<String> rawData = null;
	
	@GetMapping(params = "t")
	@ResponseBody
	private List<String> searchByTitle(@RequestParam("t") String title) throws Exception {
		if (rawData == null) {
			File resource = new ClassPathResource("data/movies.csv").getFile();
			rawData = Files.readAllLines(resource.toPath());
		}
		
		List<String> result = rawData.stream().filter(it -> it.split(",")[1].contains(title)).collect(Collectors.toList());
		System.out.println(result);
		return result;
	}
	
	@GetMapping(params = "g")
	@ResponseBody
	private List<String> searchByGenre(@RequestParam("g") List<String> genre) throws Exception {
		if (rawData == null) {	
			File resource = new ClassPathResource("data/movies.csv").getFile();
			rawData = Files.readAllLines(resource.toPath());
		}
		
		List<String> result = rawData.stream().filter(it -> Arrays.asList(it.split(",")[2].split("\\|")).containsAll(genre)).collect(Collectors.toList());
		System.out.println(result);
		return result;
	}
}
