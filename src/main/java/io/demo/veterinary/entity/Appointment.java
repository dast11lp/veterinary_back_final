package io.demo.veterinary.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;


@Entity(name = "cita")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cit")
	private Long id;

	@Column(name = "fecha_cit")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dateAndTime;

	@Column(name = "consultorio_cit")
	private int office;

	@Column(name = "monto_cit")
	private Double amount;

	@Column(name = "procedimiento_cit")
	private String procedure;

	@Column(name = "descripcion_cit")
	private String description;

	@Column(name = "receta_cit")
	private String prescription;

	@JsonIgnoreProperties({"appointments", "handler", "hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_vet")
	private Veterinarian veterinarian;
	
	@JsonIgnoreProperties({"appointments", "handler", "hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_mas")
	private Pet pet;
	
	
	

	public Appointment() {}

	public Appointment(Long id, Date dateAndTime, int office, Double amount, String procedure, String description,
			String prescription, Veterinarian veterinarian, Pet pet) {
		this.id = id;
		this.dateAndTime = dateAndTime;
		this.office = office;
		this.amount = amount;
		this.procedure = procedure;
		this.description = description;
		this.prescription = prescription;
		this.veterinarian = veterinarian;
		this.pet = pet;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Date getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public int getOffice() {
		return office;
	}

	public void setOffice(int office) {
		this.office = office;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getProcedure() {
		return procedure;
	}

	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrescription() {
		return prescription;
	}

	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}

	public Veterinarian getVeterinarian() {
		return veterinarian;
	}

	public void setVeterinarian(Veterinarian veterinarian) {
		this.veterinarian = veterinarian;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

}
