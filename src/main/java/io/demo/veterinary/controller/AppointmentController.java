package io.demo.veterinary.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.demo.veterinary.entity.Appointment;
import io.demo.veterinary.entity.Pet;
import io.demo.veterinary.entity.Veterinarian;
import io.demo.veterinary.service.AppointmentService;
import io.demo.veterinary.service.PetService;
import io.demo.veterinary.service.VeterinarianService;

@RestController
@RequestMapping("appointment")
@CrossOrigin({ "*" })
public class AppointmentController extends BaseController<Appointment, AppointmentService> {

	private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

	@Autowired
	private VeterinarianService vetService;

	@Autowired
	private PetService petService;

	@Autowired
	private AppointmentService appointmentService;

	@Override
	public ResponseEntity<?> create(@RequestBody Appointment appointment) throws Exception {

		if (appointment.getPet() != null)
			return ResponseEntity.ok("no se debe asignar mascotá");
		if (appointment.getAmount() != null)
			return ResponseEntity.ok("no se debe asignar monto");

		Veterinarian vet = this.vetService.findById(appointment.getVeterinarian().getId());

		if (vet == null)
			return ResponseEntity.ok("el veterinario no se encuentra en la base de datos");

		List<Appointment> appointmentsVet = vet.getAppointments();

		System.out.println("CITA PEDIDA");
		System.out.println(appointment.getDateAndTime());

		for (Appointment appoint : appointmentsVet) {
			System.out.println("citas time");
			System.out.println(appoint.getDateAndTime());
			if (appoint.getDateAndTime().equals(appointment.getDateAndTime())) {
				return ResponseEntity.ok("el veterinario se encuentra ocupado en esta fecha");
			}
		}

//		List <Appointment> allAppointments = this.appointmentService.findAll();
//		
//		for(Appointment appoint: allAppointments) {
//			System.out.println("citas time");
//			System.out.println(appoint.getDateAndTime());
//			if(appoint.getDateAndTime().equals(appointment.getDateAndTime())) {
//				return ResponseEntity.ok("El consultorio se encuentra ocupado");
//			}
//		}

		return super.create(appointment);
	}

	@Override
	public ResponseEntity<?> update(Appointment entity) {
		this.appointmentService.findById(null);
		return super.update(entity);
	}

	@PutMapping("/cancel/{idPet}/{idAppointment}")
	public ResponseEntity<?> cancelAppointment(@PathVariable Long idPet, @PathVariable Long idAppointment) {

//		Appointment appointment = this.appointmentService.updatebyId(idAppointment);

		Appointment appointment = this.appointmentService.findById(idAppointment);

		appointment.getPet();

		Appointment appointmentAux = new Appointment(appointment.getId(), appointment.getDateAndTime(),
				appointment.getOffice(), appointment.getAmount(), appointment.getProcedure(),
				appointment.getDescription(), appointment.getPrescription(), appointment.getVeterinarian(), null);

		return ResponseEntity.ok(this.appointmentService.save(appointmentAux));
	}

	@PutMapping("request")
	public ResponseEntity<?> requestAppointment(@RequestParam Long idPet, @RequestParam Long idAppointment) {
		Map<String, String> response = new HashMap<>();
		Pet pet = null;
		Appointment appointment = null;
		pet = this.petService.findById(idPet);
		if (pet == null) {
			response.put("Message", "la mascota no se encuentra en la base de datos");
			return ResponseEntity.ok(response);
		}
		appointment = this.appointmentService.findById(idAppointment);
		if (!pet.getAppointments().isEmpty()) {
			response.put("Message", "Ya cuenta con citas reservadas");
			return ResponseEntity.ok(response);
		}
		if (appointment == null) {
			response.put("Message", "La cita no tiene agenda");
			return ResponseEntity.ok(response);
		}
		if (appointment.getPet() != null) {
			response.put("Message", "La cita se encuentra agendada");
			return ResponseEntity.ok(response);
		}
		appointment.setPet(pet);

		return ResponseEntity.ok(this.appointmentService.save(appointment));
	}

	@DeleteMapping("delete-appointment")
	public ResponseEntity<?> deleteById(@RequestParam Long id) {
		System.out.println(id);
		return ResponseEntity.ok(this.appointmentService.deleteById(id));
	}

	@Override
	public ResponseEntity<List<Appointment>> list(Long idUser) {
		// TODO Auto-generated method stub
		return super.list(idUser);
	}

}
