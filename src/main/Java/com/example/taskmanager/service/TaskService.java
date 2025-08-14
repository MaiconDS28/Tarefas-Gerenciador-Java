package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public List<Task> getAll() {
        return new ArrayList<>(tasks); // devolve cópia para segurança
    }

    public Task create(Task task) {
        long id = idCounter.getAndIncrement();
        task.setId(id);
        tasks.add(task);
        return task;
    }

    public Optional<Task> findById(Long id) {
        return tasks.stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    public Optional<Task> update(Long id, Task updated) {
        return findById(id).map(existing -> {
            existing.setTitle(updated.getTitle());
            existing.setDescription(updated.getDescription());
            existing.setCompleted(updated.isCompleted());
            return existing;
        });
    }

    public boolean delete(Long id) {
        return tasks.removeIf(t -> t.getId().equals(id));
    }
}
