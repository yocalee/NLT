package softuni.workshop.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.workshop.data.entities.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Project findByName(String projectName);

    @Query("select p from Project p where p.isFinished = true")
    List<Project> findAllByFinished();

}
