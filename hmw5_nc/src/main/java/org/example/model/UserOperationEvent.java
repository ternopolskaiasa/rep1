package org.example.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class UserOperationEvent {
    private String userId;
    private String operationType;
    private String email;
}
