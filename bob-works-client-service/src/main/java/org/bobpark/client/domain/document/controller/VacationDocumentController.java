package org.bobpark.client.domain.document.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.client.common.page.Page;
import org.bobpark.client.common.utils.CommonUtils;
import org.bobpark.client.domain.document.model.AddVacationDocumentRequest;
import org.bobpark.client.domain.document.model.DocumentResponse;
import org.bobpark.client.domain.document.model.SearchVacationDocumentRequest;
import org.bobpark.client.domain.document.model.UsageVacationResponse;
import org.bobpark.client.domain.document.model.VacationDocumentResponse;
import org.bobpark.client.domain.document.model.response.VacationDocumentDetailResponse;
import org.bobpark.client.domain.document.service.VacationDocumentService;
import org.bobpark.client.domain.user.model.UserResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("document/vacation")
public class VacationDocumentController {

    private final VacationDocumentService vacationDocumentService;

    @PostMapping(path = "")
    public DocumentResponse addVacation(@RequestBody AddVacationDocumentRequest addRequest) {
        return vacationDocumentService.addVacation(addRequest);
    }

    @GetMapping(path = "{documentId}")
    public VacationDocumentDetailResponse getVacationDocument(@PathVariable long documentId) {
        return vacationDocumentService.getVacationDocument(documentId);
    }

    @GetMapping(path = "search")
    public Page<VacationDocumentResponse> search(SearchVacationDocumentRequest searchRequest,
        @PageableDefault(size = 30) Pageable pageable) {
        return vacationDocumentService.search(searchRequest, pageable);
    }

    @GetMapping(path = "usage")
    public List<UsageVacationResponse> usage(@AuthenticationPrincipal OidcUser user) {

        UserResponse userResponse = CommonUtils.parseToUserResponse(user);

        return vacationDocumentService.usage(userResponse.id());
    }
}
