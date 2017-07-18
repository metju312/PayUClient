package controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorizeResponse {

    private String accessToken;
    private String tokenType;
    private String expiresIn;
    private String grantType;

    @JsonProperty("access_token")
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @JsonProperty("token_type")
    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @JsonProperty("expires_in")
    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    @JsonProperty("grant_type")
    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    @Override
    public String toString() {
        return "AuthorizeResponse" +
                "accessToken : " + accessToken +
                "\ntokenType : " + tokenType +
                "\nexpiresIn : " + expiresIn +
                "\ngrantType : " + grantType;
    }
}
