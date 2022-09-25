package de.runtimeterror.customer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record CustomerRegistrationRequest(
        @NotBlank @Size(max = 128) String firstName,
        @NotBlank @Size(max = 128) String lastName,
        @Pattern(regexp = "^(.+)@(\\\\S+)$") String email) {
}
