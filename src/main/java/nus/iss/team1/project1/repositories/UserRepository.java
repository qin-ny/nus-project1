package nus.iss.team1.project1.repositories;

import org.springframework.data.repository.CrudRepository;

import nus.iss.team1.project1.models.User;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
