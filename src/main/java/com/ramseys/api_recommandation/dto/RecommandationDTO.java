package com.ramseys.api_recommandation.dto;

import lombok.Data;

@Data
public class RecommandationDTO {
    private MediaDTO media;
    private int matchScore;
}