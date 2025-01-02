package org.bobpark.userservice.domain.user.scheduler;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.entity.UserVacation;
import org.bobpark.userservice.domain.user.model.SearchUserRequest;
import org.bobpark.userservice.domain.user.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class UserVacationScheduler {

    private static final int DEFAULT_TOTAL_VACATION_COUNT = 15;
    private static final List<Integer> INCREMENT_CONTINUOUS_EMPLOYEE_YEARS =
        List.of(1, 3, 5, 7, 10, 12, 14, 15, 17, 20, 25);

    private final UserRepository userRepository;

    @Transactional
    @Scheduled(cron = "${user.scheduler.cron.create-user-vacation:0 5 0 * * *}")
    public void createUserVacation() {

        LocalDate now = LocalDate.now();

        List<User> users = userRepository.search(SearchUserRequest.builder().build());

        for (User user : users) {
            List<UserVacation> vacations = user.getVacations();

            if (vacations.stream()
                .anyMatch(vacation -> vacation.getYear() == now.getYear())) {
                continue;
            }

            LocalDate employmentDate = user.getEmploymentDate();

            Period between = Period.between(employmentDate, now);

            int totalVacationCount = DEFAULT_TOTAL_VACATION_COUNT + incrementVacationCount(between.getYears());

            user.createVacations(now.getYear(), totalVacationCount, 0);

            log.debug("created user vacation. (totalCount={})", totalVacationCount);

        }

    }

    private int incrementVacationCount(int continuousEmployeeYears) {

        int index = 0;

        for (Integer year : INCREMENT_CONTINUOUS_EMPLOYEE_YEARS) {

            if (year > continuousEmployeeYears) {
                break;
            }

            index++;
        }

        return index;
    }

}
