package com.example.cloudgateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cloudgateway.entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findByUserName(String username);
}