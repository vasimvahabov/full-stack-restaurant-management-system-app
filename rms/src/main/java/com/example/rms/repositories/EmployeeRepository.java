package com.example.rms.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.rms.dtos.EmployeeDTO;
import com.example.rms.entities.Employee;

public interface EmployeeRepository extends CrudRepository<Employee,Integer>{

  @Query(value = "select new com.example.rms.dtos.EmployeeDTO(e.id as string,e.firstName,"
                  +"e.lastName,e.email,e.phone,e.status,p.id,p.title,p.status) from Employee"
                                    +" as e inner join Position as p on e.posId=p.id")
  public List<EmployeeDTO> getAllEmployees();
}