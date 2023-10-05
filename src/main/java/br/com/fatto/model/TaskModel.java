package br.com.fatto.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.fatto.view.TaskDto;
import jakarta.persistence.CascadeType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tarefas")
public class TaskModel {

	@JsonIgnore

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@Column(name = "nome", nullable = false)
    private String nome;
	
	@Column(name = "custo")
    private Double custo;
	
	@Column(name = "dtLimite")
	private LocalDate dtLimite;
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ordem_apresentacao")
    private Long ordem_apresentacao;
	
}
