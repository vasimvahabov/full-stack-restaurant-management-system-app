export interface Product{
  id:number|null;
  name:string;
  price:number;
  status:boolean|null;
  cateId:number;
  cateName:string|null;
  cateStatus:boolean|null;
}