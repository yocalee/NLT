package softuni.workshop.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.workshop.data.entities.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository  extends JpaRepository<Employee, Integer> {

    @Query("select e from Employee e where e.age > 25")
    List<Employee> findAllByAgeAbove();
    //TODO
}
