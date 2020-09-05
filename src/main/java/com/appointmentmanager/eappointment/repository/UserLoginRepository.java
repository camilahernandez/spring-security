package com.appointmentmanager.eappointment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.appointmentmanager.eappointment.entity.UserLogin;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin,Long>{

	Optional<UserLogin> findByUserName(String userName);
}
