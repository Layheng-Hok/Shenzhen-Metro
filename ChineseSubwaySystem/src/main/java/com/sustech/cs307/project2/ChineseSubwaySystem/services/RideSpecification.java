package com.sustech.cs307.project2.ChineseSubwaySystem.services;

import com.sustech.cs307.project2.ChineseSubwaySystem.model.RideFilterDto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import com.sustech.cs307.project2.ChineseSubwaySystem.model.Ride;

public class RideSpecification {

    public static Specification<Ride> filterRides(RideFilterDto filter) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (filter.getUserNum() != null && !filter.getUserNum().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("userNum"), filter.getUserNum()));
            }

            if (filter.getDuration() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("duration"), filter.getDuration()));
            }

            if (filter.getStartStation() != null && !filter.getStartStation().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("startStation"), filter.getStartStation()));
            }

            if (filter.getEndStation() != null && !filter.getEndStation().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("endStation"), filter.getEndStation()));
            }

            if (filter.getRideClass() != null && !filter.getRideClass().equals("Any")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("rideClass"), filter.getRideClass()));
            }

            if (filter.getPrice() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("price"), filter.getPrice()));
            }

            return predicate;
        };
    }
}

