package br.com.fatto.view;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude
public class TaskDto {

	Long id;
	String nome;
	Double custo;
	LocalDate dtLimite;
	Long ordem_apresentacao;
	
	String message;
	Boolean success;
}
