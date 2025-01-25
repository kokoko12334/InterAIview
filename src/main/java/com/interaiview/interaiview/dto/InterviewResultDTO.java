package com.interaiview.interaiview.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InterviewResultDTO {
    private List<QaPairDTO> qas;
    private String result;
}
