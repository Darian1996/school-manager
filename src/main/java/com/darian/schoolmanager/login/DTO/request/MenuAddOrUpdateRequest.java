package com.darian.schoolmanager.login.DTO.request;

import lombok.Data;

@Data
public class MenuAddOrUpdateRequest {

    private Long id;

    private Long parentId;

    private String name;

    private String title;

    private String icon;

    private String jump;

    private Long lastModifiedUserId;

    private Long orderId;
}