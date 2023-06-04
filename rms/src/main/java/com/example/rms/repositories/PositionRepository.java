package com.example.rms.repositories;

import com.example.rms.dtos.PositionDTO;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository; 
import com.example.rms.entities.Position;

public interface PositionRepository extends CrudRepository<Position,Integer>{

  @Query(value="select new com.example.rms.dtos.PositionDTO(id,title,status)"
  		                       + "from Position p where p.status=true")
  public List<PositionDTO> getActivePositions();

  @Query(value="select new com.example.rms.dtos.PositionDTO(id,title,status) from Position p")
  public List<PositionDTO> getAllPositions();
}
