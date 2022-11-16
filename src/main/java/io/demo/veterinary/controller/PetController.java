package io.demo.veterinary.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.demo.veterinary.entity.Pet;
import io.demo.veterinary.service.PetService;

@RestController
@RequestMapping("pet")
@CrossOrigin("*")
public class PetController extends BaseController<Pet, PetService> {

	@PutMapping("/cancel")
	public void cancelAppointment(@RequestParam Long idPet, @RequestParam Long idAppointment, HttpServletResponse response) throws IOException {
		response.sendRedirect("/appointment/cancel/"+idPet+"/"+ idAppointment);
	}
	
}
