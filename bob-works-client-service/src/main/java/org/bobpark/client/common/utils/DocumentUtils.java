package org.bobpark.client.common.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.bobpark.client.domain.document.feign.DocumentClient;
import org.bobpark.client.domain.document.feign.DocumentTypeClient;
import org.bobpark.client.domain.document.model.DocumentApprovalResponse;
import org.bobpark.client.domain.document.model.DocumentTypeApprovalLineResponse;
import org.bobpark.client.domain.document.model.response.DocumentTypeApprovalLineStatusResponse;
import org.bobpark.client.domain.user.feign.UserClient;
import org.bobpark.client.domain.user.model.UserResponse;

public class DocumentUtils {

    private static DocumentUtils instance;

    private DocumentClient documentClient;
    private DocumentTypeClient documentTypeClient;
    private UserClient userClient;


    public static DocumentUtils getInstance() {
        if (instance == null) {
            instance = new DocumentUtils();
        }

        return instance;
    }

    public void extractLines(DocumentTypeApprovalLineResponse line, List<DocumentApprovalResponse> approvals,
        List<DocumentTypeApprovalLineStatusResponse> list) {

        DocumentApprovalResponse approval =
            approvals.stream()
                .filter(item -> item.lineId().equals(line.id()))
                .findAny()
                .orElse(null);

        UserResponse user = getUser(line.userId());

        DocumentTypeApprovalLineStatusResponse lineStatus =
            DocumentTypeApprovalLineStatusResponse.builder()
                .id(line.id())
                .uniqueUserId(user.id())
                .userId(user.userId())
                .username(user.name())
                .positionId(user.position().id())
                .positionName(user.position().name())
                .status(approval != null ? approval.status() : "WAITING")
                .approvedDateTime(approval != null ? approval.approvedDateTime() : null)
                .reason(approval != null ? approval.reason() : null)
                .build();

        list.add(lineStatus);

        if (line.next() != null) {
            extractLines(line.next(), approvals, list);
        }
    }

    public UserResponse getUser(long userId) {
        return userClient.getUserById(userId);
    }

    @Autowired
    public void setDocumentClient(DocumentClient documentClient) {
        this.documentClient = documentClient;
    }

    @Autowired
    public void setDocumentTypeClient(DocumentTypeClient documentTypeClient) {
        this.documentTypeClient = documentTypeClient;
    }

    @Autowired
    public void setUserClient(UserClient userClient) {
        this.userClient = userClient;
    }
}
