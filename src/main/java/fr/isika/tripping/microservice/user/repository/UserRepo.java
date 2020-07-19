package fr.isika.tripping.microservice.user.repository;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import fr.isika.tripping.microservice.user.model.UserEntity;

public interface UserRepo extends CrudRepository<UserEntity, Integer>{

	Optional<UserEntity> findByUsername(String username);
	


}
