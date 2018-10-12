package com.coocaa.union.manager;

import com.coocaa.union.manager.exception.BaseJSONException;
import com.coocaa.union.manager.utils.ResponseObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CCUnionControllerAdvice {
    @ResponseBody
    @ExceptionHandler(value = BaseJSONException.class)
    public ResponseObject<Object> myErrorHandler(BaseJSONException ex) {
        return ResponseObject.error(String.valueOf(ex.getError().getCode()), ex.getMessage());
    }

}
