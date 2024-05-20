package com.sustech.cs307.project2.ChineseSubwaySystem.controller;

import com.sustech.cs307.project2.ChineseSubwaySystem.dto.RideDto;
import com.sustech.cs307.project2.ChineseSubwaySystem.dto.RideFilterDto;
import com.sustech.cs307.project2.ChineseSubwaySystem.object.*;
import com.sustech.cs307.project2.ChineseSubwaySystem.repository.*;
import com.sustech.cs307.project2.ChineseSubwaySystem.services.RideService;
import com.sustech.cs307.project2.ChineseSubwaySystem.services.RideSpecification;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;

@Controller
@RequestMapping("/rides")
public class RideController {
    @Autowired
    private RideService rideService;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private OngoingRideRepository ongoingRideRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private RoutePricingRepository routePricingRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @GetMapping({"", "/"})
    public String showRideListPage(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "100") int size,
                                   Model model) {
        Page<Ride> ridePage = rideService.getRidesPaginated(page, size);
        model.addAttribute("ridePage", ridePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "rides/index";
    }

    @GetMapping("/create")
    public String showCreateRidePage(Model model) {
        RideDto rideDto = new RideDto();
        model.addAttribute("rideDto", rideDto);
        return "rides/create_ride";
    }

    @Transactional
    @PostMapping("/create")
    public String createRide(@Valid @ModelAttribute RideDto rideDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "rides/create_ride";
        }

        String userNum = rideDto.getUserNum();
        String startStation = rideDto.getStartStation();

        if (userNum.length() != 9 && userNum.length() != 18) {
            bindingResult.addError(new FieldError("rideDto", "userNum", "Invalid user number."));
        }

        if (userNum.length() == 9 && cardRepository.findById(userNum).isEmpty()) {
            bindingResult.addError(new FieldError("rideDto", "userNum", "Card not found."));
        }

        if (userNum.length() == 18 && passengerRepository.findById(userNum).isEmpty()) {
            bindingResult.addError(new FieldError("rideDto", "userNum", "National ID not found."));
        }

        if (stationRepository.findById(startStation).isEmpty()) {
            bindingResult.addError(new FieldError("rideDto", "startStation", "Station not found."));
        } else if (!stationRepository.findById(startStation).get().getStatus().equals("Operational")) {
            bindingResult.addError(new FieldError("rideDto", "startStation", "Station is currently not operational."));
        }

        if (bindingResult.hasErrors()) {
            return "rides/create_ride";
        }

        Ride ride = new Ride();
        ride.setUserNum(userNum);
        ride.setAuthType(userNum.length() == 9 ? "Travel card" : "National ID");
        ride.setStartStation(startStation);
        ride.setStartTime(new Timestamp(System.currentTimeMillis()));
        ride.setRideClass(rideDto.getRideClass());
        rideRepository.save(ride);

        return "redirect:/rides";
    }

    @GetMapping("/update")
    public String showUpdateRidePage(Model model, @RequestParam long id) {
        try {
            Ride ride = rideRepository.findById(id).get();
            model.addAttribute("ride", ride);

            RideDto rideDto = new RideDto();
            rideDto.setUserNum(ride.getUserNum());
            rideDto.setAuthType(ride.getAuthType());
            rideDto.setStartTime(ride.getStartTime());
            rideDto.setStartStation(ride.getStartStation());
            rideDto.setRideClass(ride.getRideClass());

            model.addAttribute("rideDto", rideDto);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/ongoingRides";
        }
        return "rides/update_ride";
    }

    @Transactional
    @PostMapping("/update")
    public String updateRide(Model model, @RequestParam long id, @Valid @ModelAttribute RideDto rideDto, BindingResult bindingResult) {
        try {
            Ride ride = rideRepository.findById(id).get();
            model.addAttribute("ride", ride);

            String endStation = rideDto.getEndStation();

            if (stationRepository.findById(endStation).isEmpty()) {
                bindingResult.addError(new FieldError("rideDto", "endStation", "Station not found."));
                return "rides/update_ride";
            } else if (!stationRepository.findById(endStation).get().getStatus().equals("Operational")) {
                bindingResult.addError(new FieldError("rideDto", "endStation", "Station is currently not operational."));
                return "rides/update_ride";
            }

            String userNum = rideDto.getUserNum();
            String rideClass = ride.getRideClass();
            float economyPrice = routePricingRepository.findByStartStationAndEndStation(ride.getStartStation(), rideDto.getEndStation()).getPrice();
            float businessPrice = (float) (economyPrice + 0.5 * economyPrice);

            if (userNum.length() == 9) {
                if (rideClass.equals("Economy") && cardRepository.findById(userNum).get().getMoney() < economyPrice) {
                    bindingResult.addError(new FieldError("rideDto", "userNum", "Insufficient balance in card."));
                } else if (rideClass.equals("Business") && cardRepository.findById(userNum).get().getMoney() < businessPrice) {
                    bindingResult.addError(new FieldError("rideDto", "userNum", "Insufficient balance in card."));
                }
            }

            if (bindingResult.hasErrors()) {
                return "rides/update_ride";
            }

            float fee = rideClass.equals("Economy") ? economyPrice : businessPrice;
            ride.setEndTime(new Timestamp(System.currentTimeMillis()));
            ride.setDuration(Duration.between(ride.getStartTime().toInstant(), ride.getEndTime().toInstant()).getSeconds());
            ride.setEndStation(endStation);
            ride.setPrice(fee);

            rideRepository.save(ride);

            if (userNum.length() == 9) {
                Card card = cardRepository.findById(userNum).get();
                card.setMoney(card.getMoney() - fee);
                cardRepository.save(card);
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        return "redirect:/rides";
    }

    @GetMapping("/filter")
    public String filterRide(Model model) {
        RideFilterDto rideFilterDto = new RideFilterDto();
        model.addAttribute("rideFilterDto", rideFilterDto);
        return "rides/filter_ride";
    }

    @Transactional
    @PostMapping("/filter")
    public String filterRide(@Valid @ModelAttribute RideFilterDto rideFilterDto, Model model, BindingResult bindingResult,
                             @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size) {
        if (bindingResult.hasErrors()) {
            return "rides/filter_ride";
        }

        Specification<Ride> spec = RideSpecification.filterRides(rideFilterDto);
        Page<Ride> ridePage = rideService.getFilteredRidesPaginated(spec, page, size);

        model.addAttribute("ridePage", ridePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);

        return "rides/filter_ride";
    }


    @GetMapping("/ongoingRides")
    public String showOngoingRideListPage(Model model) {
        List<OngoingRide> ongoingRides = ongoingRideRepository.findAll();
        model.addAttribute("ongoingRides", ongoingRides);
        return "ongoingRides/index";
    }
}
