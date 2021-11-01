package com.teamnexters.lazy.api.config.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamnexters.lazy.api.config.AppleClient;
import com.teamnexters.lazy.api.config.auth.dto.ApplePublicKeyResponse;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:application-oauth.yml")
public class AppleJwtUtils {

    private final AppleClient appleClient;

    @Value("${spring.security.oauth2.client.registration.apple.key-id}")
    private String keyId;

    @Value("${spring.security.oauth2.client.registration.apple.team-id}")
    private String teamId;

    @Value("${spring.security.oauth2.client.registration.apple.client-id}")
    private String clientId;


    public Claims getClaimsBy(String identityToken) {
        try {
            ApplePublicKeyResponse response = appleClient.getAppleAuthPublicKey();
            // 애플 accessToken 의 header 를 Base64, UTF-8 디코딩해서, 알맞는 key 재료를 알기위한 kid, alg 알아내기
            String headerOfIdentityToken = identityToken.substring(0, identityToken.indexOf("."));
            Map header = new ObjectMapper().readValue(new String(Base64.getDecoder().decode(headerOfIdentityToken), StandardCharsets.UTF_8), Map.class);
            ApplePublicKeyResponse.Key key = response.getMatchedKeyBy(header.get("kid"), header.get("alg"))
                    .orElseThrow(() -> new NullPointerException("Failed get public key from apple's id server."));

            // 알맞는 key 재료로 부터, RS256 ( SHA-256 + RSA ) 암호화방식에서 사용하는 n, e 구하기
            byte[] nBytes = Base64.getUrlDecoder().decode(key.getN());
            byte[] eBytes = Base64.getUrlDecoder().decode(key.getE());

            BigInteger n = new BigInteger(1, nBytes);
            BigInteger e = new BigInteger(1, eBytes);

            // n, e를 이용하여 public key 만들기
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
            KeyFactory keyFactory = KeyFactory.getInstance(key.getKty());
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(identityToken).getBody();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            //토큰 서명 검증 or 구조 문제 (Invalid token)
        } catch (ExpiredJwtException e) {
            //토큰이 만료됐기 때문에 클라이언트는 토큰을 refresh 해야함.
        } catch (Exception ignored) {
        }
        return null;
    }

    public String makeClientSecret() throws IOException {
        Date expirationDate = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setHeaderParam("kid", keyId)
                .setHeaderParam("alg", "ES256")
                .setIssuer(teamId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .setAudience("https://appleid.apple.com")
                .setSubject(clientId)
                .signWith(SignatureAlgorithm.ES256, getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() throws IOException {
        ClassPathResource resource = new ClassPathResource("Apple_Developer_페이지에서_다운.p8");
        String privateKey = new String(Files.readAllBytes(Paths.get(resource.getURI())));
        Reader pemReader = new StringReader(privateKey);
        PEMParser pemParser = new PEMParser(pemReader);
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PrivateKeyInfo object = (PrivateKeyInfo) pemParser.readObject();
        return converter.getPrivateKey(object);
    }

}