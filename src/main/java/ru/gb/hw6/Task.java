package ru.gb.hw6;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Column(nullable = false, name = "task_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String state;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Column(nullable = false)
    private Date date;

    @JsonIgnoreProperties("tasks")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @ManyToMany(mappedBy = "tasks")
    private Set<User> users = new HashSet<>();

    public void addUser(User user) {
        users.add(user);
        user.getTasks().add(this);
    }

    public void removeUser(User user) {
        users.remove(user);
        user.getTasks().remove(this);
    }
}
