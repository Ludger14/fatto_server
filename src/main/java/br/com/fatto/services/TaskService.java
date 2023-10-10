package br.com.fatto.services;

import java.util.Objects;
import java.util.Optional;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.fatto.repository.TaskRepository;
import br.com.fatto.view.TaskDto;
import br.com.fatto.model.TaskModel;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	public TaskDto createTask(TaskModel taskModel) throws ParseException {
		TaskDto task = new TaskDto();
		if (taskModel.getNome() == null || taskModel.getCusto() == null || taskModel.getDtLimite() == null) {
			task.setSuccess(Boolean.FALSE);
            task.setMessage("Os campos Nome, Custo e Data Limite são obrigatórios.");
            return task;
        }
		
		if (taskRepository.checkNameTask(taskModel.getNome()) != null) {
			task.setSuccess(Boolean.FALSE);
			task.setMessage("Já existe uma tarefa com esse nome.");
			return task;
        }
		
		Long nextOrdem = this.nextOrdem();
		
		taskModel.setOrdem_apresentacao(nextOrdem);
		
		taskRepository.save(taskModel);
		task.setId(taskModel.getId());
		task.setNome(taskModel.getNome());
		task.setCusto(taskModel.getCusto());
		task.setDtLimite(taskModel.getDtLimite());
		task.setOrdem_apresentacao(taskModel.getOrdem_apresentacao());
		task.setMessage("A tarefa " + taskModel.getNome() + " foi registrada com sucesso.");
		task.setSuccess(Boolean.TRUE);
		
		return task;
	}
	
	private Long nextOrdem() {
		List<TaskModel> task = taskRepository.ordemApresentacaoDesc();
		
		if(!task.isEmpty()) {
			Long maiorNumber = task.get(0).getOrdem_apresentacao();
			return maiorNumber + 1;
		} else {
			return 1L;
		}
	}
	
	public List<TaskModel> getAllTask(){
		return taskRepository.ordemApresentacaoAsc();
	}
	
	public TaskDto editTask(String nome, TaskModel taskModel) throws ParseException {
		TaskDto task = new TaskDto();
        TaskModel newTask = taskRepository.checkNameTask(nome);
        if(Objects.nonNull(newTask)) {			
			if (taskModel.getNome() == null || taskModel.getCusto() == null || taskModel.getDtLimite() == null) {
				task.setSuccess(Boolean.FALSE);
	            task.setMessage("Os campos Nome, Custo e Data Limite são obrigatórios.");
	            return task;
	        }
			
			if (!nome.equals(taskModel.getNome())) {
				TaskModel newTaskWithSameName = taskRepository.checkNameTask(taskModel.getNome());
	            if (Objects.isNull(newTaskWithSameName)) {
	            	newTask.setNome(taskModel.getNome());
	    			newTask.setCusto(taskModel.getCusto());
	    			newTask.setDtLimite(taskModel.getDtLimite());
	    			
	    			taskRepository.save(newTask);
	    			
	    			task.setMessage("A tarefa " + taskModel.getNome() + " foi salva com sucesso.");
	    			task.setSuccess(Boolean.TRUE);
	            } else {
	            	task.setSuccess(Boolean.FALSE);
					task.setMessage("Já existe uma tarefa com esse nome.");
					return task;
	            }
			}else {
				newTask.setNome(taskModel.getNome());
				newTask.setCusto(taskModel.getCusto());
				newTask.setDtLimite(taskModel.getDtLimite());
				
				taskRepository.save(newTask);
				
				task.setMessage("A tarefa " + taskModel.getNome() + " foi salva com sucesso.");
				task.setSuccess(Boolean.TRUE);
			}
			
			task.setId(taskModel.getId());
			task.setNome(taskModel.getNome());
			task.setCusto(taskModel.getCusto());
			task.setDtLimite(taskModel.getDtLimite());
			task.setOrdem_apresentacao(taskModel.getOrdem_apresentacao());
			
			return task;
		}else {
			task.setMessage("A tarefa não foi editada.");
			task.setSuccess(Boolean.FALSE);
			return task;
		}
	}
	
	public TaskDto deleteTask(String nome) throws ParseException {
		TaskDto task = new TaskDto();		
		
		TaskModel taskModel = taskRepository.checkNameTask(nome);
		if(Objects.nonNull(taskModel)) {
			taskRepository.delete(taskModel);		
			task.setMessage("A tarefa foi deletada com sucesso.");
			task.setSuccess(Boolean.TRUE);
		}else {
			task.setMessage("Houve um erro, a tarefa não foi deletada.");
			task.setSuccess(Boolean.FALSE);
		}		
		
		return task;
	}
	
	public TaskDto salvarTaskOrder(List<TaskModel> taskList) throws ParseException {
		TaskDto task = new TaskDto();
		try {
	        for (TaskModel tarefa : taskList) {
	        	TaskModel taskModel = taskRepository.checkNameTask(tarefa.getNome());
	        	if(tarefa.getOrdem_apresentacao() != taskModel.getOrdem_apresentacao()) {
	        		taskModel.setOrdem_apresentacao(tarefa.getOrdem_apresentacao());
	        		taskRepository.save(taskModel);
	        	}	        	
	        }
	        task.setSuccess(Boolean.TRUE);
	        task.setMessage("A ordem das tarefas foi salva com sucesso.");
	        return task;
	    } catch (Exception e) {
	    	task.setSuccess(Boolean.FALSE);
	        task.setMessage("Houve um erro ao salvar a ordem das tarefas.");
	        return task;
	    }
	}
	
}
