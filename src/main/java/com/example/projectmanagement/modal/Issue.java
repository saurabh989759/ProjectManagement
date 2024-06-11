package com.example.projectmanagement.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "issues")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status ;
    private Long projectId ;
    private String priority ;
    private LocalDate dueDate ;
    @ElementCollection
    private List<String> tags = new ArrayList<>();


    @ManyToOne
    private User assignee ;

    @JsonIgnore
    @ManyToOne
    private Project project ;

    @JsonIgnore
    @OneToMany(mappedBy = "issue" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
}
