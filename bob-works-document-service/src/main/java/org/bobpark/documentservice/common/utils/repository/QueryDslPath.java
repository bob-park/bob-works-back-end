package org.bobpark.documentservice.common.utils.repository;

import java.io.Serializable;

import lombok.Builder;

import com.querydsl.core.types.Expression;

@Builder
public record QueryDslPath<T extends Comparable<?>>(String name, Expression<T> path) implements Serializable {

}
