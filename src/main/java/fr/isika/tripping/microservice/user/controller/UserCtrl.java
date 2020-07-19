package fr.isika.tripping.microservice.user.controller;

import java.net.URI;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import fr.isika.tripping.microservice.user.model.UserEntity;
import fr.isika.tripping.microservice.user.repository.UserRepo;

@CrossOrigin("*")
@Controller
@RestController
@RequestMapping (path = "/tripping/user")
public class UserCtrl {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping(path = "/findById/{id}")
	public @ResponseBody Optional<UserEntity> findById(@PathVariable Integer id){
		
		Optional<UserEntity> userFound = userRepo.findById(id);
		
		if(!userFound.isPresent()) throw new Error("user not found, sorry");
		
		return userFound;
		
	}
	
	@PostMapping(path = "/registerUser")
	public @ResponseBody ResponseEntity<Void> createUser(@RequestBody UserEntity user){
		
		log.info("In UserCtrl createUser :" + user.toString());
		
		UserEntity createdUser = userRepo.save(user);
		
		if (createdUser==null) 
			return ResponseEntity.noContent().build();
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(createdUser.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping(path = "/findAllUsers")
	public @ResponseBody Iterable<UserEntity> getAllUsers(){
		return userRepo.findAll();
	}
	
	
	@GetMapping(path = "/findUserByUsername/{username}")
	public @ResponseBody Optional<UserEntity> findUserByUsername(@PathVariable String username){
		Optional<UserEntity> optionalUser = userRepo.findByUsername(username); 
		if(!optionalUser.isPresent()) 
			log.info("Sorry, this user was not found");
		return optionalUser;
	}
}
