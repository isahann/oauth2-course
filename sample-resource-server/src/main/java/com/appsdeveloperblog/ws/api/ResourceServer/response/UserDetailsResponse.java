package com.appsdeveloperblog.ws.api.ResourceServer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class UserDetailsResponse {
    private String userFirstName;
    private String userLastName;
    private String userId;
}
