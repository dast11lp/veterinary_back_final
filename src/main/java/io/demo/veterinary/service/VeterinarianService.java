package io.demo.veterinary.service;

import org.springframework.stereotype.Service;

import io.demo.veterinary.entity.Veterinarian;
import io.demo.veterinary.repository.VeterinarianRepository;

@Service
public class VeterinarianService extends BaseService<Veterinarian, VeterinarianRepository>{

}
