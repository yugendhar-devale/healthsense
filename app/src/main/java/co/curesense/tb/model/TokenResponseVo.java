package co.curesense.tb.model;

import com.google.gson.annotations.SerializedName;

public class TokenResponseVo {
    @SerializedName("access_token")
    public String access_token;
    @SerializedName("token_type")
    public String token_type;
    @SerializedName("expires_in")
    public int expires_in;
    @SerializedName("refresh_token")
    public String refresh_token;
    @SerializedName("scope")
    public String scope;

    @SerializedName("grant_type")
    public String grant_type;
    @SerializedName("client_id")
    public String client_id;
    @SerializedName("client_secret")
    public String client_secret;

    public TokenResponseVo(String refresh_token) {
        this.refresh_token = refresh_token;
        this.grant_type = "refresh_token";
        this.client_id = "Pw1S4SfNNtOLbO0xYeIDRHEu4tZHveGOzg5PtiAR";
        this.client_secret = "h2IQUuMY4wnKE9u00bTIZ3UBzsJrzZthZelhKHcFfjllfeFM3y7I5UrNOWsOwPObOzEuJgtRkYyFpDdrzRS146HyrQklsp8U2S2xjmaRhhahY8I4CCjTmKjzOD4JMSQS";
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
