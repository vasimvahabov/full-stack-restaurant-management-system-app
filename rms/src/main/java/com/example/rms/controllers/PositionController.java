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
import com.example.rms.dtos.PositionDTO;
import com.example.rms.models.PositionModel;
import com.example.rms.services.PositionService;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.PathVariable;

@RestController 
@RequestMapping("/position")
public class PositionController {

  @Autowired
  private PositionService _positionService;

  @GetMapping("list/active")
  public ResponseEntity<List<PositionModel>> getActivePositions(){
    List<PositionDTO> activePositionDTOs=this._positionService.getActivePositions();
    List<PositionModel> activePositionModels=new ArrayList<>();
    for(var item:activePositionDTOs){
      PositionModel positionModel=PositionModel.builder()
                                              .id(item.id)
                                              .title(item.title)
                                              .status(item.status)
                                              .build();
      activePositionModels.add(positionModel);
    }
    return ResponseEntity.ok(activePositionModels);
  }

  @GetMapping("list/all")
  public ResponseEntity<List<PositionModel>> getAllPositions(){
    List<PositionDTO> positionDTOs=this._positionService.getAllPositions();
    List<PositionModel> positionModels=new ArrayList<>();
    for(var item:positionDTOs){
      PositionModel positionModel=PositionModel.builder()
                                              .id(item.id)
                                              .title(item.title)
                                              .status(item.status)
                                              .build();
      positionModels.add(positionModel);
    }
    return ResponseEntity.ok(positionModels);
  }

  @PutMapping("change-status/{posId}")
  public ResponseEntity<Void> changePositionStatus(@PathVariable int posId){
    this._positionService.changePositionStatus(posId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("update")
  public ResponseEntity<Void> updatePosition(@RequestBody PositionModel positionModel){
    PositionDTO positionDTO=new PositionDTO(positionModel.id,positionModel.title,positionModel.status);
    this._positionService.updatePosition(positionDTO);
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }

  @PostMapping("add")
  public ResponseEntity<PositionModel> addPosition(@RequestBody PositionModel positionModel){
    PositionDTO positionDTO=new PositionDTO(positionModel.id,positionModel.title,positionModel.status);
    positionDTO=this._positionService.addPosition(positionDTO);
    positionModel.id=positionDTO.id;
    positionModel.status=positionDTO.status;
    return ResponseEntity.ok(positionModel);
  }
}
