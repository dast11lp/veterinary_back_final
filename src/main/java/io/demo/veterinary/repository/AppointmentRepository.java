package io.demo.veterinary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.demo.veterinary.entity.Appointment;
import io.demo.veterinary.entity.Pet;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
	Appointment findByPet(Pet pet);
}
