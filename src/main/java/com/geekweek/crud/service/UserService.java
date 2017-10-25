package com.geekweek.crud.service;

import com.geekweek.crud.dto.User;
import com.geekweek.crud.model.UserEntity;
import com.geekweek.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Magdalena.Rynasiewic on 19.10.2017.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        Iterable<UserEntity> userEntities = userRepository.findAll();
        List<User> users = new ArrayList<User>();
        userEntities.forEach(user -> {
            users.add(mapping(user));
        });

        return  users;
    }

    public User findById(long id) {
        UserEntity userEntity = userRepository.findOne(id);
        return mapping(userRepository.findOne(id));
    }

    public void delete(long id) {
        userRepository.delete(id);
    }

    public boolean isUserExist(User user) {
        UserEntity userEntity = userRepository.findByFirstNameAndLastName(user.getFirstName(), user.getLastName());
        return userEntity != null;
    }

    public void save(User user) {
        userRepository.save(mapping(user));
    }

    private User mapping(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setAge(userEntity.getAge());

        return user;
    }

    private UserEntity mapping(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setAge(user.getAge());

        return userEntity;
    }



}
