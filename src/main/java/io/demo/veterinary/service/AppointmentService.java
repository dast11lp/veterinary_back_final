package io.demo.veterinary.service;

import org.springframework.stereotype.Service;

import io.demo.veterinary.entity.Appointment;
import io.demo.veterinary.repository.AppointmentRepository;

@Service
public class AppointmentService extends BaseService<Appointment, AppointmentRepository>{
	
}
