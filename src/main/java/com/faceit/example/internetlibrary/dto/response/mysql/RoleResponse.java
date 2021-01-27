package com.faceit.example.internetlibrary.dto.response.mysql;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Role response")
@Data
public class RoleResponse {

    @Schema(description = "Identifier")
    private long id;

    @Schema(example = "ROLE_USER")
    private String name;
}
