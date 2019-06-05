package com.myapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.exceptions.ResourceNotFoundException;
import com.myapp.models.Exercise;
import com.myapp.models.User;
import com.myapp.repository.ExerciseRepository;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {
	
	@Autowired
    ExerciseRepository exerciseRepository;
	
	// by exercise id
	@GetMapping("/{id}")
	public Exercise getExerciseById(@PathVariable(value = "id") Long exerciseId) {
	    return exerciseRepository.findById(exerciseId).orElseThrow(() -> new ResourceNotFoundException("Exercise", "exerciseId", exerciseId));
	}
	
	// by user id
	@GetMapping("/user/{id}")
	public @ResponseBody Page<Exercise> getAllExerciseForUser(@PathVariable(value = "id") Long userId, @RequestParam Integer firstItem, Integer lastItem){
		
		User temp = new User(userId);
		PageRequest pageReq;
		Direction dir = Direction.DESC;
		String orderBy = "userId";
		
		if (lastItem < 0) {
			pageReq = PageRequest.of(0, Integer.MAX_VALUE, dir, orderBy);
	    } else {
	    	pageReq = PageRequest.of(firstItem, lastItem, dir, orderBy);
	    }
		
		return exerciseRepository.findByUser(temp, pageReq);
		
	}
	
	//TODO add get from history. Need to implement exercise instance stuff before this can be added.
	
	@PostMapping("/new")
	public Exercise createUser(@Valid @RequestBody Exercise exercise) {
	    return exerciseRepository.save(exercise);
	}
	
	@GetMapping
	public @ResponseBody Page<Exercise> getAll(){
		
		Direction dir = Direction.ASC;
		String orderBy = "exerciseName";
		
		PageRequest pageReq = PageRequest.of(0, Integer.MAX_VALUE, dir, orderBy);
		
		return exerciseRepository.findAll(pageReq);
	}

}
