package com.faceit.example.internetlibrary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Schema(description = "Role request")
@Data
public class RoleRequest {

    @NotBlank(message = "exception.pleaseProvideARoleName")
    @Schema(example = "ROLE_USER")
    private String name;
}
