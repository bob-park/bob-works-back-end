package org.bobpark.authorizationservice.domain.authorization.entity.token;

import static org.springframework.util.StringUtils.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.bobpark.authorizationservice.domain.authorization.converter.BlobToMapConverter;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class AuthorizationToken {

    protected String value;
    protected LocalDateTime issuedAt;
    protected LocalDateTime expiresAt;

    @Convert(converter = BlobToMapConverter.class)
    protected Map<String, Object> metadata;

    @Builder
    private AuthorizationToken(String value, LocalDateTime issuedAt, LocalDateTime expiresAt,
        Map<String, Object> metadata) {
        this.value = value;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.metadata = metadata;
    }

    public Instant getIssuedAtInstant(){
        if(issuedAt == null){
            return null;
        }

        return issuedAt.atZone(ZoneId.systemDefault()).toInstant();
    }

    public Instant getExpiresAtInstant(){
        if(expiresAt == null){
            return null;
        }

        return expiresAt.atZone(ZoneId.systemDefault()).toInstant();
    }

    public boolean isEmpty(){
        return !hasText(value);
    }
}
