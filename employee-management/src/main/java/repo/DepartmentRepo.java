package repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import entity.Department;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long>
{
//	  @Query("SELECT d FROM Department d JOIN FETCH d.employees WHERE d.id = :id")
//	    Department findByIdWithEmployees(Long id);
	
	 @Query("SELECT d FROM Department d LEFT JOIN FETCH d.employees")
	    List<Department> findAllWithEmployees();
}
