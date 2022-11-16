package io.demo.veterinary.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.demo.veterinary.entity.Veterinarian;
import io.demo.veterinary.service.VeterinarianService;

@RestController
@RequestMapping("veterinarian")
public class VeterinarianController extends BaseController<Veterinarian,VeterinarianService>{
	
}
