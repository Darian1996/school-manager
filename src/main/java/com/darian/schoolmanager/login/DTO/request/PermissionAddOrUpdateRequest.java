package com.darian.schoolmanager.login.DTO.request;

import lombok.Data;

import java.util.List;

@Data
public class PermissionAddOrUpdateRequest {

    private Long id;

    private String name;

    private String description;

    private List<Long> securityIdList;
}