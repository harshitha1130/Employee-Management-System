package service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ExceptionHandler.ResourceNotFoundException;
import ExceptionHandler.ValidationException;
import dto.DepartmentDTO;
import dto.EmployeeDTO;
import dto.EmployeeSummaryDTO;
import entity.Department;
import entity.Employee;
import repo.DepartmentRepo;

@Service
public class DepartmentService {

	@Autowired
    private DepartmentRepo departmentRepository;


	

	public List<Department> getAllDepartments() {
	   return departmentRepository.findAll();
	}


	
	public Department getDepartmentById(Long id) {
		  return departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department with ID " + id + " not found"));	
	}
	
	
    public Department saveDepartment(Department department) {
        validateDepartment(department);
        checkUniqueDepartmentName(department.getName(), null);
        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {
    	
    	  if (!departmentRepository.existsById(id)) {
              throw new ResourceNotFoundException("Department with ID " + id + " not found");
          }
        departmentRepository.deleteById(id);
    }
    
    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department with ID " + id + " not found"));
        validateDepartment(departmentDetails);
        checkUniqueDepartmentName(departmentDetails.getName(), id);
        department.setName(departmentDetails.getName()); // Only update the department name
        return departmentRepository.save(department);
    }
    
//    // New method to get department with employee summaries
//    public DepartmentDTO getDepartmentWithEmployeeSummaries(Long id) {
//        Department department = departmentRepository.findById(id).orElse(null);
//        if (department != null) {
//            List<EmployeeSummaryDTO> employeeSummaries = department.getEmployees().stream()
//                .map(this::convertToEmployeeSummaryDTO)
//                .collect(Collectors.toList());
//            return convertToDepartmentWithEmployeesDTO(department, employeeSummaries);
//        }
//        return null;
//    }
 // New method to get department with employee summaries
    public DepartmentDTO getDepartmentWithEmployeeSummaries(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department with ID " + id + " not found"));
        List<EmployeeSummaryDTO> employeeSummaries = department.getEmployees().stream()
            .map(this::convertToEmployeeSummaryDTO)
            .collect(Collectors.toList());
        return convertToDepartmentWithEmployeesDTO(department, employeeSummaries);
    }


    private EmployeeSummaryDTO convertToEmployeeSummaryDTO(Employee employee) {
        EmployeeSummaryDTO dto = new EmployeeSummaryDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setRole(employee.getRole());
        return dto;
    }

    private DepartmentDTO convertToDepartmentWithEmployeesDTO(Department department, List<EmployeeSummaryDTO> employeeSummaries) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setEmployees(employeeSummaries);
        return dto;
    }
    
    private void validateDepartment(Department department) {
        if (department.getName() == null || department.getName().isEmpty()) {
            throw new ValidationException("Department name cannot be null or empty");
        }
    }
    
    private void checkUniqueDepartmentName(String name, Long departmentId) {
        List<Department> departments = departmentRepository.findAll();
        for (Department dept : departments) {
            if (!dept.getId().equals(departmentId) && dept.getName().equals(name)) {
                throw new ValidationException("Department name '" + name + "' is already registered by another department");
            }
        }
    }
}
    
    
    




























































//------------------------------------------------------------------------------------------------------------

//public List<Department> getAllDepartments() {
//List<Department> departments = departmentRepository.findAll();
//departments.forEach(department -> {
//  if (department.getEmployees() != null) {
//      department.getEmployees().size(); // Ensure employees are loaded
//  }
//});
//return departments;
//}

//public Department getDepartmentById(Long id) {
//return departmentRepository.findById(id).orElse(null);
//}



//public List<Department> getAllDepartments() {
//return departmentRepository.findAll();
//}

//public List<DepartmentDTO> getAllDepartments() {
//  List<Department> departments = departmentRepository.findAll();
//  return departments.stream().map(this::convertToDTO).collect(Collectors.toList());
//}
    
    
    
    
    // New method to fetch department details along with employees
//  public DepartmentDTO getDepartmentWithEmployees(Long id) {
//      Department department = departmentRepository.findById(id).orElse(null);
//      return department != null ? convertToDTO(department) : null;
//  }
//
//  private DepartmentDTO convertToDTO(Department department) {
//      DepartmentDTO dto = new DepartmentDTO();
//      dto.setId(department.getId());
//      dto.setName(department.getName());
//      if (department.getEmployees() != null) {
//          List<EmployeeDTO> employeeDTOs = department.getEmployees().stream()
//              .map(this::convertToSummaryDTO)
//              .collect(Collectors.toList());
//          dto.setEmployees(employeeDTOs);
//      }
//      return dto;
//  }
//
//  private EmployeeDTO convertToSummaryDTO(Employee employee) {
//      EmployeeDTO dto = new EmployeeDTO();
//      dto.setId(employee.getId());
//      dto.setName(employee.getName());
//      dto.setRole(employee.getRole());
//      return dto;
//  }
//}
//

//--------------------------------------------------------------------------------------------