export interface Order{
  id:number|null;
  title:string;
  userId:number;
  userFullname:string|null;
  createdAt:string|null;
  updatedAt:string|null;
  total:number|null;  
}