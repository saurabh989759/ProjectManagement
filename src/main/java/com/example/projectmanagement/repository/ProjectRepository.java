package com.example.projectmanagement.repository;

import com.example.projectmanagement.modal.Project;
import com.example.projectmanagement.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwner(User owner) ;

    List<Project> findByNameContainingAndTeamContains(String partialName, User user) ;


    @Query("Select p From Project p join p.team t where t =: user")
    List<Project> findProjectByTeam(@Param("user") User user);

    List<Project> findByTeamContainingOrOwner(User user, User owner);
}
//Feature	      @RequestParam	                                @PathVariable
//Extracts From	  Query parameters in the URL (after ?)	        URL path (before ?)
//URL Format	  http://example.com/api?param=value	        http://example.com/api/resource/{param}