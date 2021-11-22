package com.teamnexters.lazy.api.domain.oauth.apple;

import lombok.Setter;

public class AppleToken {

    @Setter
    public static class Request {
        public String code;
        public String client_id;
        public String client_secret;
        public String grant_type;
        public String refresh_token;

        public static Request of(String code, String clientId, String clientSecret, String grantType, String refreshToken) {
            Request request = new Request();
            request.code = code;
            request.client_id = clientId;
            request.client_secret = clientSecret;
            request.grant_type = grantType;
            request.refresh_token = refreshToken;
            return request;
        }
    }

    @Setter
    public static class Response {
        public String access_token;
        public String expires_in;
        public String id_token;
        public String refresh_token;
        public String token_type;
        public String error;

        public String getAccessToken() {
            return access_token;
        }

        public String getExpiresIn() {
            return expires_in;
        }

        public String getIdToken() {
            return id_token;
        }

        public String getRefreshToken() {
            return refresh_token;
        }

        public String getTokenType() {
            return token_type;
        }
    }
}