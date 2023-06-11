import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog'; 
import { Position } from '../../models/position';
import { PositionService } from '../../services/position.service';
import { AddPositionComponent } from './add-position/add-position.component';
import { EditPositionComponent } from './edit-position/edit-position.component';

@Component({
  selector: 'app-positions',
  templateUrl: './positions.component.html',
  styleUrls: ['./positions.component.scss']
})
export class PositionsComponent {

  public positions!:Position[]; 

  constructor(private positionService:PositionService,private dialog:MatDialog){}

  ngOnInit(){
    this.positionService.getAllPositions().subscribe(response=>{ 
      if(response!==-1)
        this.positions=response; 
    });
  }

  addPosition=()=>{
    const addPosDialog=this.dialog.open(AddPositionComponent,{
      height:'300px',
      disableClose:true
    });
    addPosDialog.afterClosed().subscribe((response:any)=>{
      if(response.data!==null){
        const positionDTO=response.data;
        this.positions.push(positionDTO);
      }
    });
  }

  onSlideToogle=(posId:number)=>{
    this.positionService.changePositionStatus(posId).subscribe();
  }

  editPosition=(position:Position)=>{
    this.dialog.open(EditPositionComponent,{
      data:position,
      disableClose:true,
      height:'300px'
    });
  }
}
