package com.nikhil.social_media.dto.requests;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CreateUserRequestDto {
    private String userName;
    private String email;
}
