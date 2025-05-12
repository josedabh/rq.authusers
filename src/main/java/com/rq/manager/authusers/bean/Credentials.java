package com.rq.manager.authusers.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * The Class Key.
 */
@Data
public class Credentials {

    /** The token. */
    @Schema(description = "the token", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJleGFtcGxlIiwiaWF0I"
            + "joxNzQzNjk4NDUxLCJleHAiOjE3NTkyNTA0NTF9.tWVVDCT2_rmo"
            + "LsYMu3o7f0No5N5H29NSlRvqNOTuSeaa7UiO38k8FHBIy-414XXrs"
            + "KmUq-KkSIY-fwS7cK8mfA")
    private String token;
    
    /** The is admin. */
    @Schema(description = "The boolean that see the user one look",
         example = "true")
    private boolean isAdmin;
}
