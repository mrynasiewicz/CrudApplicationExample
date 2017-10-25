package com.geekweek.crud.controller;

import com.geekweek.crud.dto.User;
import com.geekweek.crud.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Created by Magdalena.Rynasiewic on 19.10.2017.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    /**
     * get list of users
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        logger.info("Finding all User");

        List<User> users = userService.findAllUsers();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    /**
     * Get single user
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("userId") long id) {
        logger.info("Finding User with id {}", id);

        User user = getUserById(id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /**
     * Delete a user
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("userId") long userId) {
        logger.info("Deleting User with id {}", userId);
        getUserById(userId);
        userService.delete(userId);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    /**
     * Create user
     *
     * @param user
     * @param ucBuilder
     * @return
     */
    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", user);

        if (userService.isUserExist(user)) {
            logger.error("User with fist name {} and last name {} already exist", user.getFirstName(), user.getLastName());
            throw new RuntimeException("User with fist name " + user.getFirstName() + " and last name " + user.getLastName() + " already exist.");
        }
        userService.save(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    /**
     * Update user
     *
     * @param id
     * @param user
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        logger.info("Updating User with id {}", id);

        User currentUser = getUserById(id);

        user.setId(currentUser.getId());

        userService.save(user);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    private User getUserById(long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            logger.error("User with id {} not found.", userId);
            throw new RuntimeException("User with id " + userId + " not found.");
        }

        return user;
    }

}
