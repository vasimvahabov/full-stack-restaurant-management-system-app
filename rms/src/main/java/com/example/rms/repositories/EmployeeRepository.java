package com.example.rms.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.rms.dtos.EmployeeDTO;
import com.example.rms.entities.Employee;

public interface EmployeeRepository extends CrudRepository<Employee,Integer>{

  @Query("select new com.example.rms.dtos.EmployeeDTO("
                                                +"e.id,"
                                                +"e.firstName,"
                                                +"e.lastName,"
                                                +"e.email,"
                                                +"e.phone,"
                                                +"e.status,"
                                                +"e.position.id,"
                                                +"e.position.title,"
                                                +"e.position.status"
                                             +") from Employee e")
  public List<EmployeeDTO> getAllEmployees();
}