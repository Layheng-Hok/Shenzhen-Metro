package com.sustech.cs307.project2.ChineseSubwaySystem.services;

import com.sustech.cs307.project2.ChineseSubwaySystem.model.Ride;
import com.sustech.cs307.project2.ChineseSubwaySystem.repository.RideRepository;
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

    public Page<Ride> getRidesPaginated(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "rideId");
        return rideRepository.findAll(PageRequest.of(page, size, sort));
    }

    public Page<Ride> getFilteredRidesPaginated(Specification<Ride> spec, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "rideId");
        return rideRepository.findAll(spec, PageRequest.of(page, size, sort));
    }
}
