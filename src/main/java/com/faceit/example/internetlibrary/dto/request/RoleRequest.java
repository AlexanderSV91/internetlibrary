package com.faceit.example.internetlibrary.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RoleRequest {

    @NotBlank(message = "Please provide a role name")
    private String name;
}
