export interface Order{
  id:number|null;
  title:string;
  userId:number|null;
  userFullName:string|null;
  createdAt:Date|null;
  updatedAt:Date|null;
  total:number|null;  
}