package culturarte.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {

    @JsonProperty("token")
    private String token;

    @JsonProperty("type")
    private String type;

    @JsonProperty("expiresIn")
    private Long expiresIn;

    public LoginResponse() {}

    public LoginResponse(String token, String type, Long expiresIn) {
        this.token = token;
        this.type = type;
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
