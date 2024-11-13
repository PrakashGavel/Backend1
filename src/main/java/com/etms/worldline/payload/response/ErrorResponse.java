package com.etms.worldline.payload.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorResponse {
   private int response_code;
   private String message;
   public ErrorResponse(int response_code,String message){
       this.response_code=response_code;
       this.message=message;
   }
}
