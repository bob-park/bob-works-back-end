package org.bobpark.documentservice.common.utils.documents;

public interface DocumentsUtils {

    int VACATION_HALF_TIME = 4;

    static double calculateVacationCount(double count) {
        double result = 0;

        double calculateCount = count - VACATION_HALF_TIME;

        if (calculateCount > 0) {
            result += 0.5;
        }

        if (calculateCount >= VACATION_HALF_TIME) {

            result += calculateVacationCount(calculateCount);
        }

        return result;
    }
}
