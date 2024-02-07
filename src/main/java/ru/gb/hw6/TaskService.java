package ru.gb.hw6;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public Task save(Task task) {
        task.setDate(new java.util.Date());
        return taskRepository.save(task);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    public Task update(Long id, Task updatedTask) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            return null;
        }
        task.setTitle(updatedTask.getTitle());
        task.setState(updatedTask.getState());
        if (updatedTask.getId() != null) {
            task.setId(updatedTask.getId());
        }
        return taskRepository.save(task);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task addUserToTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return null;
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        task.addUser(user);
        return taskRepository.save(task);
    }

    public Task removeUserFromTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return null;
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        task.removeUser(user);
        return taskRepository.save(task);
    }

    public Task updateState(Long id, String state) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            return null;
        }
        task.setState(state);
        return taskRepository.save(task);
    }
}
