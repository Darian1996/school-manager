package com.darian.schoolmanager.login.DTO.request;

import lombok.Data;

@Data
public class SecurityAddOrUpdateRequest {

    private Long id;

    private String urlPattern;

    private Long orderId;

    private String description;
}