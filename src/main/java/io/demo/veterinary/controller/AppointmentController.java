package io.demo.veterinary.controller;

import java.util.*;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import io.demo.veterinary.entity.AppUser;
import io.demo.veterinary.models.HourAvailable;
import io.demo.veterinary.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

	//private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);
	@Autowired
	private VeterinarianService vetService;

	@Autowired
	private PetService petService;

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private MyUserService myUserService;

	@PreAuthorize("@securityService.hasUser(#idUser)")
	@GetMapping("/dates")
	public ResponseEntity<?> listAppointDates(@RequestParam Long idUser) {
		AppUser user = this.myUserService.findById(idUser);
		if (user == null)
			return ResponseEntity.badRequest().build();

		List<Appointment> appointments = this.appointmentService.findAll();

		List<Appointment> appointmentsAvailable = new ArrayList<>();

		for (Appointment appointment: appointments) {
			if(appointment.getPet() == null) {
				appointmentsAvailable.add(appointment);
			}
		}

		if(appointmentsAvailable.isEmpty()){
			HashMap<String, String> response = new HashMap<>();
			response.put("message", "No hay citas disponibles");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(appointmentsAvailable);
	}

	@PreAuthorize("@securityService.hasUser(#idUser)")
	@GetMapping("/hours")
	public ResponseEntity<?> listAppointHours(@RequestParam Long idUser) {
		AppUser user = this.myUserService.findById(idUser);
		if (user == null)
			return ResponseEntity.badRequest().build();

		List<Appointment> appointments = this.appointmentService.findAll();

		ArrayList<HourAvailable> availablesHour = new ArrayList<>();

		for (Appointment appointment: appointments) {
			if(appointment.getPet() == null) {
				availablesHour.add(new HourAvailable(
						appointment.getId(),
						appointment.getVeterinarian().getFirstname(),
						appointment.getHour())
				);
			}
		}

		return ResponseEntity.ok(availablesHour);
	}

	@Override
	public ResponseEntity<?> create(@RequestBody Appointment appointment) throws Exception {

		// este if se deberia eliminar, deberia hacerse un dto en vez de el appointment directamente
		// omitiendo el campo pet
		if (appointment.getPet() != null)
			return ResponseEntity.ok("");

		if (appointment.getAmount() != null)
			return ResponseEntity.ok("");

		Veterinarian vet = this.vetService.findById(appointment.getVeterinarian().getId());

		if (vet == null)
			return ResponseEntity.notFound().build();

		List<Appointment> appointmentsVet = vet.getAppointments();

		for (Appointment appoint : appointmentsVet) {
			if (appoint.getDate().equals(appointment.getDate())) {
				return ResponseEntity.ok("");
			}
		}
		return super.create(appointment);
	}

	@Override
	public ResponseEntity<?> update(Appointment entity) {
		this.appointmentService.findById(null);
		return super.update(entity);
	}

	@PutMapping("/cancel/{idPet}/{idAppointment}")
	public ResponseEntity<?> cancelAppointment(@PathVariable Long idPet, @PathVariable Long idAppointment) {

		Appointment appointment = this.appointmentService.findById(idAppointment);

		if (appointment == null)
			return ResponseEntity.notFound().build();

		Appointment appointmentAux = new Appointment(appointment.getId(), appointment.getDate(),appointment.getHour(),
				appointment.getOffice(), appointment.getAmount(), appointment.getProcedure(),
				appointment.getDescription(), appointment.getPrescription(), appointment.getVeterinarian(), null);

		return ResponseEntity.ok(this.appointmentService.save(appointmentAux));
	}

	@PutMapping("request")
	public ResponseEntity<?> requestAppointment(@RequestParam Long idPet, @RequestParam Long idAppointment) {
		Map<String, String> response = new HashMap<>();
		Pet pet;
		Appointment appointment;
		pet = this.petService.findById(idPet);
		if (pet == null) {
			response.put("Message", "la mascota no se encuentra en la base de datos");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		appointment = this.appointmentService.findById(idAppointment);
		if (!pet.getAppointments().isEmpty()) {
			response.put("Message", "Ya cuenta con citas reservadas");
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
		if (appointment == null) {
			response.put("Message", "La cita no tiene agenda");
			return ResponseEntity.notFound().build();
		}
		if (appointment.getPet() != null) {
			response.put("Message", "La cita se encuentra agendada");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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
		return super.list(idUser);
	}
}
