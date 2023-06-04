package com.example.rms.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.rms.dtos.EmployeeDTO;
import com.example.rms.entities.Employee; 
import com.example.rms.repositories.EmployeeRepository;

@Service
public class EmployeeService {
  @Autowired
  private EmployeeRepository _employeeRepository;

  public List<EmployeeDTO> getAllEmployees(){
    List<EmployeeDTO> employeeDTOs=this._employeeRepository.getAllEmployees();
    return employeeDTOs;
  } 

  public EmployeeDTO addEmployee(EmployeeDTO employeeDTO){ 
    String employeePhone="+994"+employeeDTO.phone;
    Employee employee=new Employee(null,employeeDTO.firstName,employeeDTO.lastName,
                      employeeDTO.email,employeePhone,employeeDTO.posId,employeeDTO.status);
    employee=this._employeeRepository.save(employee); 
    employeeDTO.id=employee.getId(); 
    employeeDTO.status=true; 

    return employeeDTO;
  }

  public void changeEmployeeStatus(int empId){
    Employee employee=this._employeeRepository.findById(empId).orElse(null); 
    employee.setStatus(!employee.getStatus());
    this._employeeRepository.save(employee);
  }

  public void updateEmployee(EmployeeDTO employeeDTO){
   Employee employee=this._employeeRepository.findById(employeeDTO.id).orElse(null);
   
   employee.setFirstName(employeeDTO.firstName);
   employee.setLastName(employeeDTO.lastName);
   employee.setEmail(employeeDTO.email);
   employee.setPosId(employeeDTO.posId);   
   String employeePhone="+994"+employeeDTO.phone;
   employee.setPhone(employeePhone);
   
   this._employeeRepository.save(employee);
  }
}
