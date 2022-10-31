package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private String email;
    private String firstName;
    private Long id;
    private String lastName;
    private String password;
    private String phone;
    private Long userStatus;
    private String username;

}
