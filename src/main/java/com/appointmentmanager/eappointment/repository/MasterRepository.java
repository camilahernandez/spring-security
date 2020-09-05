package com.appointmentmanager.eappointment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.appointmentmanager.eappointment.entity.Master;

@Repository
public interface MasterRepository extends JpaRepository<Master,Long>{

	Optional<Master> findByKeyAndValue(String key,String value);
}
