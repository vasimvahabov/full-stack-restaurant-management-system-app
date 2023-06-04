import { StringifyOptions } from "querystring";

export interface User{
  id:number|null;
  username:string;
  password:string|null;
  firstName:string|null;
  lastName:string|null;
  status:boolean|null;
}