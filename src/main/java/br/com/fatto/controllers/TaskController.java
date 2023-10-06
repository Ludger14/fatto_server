package br.com.fatto.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fatto.model.TaskModel;
import br.com.fatto.services.TaskService;
import br.com.fatto.view.TaskDto;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = { " https://angular-fatto-7c145733c166.herokuapp.com" }, maxAge = 3600)
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	@PostMapping("/createTask")
	@Transactional(rollbackFor = Exception.class)
	public TaskDto createTask(@RequestBody TaskModel taskModel, HttpServletRequest request) throws ParseException{
		TaskDto task = taskService.createTask(taskModel);
		return task;
	}
	
	@GetMapping("/getAllTask")
	public List<TaskModel> getAllTask(HttpServletRequest request) throws ParseException{
		List<TaskModel> task = taskService.getAllTask();
		return task;
	}
	
	@PutMapping("/editTask/{nome}")
	@Transactional(rollbackFor = Exception.class)
	public TaskDto editTask(@PathVariable String nome, @RequestBody TaskModel taskModel, HttpServletRequest request) throws ParseException{
		TaskDto task = taskService.editTask(nome, taskModel);
		return task;
	}
	
	@DeleteMapping("/deleteTask/{nome}")
	@Transactional(rollbackFor = Exception.class)
	public TaskDto deleteTask(@PathVariable String nome, HttpServletRequest request) throws ParseException{
		TaskDto task = taskService.deleteTask(nome);
		return task;
	}
	
	@PutMapping("/salvarTaskOrder")
	@Transactional(rollbackFor = Exception.class)
	public TaskDto salvarTaskOrder(@RequestBody List<TaskModel> taskList) throws ParseException{
	    TaskDto task = taskService.salvarTaskOrder(taskList);
	    return task;
	}
}
