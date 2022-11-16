package io.demo.veterinary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.demo.veterinary.entity.Veterinarian;

public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long>{

}
