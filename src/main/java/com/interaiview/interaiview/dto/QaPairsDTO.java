package com.interaiview.interaiview.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QaPairsDTO {
    // Question&Answer list
    private List<QaPairDTO> qas;
}
