package org.opensource.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CommonApiResult {

    private int code;

    private String message;

    public static CommonApiResult createOk(){
        return CommonApiResult.builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .build();
    }

    public static CommonApiResult createOk(String message){
        return CommonApiResult.builder()
                .code(HttpStatus.OK.value())
                .message(message)
                .build();
    }
}
