package com.myapp.controllers;

import com.myapp.exceptions.ResourceNotFoundException;
import com.myapp.models.User;
import com.myapp.payload.UserIdentityAvailability;
import com.myapp.payload.UserSummary;
import com.myapp.repository.UserRepository;
import com.myapp.security.CurrentUser;
import com.myapp.security.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
    UserRepository userRepository;
	
	@GetMapping("/users")
	public @ResponseBody Page<User> getAllUsers(@RequestParam Integer firstItem, Integer lastItem,
			String orderBy, String dir, @RequestParam(required=true, defaultValue="") String searchFor) {
		
		// Should move all of this and just pass in the repository to use so this can be re-used
		// The findBy call can't be re-used
		Direction returnDir = Direction.ASC;
		
		if (dir.equals("DESC")) {
			returnDir = Direction.DESC;
		}
	    
		PageRequest pageReq;
		
		if (lastItem < 0) {
			pageReq = PageRequest.of(0, Integer.MAX_VALUE, returnDir, orderBy);
	    } else {
	    	pageReq = PageRequest.of(firstItem, lastItem, returnDir, orderBy);
	    }
		
		if (searchFor.isEmpty()) {
			return userRepository.findAll(pageReq);
		} else {
			return userRepository
					.findByUserEmailContainingOrUserForenameContainingOrUserSurnameContainingOrUserMiddleNameContaining(
					searchFor, searchFor, searchFor, searchFor, pageReq);
		}
	    
	}
	
	@PostMapping("/users")
	public User createUser(@Valid @RequestBody User user) {
	    return userRepository.save(user);
	}
	
	@GetMapping("/users/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }
	
	@GetMapping("/users/{id}")
	public User getUserById(@PathVariable(value = "id") Long userId) {
	    return userRepository.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
	}
	
	@PutMapping("/users/{id}")
	public User updateUser(@PathVariable(value = "id") Long userId,
	                                        @Valid @RequestBody User userDetails) {

	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

	    user.setUserEmail(userDetails.getUserEmail());
	    user.setUserForename(userDetails.getUserForename());
	    user.setUserSurname(userDetails.getUserSurname());
	    user.setUserMiddleName(userDetails.getUserMiddleName());

	    User updatedUser = userRepository.save(user);
	    return updatedUser;
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userId) {
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

	    userRepository.delete(user);

	    return ResponseEntity.ok().build();
	}
	
	@GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUserEmail(username);
        return new UserIdentityAvailability(isAvailable);
    }

}
