package com.sparta.todoparty;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) //오브젝트를 응답을 할때 필드가 null 인 애가 있으면 json 으로 파싱할때 넣지않음
public class CommonResponseDto {
    //API 응답할때 쓰는 DTO

    private String msg; // 메세지
    private Integer statusCode; // 응답코드
}
