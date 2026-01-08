package dto;

import java.util.List;

public class DepartmentDTO {

    private Long id;
    private String name;
//    private List<EmployeeDTO> employees;
//    private List<String> employees;
    private List<EmployeeSummaryDTO> employees;

    
    
    
    // Getters and Setters
    // (Include getters and setters for all fields)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//	public List<EmployeeDTO> getEmployees() {
//		return employees;
//	}
//	public void setEmployees(List<EmployeeDTO> employees) {
//		this.employees = employees;
//	}
//	
//	public List<String> getEmployees() {
//        return employees;
//    }
//
//    public void setEmployees(List<String> employees) {
//        this.employees = employees;
//    }
	public List<EmployeeSummaryDTO> getEmployees() {
		return employees;
	}
	public void setEmployees(List<EmployeeSummaryDTO> employees) {
		this.employees = employees;
	}

   
    
}
