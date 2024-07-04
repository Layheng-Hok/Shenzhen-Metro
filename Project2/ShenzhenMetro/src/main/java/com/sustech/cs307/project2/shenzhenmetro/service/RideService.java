package com.sustech.cs307.project2.shenzhenmetro.service;

import com.sustech.cs307.project2.shenzhenmetro.dto.RideFilterDto;
import com.sustech.cs307.project2.shenzhenmetro.object.Ride;
import com.sustech.cs307.project2.shenzhenmetro.repository.RideRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class RideService {
    @Autowired
    private RideRepository rideRepository;

    public Page<Ride> getAllRidesPaginated(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "rideId");
        return rideRepository.findAll(PageRequest.of(page, size, sort));
    }

    public Page<Ride> getFilteredRidesPaginated(Specification<Ride> spec, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "rideId");
        return rideRepository.findAll(spec, PageRequest.of(page, size, sort));
    }

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
