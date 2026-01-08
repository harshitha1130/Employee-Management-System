package service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ExceptionHandler.ResourceNotFoundException;
import dto.DepartmentDTO;
import dto.EmployeeDTO;
import entity.Department;
import entity.Employee;
//import jakarta.transaction.Transactional;
import repo.DepartmentRepo;
import repo.EmployeeRepo;

@Service
public class EmployeeService {

	@Autowired
    private EmployeeRepo employeeRepository;
	
    @Autowired
    private DepartmentRepo departmentRepo;

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$");
    
    public List<Employee> getAllEmployees() {
//        return employeeRepository.findAll();
        List<Employee> employees = employeeRepository.findAll();
        employees.forEach(employee -> {
            if (employee.getDepartment() != null) {
                employee.getDepartment().getName(); // Ensure department details are loaded
            }
        });
        return employees;
    }
	

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }
	 
    @Transactional
    public Employee saveEmployee(Employee employee) {
    	validateEmployee(employee);
    	 checkUniqueEmailAndPhone(employee.getEmailId(), employee.getPhoneNo(), null);
         checkDepartmentExists(employee.getDepartment().getId());

        return employeeRepository.save(employee);
    }
	   

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    
  
	/*
	 * @Transactional public Employee updateEmployee(Long id, Employee
	 * employeeDetails) { Employee employee =
	 * employeeRepository.findById(id).orElse(null); if (employee != null) {
	 * validateEmployee(employeeDetails);
	 * employee.setName(employeeDetails.getName());
	 * employee.setRole(employeeDetails.getRole());
	 * employee.setAge(employeeDetails.getAge());
	 * employee.setGender(employeeDetails.getGender());
	 * employee.setAddress(employeeDetails.getAddress());
	 * employee.setPhoneNo(employeeDetails.getPhoneNo());
	 * employee.setEmailId(employeeDetails.getEmailId()); Department department =
	 * departmentRepo.findById(employeeDetails.getDepartment().getId()).orElse(null)
	 * ; if (department != null) { employee.setDepartment(department); } return
	 * employeeRepository.save(employee); } return null; }
	 */
    @Transactional
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            throw new IllegalArgumentException("Employee with ID " + id + " not found");
        }

        validateEmployee(employeeDetails);
        checkUniqueEmailAndPhone(employeeDetails.getEmailId(), employeeDetails.getPhoneNo(), id);

        Department department = departmentRepo.findById(employeeDetails.getDepartment().getId()).orElse(null);
        if (department == null) {
            throw new IllegalArgumentException("Department with ID " + employeeDetails.getDepartment().getId() + " not found");
        }

        employee.setName(employeeDetails.getName());
        employee.setRole(employeeDetails.getRole());
        employee.setAge(employeeDetails.getAge());
        employee.setGender(employeeDetails.getGender());
        employee.setAddress(employeeDetails.getAddress());
        employee.setPhoneNo(employeeDetails.getPhoneNo());
        employee.setEmailId(employeeDetails.getEmailId());
        employee.setDepartment(department);

        return employeeRepository.save(employee);
    }

    
    
    
    // New method to get employee details with department details
    public EmployeeDTO getEmployeeDetailsWithDepartment(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            throw new IllegalArgumentException("Employee with ID " + id + " not found");
        }
        return convertToDTO(employee);
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        if (employee == null) {
            return null;
        }
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setRole(employee.getRole());
        employeeDTO.setAge(employee.getAge());
        employeeDTO.setGender(employee.getGender());
        employeeDTO.setAddress(employee.getAddress());
        employeeDTO.setPhoneNo(employee.getPhoneNo());
        employeeDTO.setEmailId(employee.getEmailId());

        if (employee.getDepartment() != null) {
            employeeDTO.setDepartmentId(employee.getDepartment().getId());
            employeeDTO.setDepartmentName(employee.getDepartment().getName());
        }

        return employeeDTO;
    }
    
    
    private void validateEmployee(Employee employee) {
        if (employee.getName() == null || employee.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (employee.getRole() == null || employee.getRole().isEmpty()) {
            throw new IllegalArgumentException("Role cannot be empty");
        }
        if (employee.getAge() == null || employee.getAge() < 18 || employee.getAge() > 65) {
            throw new IllegalArgumentException("Age must be between 18 and 65");
        }
        if (employee.getGender() == null || (!employee.getGender().equals("Male") && !employee.getGender().equals("Female") && !employee.getGender().equals("Other"))) {
            throw new IllegalArgumentException("Gender must be Male, Female, or Other");
        }
        if (employee.getAddress() == null || employee.getAddress().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }
        if (!PHONE_PATTERN.matcher(employee.getPhoneNo()).matches()) {
            throw new IllegalArgumentException("Phone number must be a 10-digit number");
        }
        if (!EMAIL_PATTERN.matcher(employee.getEmailId()).matches()) {
            throw new IllegalArgumentException("Email ID is not valid");
        }
        if (employee.getDepartment() == null || employee.getDepartment().getId() == null) {
            throw new IllegalArgumentException("Department ID cannot be null");
        }
    }
    
    
    
    private void checkUniqueEmailAndPhone(String email, String phoneNo, Long employeeId) {
        List<Employee> employees = employeeRepository.findAll();
        for (Employee emp : employees) {
            if (!emp.getId().equals(employeeId)) {
                if (emp.getEmailId().equals(email)) {
                    throw new IllegalArgumentException("Email ID is already registered by another employee");
                }
                if (emp.getPhoneNo().equals(phoneNo)) {
                    throw new IllegalArgumentException("Phone number is already registered by another employee");
                }
            }
        }
    }
    
    
    
    private void checkDepartmentExists(Long departmentId) {
        if (!departmentRepo.existsById(departmentId)) {
            throw new ResourceNotFoundException("Department with ID " + departmentId + " does not exist");
        }
    }
       
}