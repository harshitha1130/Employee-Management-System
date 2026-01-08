package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ExceptionHandler.ResourceNotFoundException;
import dto.EmployeeDTO;
import entity.Employee;
import service.EmployeeService;

@RestController
@RequestMapping("/employees")

//@Controller
public class EmployeeController {

	@Autowired
    private EmployeeService employeeService;

    @GetMapping("/getAllEmployees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/getEmployee/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }



//    @PostMapping(value = "/save", consumes = {"application/json"})
//    public Employee createEmployee(@RequestBody Employee employee) {
//        return employeeService.saveEmployee(employee);
//    }
    
    @PostMapping(value = "/save", consumes = {"application/json"})
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        try {
            Employee savedEmployee = employeeService.saveEmployee(employee);
            String SuccessMessage = " Employee with Id -> " + savedEmployee.getId() + " and Name " + savedEmployee.getName() + " is successfully created";
            return ResponseEntity.status(HttpStatus.CREATED).body(SuccessMessage);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

	  

	/*
	 * @PutMapping("/update/{id}") public Employee updateEmployee(@PathVariable Long
	 * id, @RequestBody Employee employeeDetails) { Employee employee =
	 * employeeService.getEmployeeById(id); if (employee != null) {
	 * employee.setName(employeeDetails.getName());
	 * employee.setRole(employeeDetails.getRole());
	 * employee.setAge(employeeDetails.getAge());
	 * employee.setGender(employeeDetails.getGender());
	 * employee.setAddress(employeeDetails.getAddress());
	 * employee.setPhoneNo(employeeDetails.getPhoneNo());
	 * employee.setEmailId(employeeDetails.getEmailId());
	 * employee.setDepartment(employeeDetails.getDepartment()); return
	 * employeeService.saveEmployee(employee); } return null; }
	 */
    
//    @PutMapping("/updateV1/{id}")
//    public ResponseEntity<Employee> updateEmployee1(@PathVariable Long id, @RequestBody Employee employeeDetails) {
//        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
//        if (updatedEmployee != null) {
//            return ResponseEntity.ok(updatedEmployee);
//        } else {
//            return ResponseEntity.status(404).body(null);
//        }
//    }
    @PutMapping("/updateV1/{id}")
    public ResponseEntity<?> updateEmployee1(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
            if (updatedEmployee != null) {
                return ResponseEntity.ok(updatedEmployee);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

//	   
//    @DeleteMapping("delete/{id}")
//    public void deleteEmployee(@PathVariable Long id) {
//        employeeService.deleteEmployee(id);
//    }
    
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
//        Employee employee = employeeService.getEmployeeById(id);
//        if (employee != null) {
//            employeeService.deleteEmployee(id);
//            return ResponseEntity.ok("🗑️ Employee with ID " + id + " has been deleted successfully.");
//        } else {
//            return ResponseEntity.status(404).body("⚠️ Employee with ID " + id + " not found.");
//        }
//    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok("🗑️ Employee with ID " + id + " has been deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("⚠️ Employee with ID " + id + " not found.");
        }
    }

    
 // New endpoint to get employee details with department details
    @GetMapping("/getEmployeeDetailsWithDepartment/{id}")
    public ResponseEntity<?> getEmployeeDetailsWithDepartment(@PathVariable Long id) {
        try {
            EmployeeDTO employeeDTO = employeeService.getEmployeeDetailsWithDepartment(id);
            return ResponseEntity.ok(employeeDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("Employee with ID " + id + " not found");
        }
    }
    
 
}