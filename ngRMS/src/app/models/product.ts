export interface Product{
  id:number|null;
  title:string;
  price:number;
  status:boolean|null;
  cateId:number;
  cateTitle:string|null;
  cateStatus:boolean|null;
}