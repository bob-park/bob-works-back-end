package org.bobpark.client.common.page;

import lombok.Builder;

@Builder
public record PageInfo(int page,
                       int size,
                       PageSort sort) {
}
