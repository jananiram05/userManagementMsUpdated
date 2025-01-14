package com.tekarch.userManagementMs.repositories;

import com.tekarch.userManagementMs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
