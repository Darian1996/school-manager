package com.darian.schoolmanager.login.DTO;

import com.darian.schoolmanager.login.DO.PermissionDO;
import com.darian.schoolmanager.login.DO.SecurityDO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PermissionDTO extends PermissionDO {

    private List<SecurityDO> securityDOList = new ArrayList<>();

    private String securityListString;
}