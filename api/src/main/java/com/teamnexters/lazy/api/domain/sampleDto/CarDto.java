package com.teamnexters.lazy.api.domain.sampleDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CarDto {

    private String name;
    @JsonProperty("car_number")
    private String carNumber;
}
