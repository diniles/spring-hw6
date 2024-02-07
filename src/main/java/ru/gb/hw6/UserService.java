package ru.gb.hw6;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User update(Long id, User updatedUser) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        user.setName(updatedUser.getName());
        if (updatedUser.getId() != null) {
            user.setId(updatedUser.getId());
        }
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User addTaskToUser(Long userId, Long taskId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return null;
        }
        user.addTask(task);
        return userRepository.save(user);
    }

    public User removeTaskFromUser(Long userId, Long taskId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return null;
        }
        user.removeTask(task);
        return userRepository.save(user);
    }
}
