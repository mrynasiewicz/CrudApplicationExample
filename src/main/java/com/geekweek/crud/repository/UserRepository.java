package com.geekweek.crud.repository;

import com.geekweek.crud.dto.User;
import com.geekweek.crud.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Magdalena.Rynasiewic on 19.10.2017.
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByFirstNameAndLastName(String firstName, String lastName);
}
