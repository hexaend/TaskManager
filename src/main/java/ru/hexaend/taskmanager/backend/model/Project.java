package ru.hexaend.taskmanager.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"tasks", "users", "managerList"})
public class Project {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 1000)
    private String description;

    @OneToMany
    private Set<Task> tasks = new HashSet<>();

    @ManyToMany
    private Set<User> users = new HashSet<>();

    @ManyToMany
    private Set<User> managerList = new HashSet<>();

    public void addTask(Task task) {
        tasks.add(task);
        task.setProject(this);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void addManager(User user) {
        managerList.add(user);
    }

}