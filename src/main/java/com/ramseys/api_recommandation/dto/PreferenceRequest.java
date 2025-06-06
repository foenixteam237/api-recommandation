package com.ramseys.api_recommandation.dto;

import lombok.Data;

@Data
public class PreferenceRequest {
    private Long userId;
    private Long tagId;
    private int weight;
}