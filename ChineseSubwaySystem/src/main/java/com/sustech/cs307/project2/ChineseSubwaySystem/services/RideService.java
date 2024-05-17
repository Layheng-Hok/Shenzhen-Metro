package com.sustech.cs307.project2.ChineseSubwaySystem.services;

import com.sustech.cs307.project2.ChineseSubwaySystem.model.Ride;
import com.sustech.cs307.project2.ChineseSubwaySystem.repository.RidePaginationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RideService {
    @Autowired
    private RidePaginationRepository ridePaginationRepository;

    public Page<Ride> getRidesPaginated(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "rideId");
        return ridePaginationRepository.findAll(PageRequest.of(page, size, sort));
    }
}
