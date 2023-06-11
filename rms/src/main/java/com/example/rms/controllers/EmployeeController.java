package com.example.rms.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.rms.dtos.EmployeeDTO;  
import com.example.rms.models.EmployeeModel; 
import com.example.rms.services.EmployeeService;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.PathVariable;

@RestController 
@RequestMapping("employee")
public class EmployeeController {

  @Autowired
  private EmployeeService _employeeService; 
  
  @GetMapping("list/all")
  public ResponseEntity<List<EmployeeModel>> getAllEmployees(){
    List<EmployeeDTO> employeeDTOs=this._employeeService.getAllEmployees();
    List<EmployeeModel> employeeModels=new ArrayList<>();
    for(var item:employeeDTOs){
      EmployeeModel employeeModel=EmployeeModel.builder()
                                                      .id(item.id)
                                                      .firstName(item.firstName)
                                                      .lastName(item.lastName)
                                                      .email(item.email)
                                                      .phone(item.phone)
                                                      .status(item.status)
                                                      .posId(item.posId)
                                                      .posTitle(item.posTitle)
                                                      .posStatus(item.posStatus)
                                                    .build();
      employeeModels.add(employeeModel);
    }
    return ResponseEntity.ok(employeeModels);
  }

  @PostMapping("add")
  public ResponseEntity<EmployeeModel> addEmployee(@RequestBody EmployeeModel employeeModel){
    EmployeeDTO employeeDTO=new EmployeeDTO(null,
                                                  employeeModel.firstName, 
                                                  employeeModel.lastName,
                                                  employeeModel.email,
                                                  employeeModel.phone,
                                                  null,
                                                  employeeModel.posId,
                                                  null,
                                                  null);
    employeeDTO=this._employeeService.addEmployee(employeeDTO);
    employeeModel.id=employeeDTO.id;
    employeeModel.status=employeeDTO.status;
    return ResponseEntity.ok(employeeModel);
  }

  @PutMapping("change-status/{empId}")
  public ResponseEntity<Void> changeEmployeeStatus(@PathVariable int empId){
    this._employeeService.changeEmployeeStatus(empId);
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("update")
  public ResponseEntity<Void> updateEmployee(@RequestBody EmployeeModel employeeModel){
    EmployeeDTO employeeDTO=new EmployeeDTO(employeeModel.id,
                                                  employeeModel.firstName, 
                                                  employeeModel.lastName,
                                                  employeeModel.email,
                                                  employeeModel.phone,
                                                  null,
                                                  employeeModel.posId,
                                                  null,
                                                  null);
    this._employeeService.updateEmployee(employeeDTO);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
