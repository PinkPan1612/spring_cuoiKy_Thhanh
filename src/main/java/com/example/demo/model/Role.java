package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;
    private String role_description; // miêu tả

    // user
    // role- one -> many -user
    // reverse side (không sở hữu khóa ngoại(foreign key))
    @OneToMany(mappedBy = "role")
    private List<User> users;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole_name() {
        return roleName;
    }

    public void setRole_name(String name) {
        this.roleName = name;
    }

    public String getRole_description() {
        return role_description;
    }

    public void setRole_description(String description) {
        this.role_description = description;
    }

    @Override
    public String toString() {
        return "Role [id=" + id + ", name=" + roleName + ", description=" + role_description + "]";
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
