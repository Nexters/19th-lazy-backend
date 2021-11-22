package com.teamnexters.lazy.api.domain.oauth.apple;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ApplePublicKeyResponse {
    public List<Key> keys;

    @Getter
    @Setter
    public static class Key {
        public String kty;
        public String kid;
        public String use;
        public String alg;
        public String n;
        public String e;
    }

    public Optional<Key> getMatchedKeyBy(Object kid, Object alg) {
        return this.keys.stream()
                .filter(key -> key.getKid().equals(kid) && key.getAlg().equals(alg))
                .findFirst();
    }
}