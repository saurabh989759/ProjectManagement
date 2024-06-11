package com.example.projectmanagement.config.modal;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.ArrayList;
import java.util.List;

@Data                       //__________1___________
@Entity                     //_________2___________
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName ;
    private String password;
    private String email ;

    @JsonIgnore
    @OneToMany(mappedBy = "assignee" , cascade = CascadeType.ALL)
    private List<Issue> assignedIssues = new ArrayList<>();

    private int projectSize ; // to ensure the free plan

}


//what is the use of the @Data _________1_________
//Purpose: Reduces boilerplate code by generating common methods.
//Functionality:
//Generates getters and setters for all fields.
//Generates toString(), equals(), and hashCode() methods.
//Generates a constructor requiring all final fields, and optionally all
// fields if no other constructor is defined.


//@Entity  _____________2___________
//Purpose: Indicates that the class is a JPA entity.
//Functionality:
//Maps the class to a database table.
//Allows the class to be managed by the JPA provider (like Hibernate).
//Enables ORM (Object-Relational Mapping) capabilities,
// allowing you to perform CRUD operations on the entity.