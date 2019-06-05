package com.myapp.dummy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.junit.validator.PublicClassValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.exceptions.AppException;
import com.myapp.models.Exercise;
import com.myapp.models.Role;
import com.myapp.models.RoleName;
import com.myapp.models.User;
import com.myapp.repository.ExerciseRepository;
import com.myapp.repository.RoleRepository;
import com.myapp.repository.UserRepository;

@RestController
@RequestMapping("/dummy")
public class DummyController {
	
	@Autowired
    UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@Autowired
	ExerciseRepository exerciseRepository;
	
	@PostMapping("/add/roles")
	public List<Role> createRoles() {
		List<Role> rolesAdded = new ArrayList<Role>();
		Role userRole = new Role();
		Role adminRole = new Role();
		userRole.setName(RoleName.ROLE_USER);
		adminRole.setName(RoleName.ROLE_ADMIN);
		
		rolesAdded.add(roleRepository.save(userRole));
		rolesAdded.add(roleRepository.save(adminRole));
		
		return rolesAdded;
	}
	
	@PostMapping("/add/users")
	public List<User> createDummyUsers() {
		List<User> usersAdded = new ArrayList<User>();
		
		for (int i = 0; i < 5; i++) {
			User temp = new User("user" + i + "@hotmail.com", "forename" + i, "surname" + i, "middlename" + i, "pass" + i);
			temp.setUserPassword(passwordEncoder.encode(temp.getUserPassword()));

	        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
	                .orElseThrow(() -> new AppException("User Role not set."));

	        temp.setRoles(Collections.singleton(userRole));

			usersAdded.add(userRepository.save(temp));
		}
		
	    return usersAdded;
	}
	
	@PostMapping("/add/exercises")
	public List<Exercise> createDummyExercises(){
		List<Exercise> exercisesAdded = new ArrayList<>();
		List<Exercise> toAdd = new ArrayList<>(); 
		
		User temp = userRepository.getOne((long) 1.0);
		toAdd.add(new Exercise("Bench Press", temp));
		toAdd.add(new Exercise("Squat", temp));
		toAdd.add(new Exercise("Deadlift", temp));
		toAdd.add(new Exercise("Overhead Press", temp));
		toAdd.add(new Exercise("Lat Pulldown", temp));
//		Exercise benchPress = new Exercise("Bench Press", temp);
//		Exercise squat = new Exercise("Squat", temp);
//		Exercise deadlift = new Exercise("Deadlift", temp);
//		Exercise overheadPress = new Exercise("Overhead Press", temp);
//		Exercise latPulldown = new Exercise("Lat Pulldown", temp);
		
		for (Exercise e : toAdd) {
			exercisesAdded.add(exerciseRepository.save(e));
		}
		
		return exercisesAdded;
		
	}

}
