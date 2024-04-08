package ph.edu.cksc.college.advweb.blog.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Long id;
    private String username;
    private String loginName;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String username, String loginName, List<String> roles) {
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        this.loginName = loginName;
        this.roles = roles;
    }
}