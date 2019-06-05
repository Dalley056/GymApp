package com.myapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.myapp.models.Exercise;
import com.myapp.models.User;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
	
	//TODO: change these into manually written queries using annotation, as generated queries are not optimised
	
	Page<Exercise> findByUser(User user, Pageable pageable);
	
	Exercise findByExerciseId(Long userId);
	
	Page<Exercise> findByExerciseNameContaining(String searchString, Pageable pageable);
	
	Page<Exercise> findAllOrderByExerciseCreated(Pageable pageable); 
	
	//TODO: User history find

}
