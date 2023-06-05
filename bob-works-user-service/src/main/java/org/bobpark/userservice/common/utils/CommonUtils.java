package org.bobpark.userservice.common.utils;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;

public interface CommonUtils {

    static String generateDateFolderPath() {

        LocalDate now = LocalDate.now();

        List<String> paths = Lists.newArrayList();

        paths.add(String.format("%04d", now.getYear()));
        paths.add(String.format("%02d", now.getMonthValue()));
        paths.add(String.format("%02d", now.getDayOfMonth()));

        return StringUtils.join(paths, File.separatorChar);
    }
}
