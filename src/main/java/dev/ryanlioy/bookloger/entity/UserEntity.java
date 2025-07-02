package dev.ryanlioy.bookloger.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String username;
    @OneToMany
    List<CollectionEntity> collections = new ArrayList<>();
}
