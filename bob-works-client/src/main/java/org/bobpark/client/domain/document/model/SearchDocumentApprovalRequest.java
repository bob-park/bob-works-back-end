package org.bobpark.client.domain.document.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class SearchDocumentApprovalRequest {
    private String type;
    private Long writerId;
    private String status;
}
