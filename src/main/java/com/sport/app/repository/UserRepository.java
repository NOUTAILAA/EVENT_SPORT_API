package com.sport.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sport.app.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
