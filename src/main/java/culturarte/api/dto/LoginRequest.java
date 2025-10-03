package culturarte.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @JsonProperty("username")
    @NotBlank(message = "El username es obligatorio")
    private String username;

    @JsonProperty("password")
    @NotBlank(message = "La password es obligatoria")
    private String password;

    public LoginRequest() {}

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
