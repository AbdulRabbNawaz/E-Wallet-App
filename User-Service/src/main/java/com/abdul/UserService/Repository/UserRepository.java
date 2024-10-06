package com.abdul.UserService.Repository;

import com.abdul.UserService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByMobileNo(String mobile);

}
