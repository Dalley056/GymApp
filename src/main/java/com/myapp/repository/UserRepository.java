package com.myapp.repository;

import com.myapp.enums.UserOrders;
import com.myapp.models.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
	
//	@Query("SELECT new User(userId, userEmail, userForename, userSurname, userMiddleName) "
//			+ "FROM User "
//			+ "ORDER BY userEmail DESC")
//    public List<User> findAllByEmail();
	
//	@Query("SELECT new User(userId, userEmail, userForename, userSurname, userMiddleName userCreated) "
//			+ "FROM User "
//			+ "ORDER BY :orderBy DESC")
//    public List<User> findAllBy(@Param("orderBy") String orderBy);
//	
//	@Query("SELECT new User(userId, userEmail, userForename, userSurname, userMiddleName, userCreated) "
//			+ "FROM User")
//	public List<User> findAllByPage(Pageable pageable);
	
//	Page<User> findByUserEmailContainingOrUserForenameContainingOrUserSurnameContainingOrUserMiddleNameContaining(
//			String userEmail, String userForename, String userSurname, String userMiddleName,
//			Pageable pageable);
	
	Page<User> findByUsernameContainingOrUserForenameContainingOrUserSurnameContainingOrUserMiddleNameContaining(
			String username, String userForename, String userSurname, String userMiddleName,
			Pageable pageable);
		
	List<User> findByUserIdIn(List<Long> userIds);
	
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

}
