package org.bobpark.userservice.domain.user.entity;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import java.io.InputStream;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

import org.bobpark.userservice.common.entity.BaseEntity;
import org.bobpark.userservice.domain.user.entity.converter.ByteArrayToInputStreamConverter;

@ToString
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users_document_signatures")
public class UserDocumentSignature extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "signature")
    @Convert(converter = ByteArrayToInputStreamConverter.class)
    private InputStream signature;

    @Builder
    private UserDocumentSignature(Long id, InputStream signature) {

        checkArgument(isNotEmpty(signature), "signature must be provided.");

        this.id = id;
        this.signature = signature;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
