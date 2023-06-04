import { FormControl, ValidationErrors } from "@angular/forms";

export class CustomValidators{
  public static whiteSpace(control:FormControl):ValidationErrors|null{
    if(control.value!==null){
      const value:string=control.value.toString(); 
      for(let char of value)
        if(char===' ')
          return {'whitespace':true};
    }
    return null;
  }
  public static onlyWhiteSpace(control:FormControl):ValidationErrors|null{
    const value:string=control.value.toString().trim();
    return value.length===0?{'onlywhitespace':true}:null
  }
}