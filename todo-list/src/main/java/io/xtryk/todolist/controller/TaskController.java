package io.xtryk.todolist.controller;


import io.xtryk.todolist.model.Task;
import io.xtryk.todolist.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;


import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    @Autowired
    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }
    @GetMapping("/tasks/{id}")
    ResponseEntity<?> getTask(@PathVariable int id){
        return  (repository.findById(id))
                .map(task -> ResponseEntity.ok(task))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/tasks/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate){
        if(repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        toUpdate.setId(id); //just to be sure
        repository.save(toUpdate);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("tasks")
    ResponseEntity<Task> createTask(@RequestBody @Valid Task toCreate){
        Task result = repository.save(toCreate);
        return ResponseEntity.created(URI.create("/"+result.getId())).body(result);
    }

    // @DeleteMapping to do


}
