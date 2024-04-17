package ph.edu.cksc.college.advweb.blog.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    private String loginName;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}