package main;

import main.model.Task;
import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/tasks")
    public ResponseEntity add(String title, String description)
    {
        Task task = new Task();
        task.setDescription(description);
        task.setTitle(title);
        task.setDone(false);
        taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity get(@PathVariable int id)
    {
        Optional<Task> task = taskRepository.findById(id);
        if(!task.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(task.get(), HttpStatus.OK);
    }

    @GetMapping("/tasks")
    public ResponseEntity getTasks()
    {
        Iterable<Task> task = taskRepository.findAll();
        ArrayList<Task> tasks = new ArrayList<>();
        task.forEach(tasks::add);
        return new ResponseEntity(tasks, HttpStatus.OK);
    }

    @PatchMapping("/tasks/{id}")
    public ResponseEntity patch(@PathVariable int id, @ModelAttribute Task task)
    {
        Optional<Task> tasks = taskRepository.findById(id);
        if(!tasks.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Task task1 = tasks.get();
        if(task.getTitle() != null) {
            task1.setTitle(task.getTitle());
        }
        if(task.getDescription() != null) {
            task1.setDescription(task.getDescription());
        }
        if(task.isDone() != null) {
            task1.setDone(task.isDone());
        }
        taskRepository.save(task1);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity delete(@PathVariable int id)
    {
        Optional<Task> task = taskRepository.findById(id);
        if(!task.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        taskRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
