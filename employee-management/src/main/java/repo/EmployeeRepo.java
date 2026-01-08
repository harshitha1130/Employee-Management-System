package repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entity.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> 
{

	Employee findByPhoneNo(String phoneNo);

	
}
