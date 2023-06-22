package org.bobpark.client.domain.user.service.vacation.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import org.bobpark.client.domain.user.feign.UserV1AlternativeVacationClient;
import org.bobpark.client.domain.user.model.vacation.UserAlternativeVacation;
import org.bobpark.client.domain.user.service.vacation.UserAlternativeVacationService;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserAlternativeVacationServiceImpl implements UserAlternativeVacationService {

    private final UserV1AlternativeVacationClient alternativeVacationClient;

    @Override
    public List<UserAlternativeVacation> getUsableList(long id) {
        return alternativeVacationClient.getUsableList(id);
    }
}
