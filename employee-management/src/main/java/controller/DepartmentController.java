package controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ExceptionHandler.ResourceNotFoundException;
import ExceptionHandler.ValidationException;
import dto.DepartmentDTO;
import entity.Department;
import service.DepartmentService;

@RestController
@RequestMapping("/departments")

//@Controller
public class DepartmentController {

	 @Autowired
	 private DepartmentService departmentService;

	    @GetMapping("/getAllDepartments")
	    public List<Department> getAllDepartments() {
	        return departmentService.getAllDepartments();
	    }
	 


//	    @GetMapping("/getDepartment/{id}")
//	    public Department getDepartmentById(@PathVariable Long id) {
//	        return departmentService.getDepartmentById(id);
//	    }
	    @GetMapping("/getDepartment/{id}")
	    public ResponseEntity<?> getDepartmentById(@PathVariable Long id) {
	        try {
	            Department department = departmentService.getDepartmentById(id);
	            return ResponseEntity.ok(department);
	        } catch (ResourceNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        }
	    }
	    


//	    @PostMapping("/save")
//	    public Department createDepartment(@RequestBody Department department) {
//	        return departmentService.saveDepartment(department);
//	    }
	    @PostMapping("/save")
	    public ResponseEntity<?> createDepartment(@RequestBody Department department) {
	        try {
	            Department savedDepartment = departmentService.saveDepartment(department);
	            String successMessage = "Department with name " + savedDepartment.getName() + " has been successfully added.";
	            return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
	        } catch (ValidationException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	        }
	    }

//	    @PutMapping("/update/{id}")
//	    public Department updateDepartment(@PathVariable Long id, @RequestBody Department departmentDetails) {
//	        Department department = departmentService.getDepartmentById(id);
//	        if (department != null) {
//	            department.setName(departmentDetails.getName());
//	            department.setEmployees(departmentDetails.getEmployees());
//	            return departmentService.saveDepartment(department);
//	        }
//	        return null;
//	    }
	    
//	    @PutMapping("/update/{id}")
//	    public Department updateDepartment(@PathVariable Long id, @RequestBody Department departmentDetails) {
//	        Department department = departmentService.getDepartmentById(id);
//	        if (department != null) {
//	            department.setName(departmentDetails.getName()); // Only update the department name
//	            return departmentService.saveDepartment(department);
//	        }
//	        return null;
//	    }
//
//	    @DeleteMapping("/delete/{id}")
//	    public void deleteDepartment(@PathVariable Long id) {
//	        departmentService.deleteDepartment(id);
//	    }
	    
	    @PutMapping("/update/{id}")
	    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @RequestBody Department departmentDetails) {
	        try {
	            Department updatedDepartment = departmentService.updateDepartment(id, departmentDetails);
	            return ResponseEntity.ok(updatedDepartment);
	        } catch (ResourceNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        } catch (ValidationException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	        }
	    }

	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
	        try {
	            departmentService.deleteDepartment(id);
	            return ResponseEntity.ok("Department with ID " + id + " has been deleted successfully.");
	        } catch (ResourceNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        }
	    }

	 // New endpoint to get department with employee summaries
//	    @GetMapping("/getDepartmentWithEmployeeSummaries/{id}")
//	    public DepartmentDTO getDepartmentWithEmployeeSummaries(@PathVariable Long id) {
//	        return departmentService.getDepartmentWithEmployeeSummaries(id);
//	    }
	    
	    
	    // New endpoint to get department with employee summaries
	    
	    @GetMapping("/getDepartmentWithEmployeeSummaries/{id}")
	    public ResponseEntity<?> getDepartmentWithEmployeeSummaries(@PathVariable Long id) {
	        try {
	            DepartmentDTO departmentDTO = departmentService.getDepartmentWithEmployeeSummaries(id);
	            return ResponseEntity.ok(departmentDTO);
	        } catch (ResourceNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        }
	    }
	    

	
	}























































































/*     @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Department department = departmentService.getDepartmentById(id);
        if (department != null) {
            return new ResponseEntity<>(department, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department savedDepartment = departmentService.saveDepartment(department);
        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department departmentDetails) {
        Department department = departmentService.getDepartmentById(id);
        if (department != null) {
            department.setName(departmentDetails.getName());
            Department updatedDepartment = departmentService.saveDepartment(department);
            return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/withEmployeeSummaries/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentWithEmployeeSummaries(@PathVariable Long id) {
        DepartmentDTO departmentDTO = departmentService.getDepartmentWithEmployeeSummaries(id);
        if (departmentDTO != null) {
            return new ResponseEntity<>(departmentDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/
