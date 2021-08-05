package com.teamnexters.lazy.api.domain.sampleDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PutRequestDto {

    private String name;
    private int age;
    private List<CarDto> carList;

}
