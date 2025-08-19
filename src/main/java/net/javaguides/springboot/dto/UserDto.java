package net.javaguides.springboot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "UserDto Model Information"
)
public class UserDto {


    private Long id;
    @Schema(
            description = "User First Name"
    )
    @NotEmpty(message = "User first Name should not be null")
    private String firstName;

    @Schema(
            description = "User Last Name"
    )
    @NotEmpty(message = "User lst Name should not be null")
    private String lastName;

    @Schema(
            description = "User Email Address"
    )
    @NotEmpty(message = "User email should not be null")
    @Email(message = "Email address should not be valid")
    private String email;
}
