package com.asodev.library.dto;

import com.asodev.library.model.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

public record AuthenticationResponse(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("refresh_token")
        String refreshToken,

        @JsonProperty("username")
        String username,
        @JsonProperty("role")
        Set<Role> roles


) {

}
