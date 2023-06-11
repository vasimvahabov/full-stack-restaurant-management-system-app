package com.example.rms.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.rms.entities.Position;
import com.example.rms.dtos.PositionDTO; 
import com.example.rms.repositories.PositionRepository;

@Service
public class PositionService{
  @Autowired
  private PositionRepository _positionRepository;

  public List<PositionDTO> getActivePositions(){
    List<PositionDTO> activePositionDTOs=this._positionRepository.getActivePositions();
    return activePositionDTOs;
  }

  public List<PositionDTO> getAllPositions(){
    List<PositionDTO> positionDTOs=this._positionRepository.getAllPositions(); 
    return positionDTOs;
  }

  public void changePositionStatus(int posId){  
    Position position=this._positionRepository.findById(posId).orElse(null);
    position.setStatus(!position.getStatus());
    _positionRepository.save(position);
  }

  public void updatePosition(PositionDTO positionDTO){
    Position position=this._positionRepository.findById(positionDTO.id).orElse(null);
    position.setTitle(positionDTO.title);
    _positionRepository.save(position);
  }

  public PositionDTO addPosition(PositionDTO positionDTO){
    Position position=new Position(null,positionDTO.title,null); 
    position=_positionRepository.save(position);  
    positionDTO.id=position.getId();
    positionDTO.status=true;
    return positionDTO;
  } 
}
