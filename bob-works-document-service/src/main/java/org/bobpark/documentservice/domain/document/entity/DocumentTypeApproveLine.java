package org.bobpark.documentservice.domain.document.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

import org.bobpark.documentservice.domain.position.entity.Position;
import org.bobpark.documentservice.domain.user.entity.User;

@ToString
@Getter
@NoArgsConstructor
@Entity
@Table(name = "document_types_approve_lines")
public class DocumentTypeApproveLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_id")
    private DocumentTypeApproveLine parent;

    @Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentTypeApproveLine> children = new ArrayList<>();

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id")
    private DocumentType documentType;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void setParent(DocumentTypeApproveLine parent) {
        this.parent = parent;
    }

    public void addChild(DocumentTypeApproveLine child) {
        if (getChildren().stream().anyMatch(item -> item == child)) {
            return;
        }

        child.setParent(this);
        getChildren().add(child);
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
