package org.bobpark.client.common.page;

import java.util.List;

import lombok.Builder;

@Builder(toBuilder = true)
public record Page<T>(List<T> content,
                      long total,
                      PageInfo pageable) {
}
