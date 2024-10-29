package org.bobpark.documentservice.domain.document.entity.holiday;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

import org.apache.commons.lang3.StringUtils;

import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.entity.DocumentType;
import org.bobpark.documentservice.domain.document.type.DocumentStatus;
import org.bobpark.documentservice.domain.document.type.DocumentTypeName;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "holiday_work_reports")
@DiscriminatorValue("HOLIDAY_WORK")
public class HolidayWorkReport extends Document {

    private String workPurpose;

    @Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HolidayWorkUser> users = new ArrayList<>();

    @Builder
    private HolidayWorkReport(Long id, DocumentType documentType, Long writerId, Long teamId, DocumentStatus status,
        String workPurpose) {
        super(id, DocumentTypeName.HOLIDAY_WORK, documentType, writerId, teamId, status);

        checkArgument(StringUtils.isNotBlank(workPurpose), "workPurpose must be provided.");

        this.workPurpose = workPurpose;
    }

    public void addUser(HolidayWorkUser workUser) {

        workUser.setReport(this);

        getUsers().add(workUser);
    }

    public void removeUser(HolidayWorkUser workUser) {

        HolidayWorkUser findUser =
            getUsers().stream()
                .filter(user -> user.getId().equals(workUser.getId()))
                .findAny()
                .orElse(null);

        if (findUser == null) {
            return;
        }

        getUsers().remove(findUser);
    }
}
