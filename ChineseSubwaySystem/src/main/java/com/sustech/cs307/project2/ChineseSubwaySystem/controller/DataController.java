package com.sustech.cs307.project2.ChineseSubwaySystem.controller;

import com.sustech.cs307.project2.ChineseSubwaySystem.model.*;
import com.sustech.cs307.project2.ChineseSubwaySystem.repository.*;
import com.sustech.cs307.project2.ChineseSubwaySystem.services.RideService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes({"totalStationsToAdd", "stationsAdded"})
public class DataController {
    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private LineDetailRepository lineDetailRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private RideService rideService;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    PassengerRepository passengerRepository;

    @GetMapping("/stations")
    public String showStationListPage(Model model) {
        List<Station> stations = stationRepository.findAll();
        model.addAttribute("stations", stations);
        return "stations/index";
    }

    @GetMapping("stations/create")
    public String showStationCreatePage(Model model) {
        StationDto stationDto = new StationDto();
        model.addAttribute("stationDto", stationDto);
        return "stations/create_station";
    }

    @PostMapping("stations/create")
    public String createStation(@Valid @ModelAttribute StationDto stationDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "stations/create_station";
        }
        Station station = new Station();
        station.setEnglishName(stationDto.getEnglishName());
        station.setChineseName(stationDto.getChineseName());
        station.setDistrict(stationDto.getDistrict());
        station.setIntro(stationDto.getIntro());
        station.setStatus(stationDto.getStatus());
        stationRepository.save(station);
        return "redirect:/stations";
    }

    @GetMapping("stations/update")
    public String showStationUpdatePage(Model model, @RequestParam String englishName) {
        try {
            Station station = stationRepository.findById(englishName).get();
            model.addAttribute("station", station);

            StationDto stationDto = new StationDto();
            stationDto.setEnglishName(station.getEnglishName());
            stationDto.setChineseName(station.getChineseName());
            stationDto.setDistrict(station.getDistrict());
            stationDto.setIntro(station.getIntro());
            stationDto.setStatus(station.getStatus());

            model.addAttribute("stationDto", stationDto);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/stations";
        }
        return "stations/update_station";
    }

    @PostMapping("stations/update")
    public String updateStation(Model model, @RequestParam String englishName, @Valid @ModelAttribute StationDto stationDto, BindingResult bindingResult) {
        try {
            Station station = stationRepository.findById(englishName).get();
            model.addAttribute("station", station);

            if (bindingResult.hasErrors()) {
                return "stations/update_station";
            }

            station.setDistrict(stationDto.getDistrict());
            station.setIntro(stationDto.getIntro());
            station.setStatus(stationDto.getStatus());
            stationRepository.save(station);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/stations";
    }

    @GetMapping("stations/remove")
    public String removeStation(@RequestParam String englishName, Model model) {
        try {
            Station station = stationRepository.findById(englishName).orElse(null);
            if (station != null)
                stationRepository.delete(station);
            else
                model.addAttribute("errorMessage", "Station not found or cannot be removed due to foreign key constraint.");
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            model.addAttribute("errorMessage", "Station cannot be removed due to foreign key constraint.");
        }
        List<Station> stations = stationRepository.findAll();
        model.addAttribute("stations", stations);
        return "stations/index";
    }

    @GetMapping("/lines")
    public String showLineListPage(Model model) {
        List<Line> lines = lineRepository.findAllOrderedByName();
        model.addAttribute("lines", lines);
        return "lines/index";
    }


    @GetMapping("lines/create")
    public String showCreateLinePage(Model model) {
        LineDto lineDto = new LineDto();
        model.addAttribute("lineDto", lineDto);
        return "lines/create_line";
    }

    @PostMapping("lines/create")
    public String createLine(@Valid @ModelAttribute LineDto lineDto, BindingResult bindingResult) {
        if (lineDto.getLineName().length() > 5) {
            bindingResult.addError(new FieldError("lineDto", "lineName", "Name is too long."));
        }

        if (lineDto.getStartTime() == null) {
            bindingResult.addError(new FieldError("lineDto", "startTime", "The start time is required."));
        }

        if (lineDto.getEndTime() == null) {
            bindingResult.addError(new FieldError("lineDto", "endTime", "The end time is required."));
        }

        if (lineDto.getColor().length() > 5) {
            bindingResult.addError(new FieldError("lineDto", "color", "Color is too long."));
        }

        if (lineDto.getUrl().length() > 100) {
            bindingResult.addError(new FieldError("lineDto", "url", "URL is too long."));
        }

        if (bindingResult.hasErrors()) {
            return "lines/create_line";
        }

        Line line = new Line();
        line.setLineName(lineDto.getLineName());
        line.setStartTime(lineDto.getStartTime());
        line.setEndTime(lineDto.getEndTime());
        line.setIntro(lineDto.getIntro());
        line.setMileage(lineDto.getMileage());
        line.setColor(lineDto.getColor());
        line.setFirstOpening(lineDto.getFirstOpening());
        line.setUrl(lineDto.getUrl());
        lineRepository.save(line);

        return "redirect:/lines";
    }

    @GetMapping("lines/update")
    public String showUpdateLinePage(Model model, @RequestParam int id) {
        try {
            Line line = lineRepository.findById(id).get();
            model.addAttribute("line", line);

            LineDto lineDto = new LineDto();
            lineDto.setLineName(line.getLineName());
            lineDto.setStartTime(line.getStartTime());
            lineDto.setEndTime(line.getEndTime());
            lineDto.setIntro(line.getIntro());
            lineDto.setMileage(line.getMileage());
            lineDto.setColor(line.getColor());
            lineDto.setFirstOpening(line.getFirstOpening());
            lineDto.setUrl(line.getUrl());

            model.addAttribute("lineDto", lineDto);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/lines";
        }
        return "lines/update_line";
    }

    @PostMapping("lines/update")
    public String updateLine(Model model, @RequestParam int id, @Valid @ModelAttribute LineDto lineDto, BindingResult bindingResult) {
        try {
            Line line = lineRepository.findById(id).get();
            model.addAttribute("line", line);

            if (lineDto.getStartTime() == null) {
                bindingResult.addError(new FieldError("lineDto", "startTime", "The start time is required."));
            }

            if (lineDto.getEndTime() == null) {
                bindingResult.addError(new FieldError("lineDto", "endTime", "The end time is required."));
            }

            if (lineDto.getColor().length() > 20) {
                bindingResult.addError(new FieldError("lineDto", "color", "Color is too long."));
            }

            if (lineDto.getUrl().length() > 100) {
                bindingResult.addError(new FieldError("lineDto", "url", "URL is too long."));
            }

            if (bindingResult.hasErrors()) {
                return "lines/update_line";
            }

            line.setStartTime(lineDto.getStartTime());
            line.setEndTime(lineDto.getEndTime());
            line.setIntro(lineDto.getIntro());
            line.setMileage(lineDto.getMileage());
            line.setColor(lineDto.getColor());
            line.setFirstOpening(lineDto.getFirstOpening());
            line.setUrl(lineDto.getUrl());
            lineRepository.save(line);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        return "redirect:/lines";
    }

    @GetMapping("lines/remove")
    public String removeLine(@RequestParam int id, Model model) {
        try {
            Line line = lineRepository.findById(id).orElse(null);
            if (line != null) {
                lineRepository.delete(line);
                model.addAttribute("successMessage", "Line removed successfully.");
            } else {
                model.addAttribute("errorMessage", "Line not found or cannot be removed due to foreign key constraint.");
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            model.addAttribute("errorMessage", "Line cannot be removed due to foreign key constraint.");
        }
        List<Line> lines = lineRepository.findAll();
        model.addAttribute("lines", lines);
        return "lines/index";
    }

    @GetMapping("/lineDetails")
    public String showLineDetailListPage(Model model) {
        List<LineDetail> lineDetails = lineDetailRepository.findAllOrderByLineNumberAndStationOrder();
        model.addAttribute("lineDetails", lineDetails);
        return "lineDetails/index";
    }

    @ModelAttribute("totalStationsToAdd")
    public Integer totalStationsToAdd() {
        return 0;
    }

    @ModelAttribute("stationsAdded")
    public Integer stationsAdded() {
        return 0;
    }

    @GetMapping("lineDetails/create")
    public String showLineDetailCreatePage(@RequestParam(required = false) Integer numStations, Model model,
                                           @SessionAttribute("totalStationsToAdd") Integer totalStationsToAdd,
                                           @SessionAttribute("stationsAdded") Integer stationsAdded) {
        if (numStations != null) {
            model.addAttribute("totalStationsToAdd", numStations);
            model.addAttribute("stationsAdded", 0);
        } else {
            model.addAttribute("totalStationsToAdd", totalStationsToAdd);
            model.addAttribute("stationsAdded", stationsAdded);
        }

        LineDetailDto lineDetailDto = new LineDetailDto();
        model.addAttribute("lineDetailDto", lineDetailDto);
        return "lineDetails/create_lineDetail";
    }

    @PostMapping("lineDetails/create")
    @Transactional
    public String createLineDetail(@Valid @ModelAttribute LineDetailDto lineDetailDto, BindingResult bindingResult,
                                   @ModelAttribute("totalStationsToAdd") Integer totalStationsToAdd,
                                   @ModelAttribute("stationsAdded") Integer stationsAdded,
                                   Model model, SessionStatus sessionStatus) {

        String lineName = lineDetailDto.getLineName();
        String stationName = lineDetailDto.getStationName();
        int stationOrder = lineDetailDto.getStationOrder();

        Optional<LineDetail> existingStation = lineDetailRepository.findByLineNameAndStationName(lineName, stationName);
        if (existingStation.isPresent()) {
            bindingResult.addError(new FieldError("lineDetailDto", "stationName", "This station already exists for the specified line."));
        }

        if (lineRepository.findByLineName(lineName) == null) {
            bindingResult.addError(new FieldError("lineDetailDto", "lineName", "Line not found."));
        }

        if (stationRepository.findById(stationName).isEmpty()) {
            bindingResult.addError(new FieldError("lineDetailDto", "stationName", "Station not found."));
        }


        if (bindingResult.hasErrors()) {
            return "lineDetails/create_lineDetail";
        }

        LineDetail lineDetail = new LineDetail();
        lineDetail.setLineName(lineDetailDto.getLineName());
        lineDetail.setStationName(lineDetailDto.getStationName());
        lineDetail.setStationOrder(lineDetailDto.getStationOrder());
        lineDetailRepository.updateStationBeforeCreate(lineName, stationOrder);
        lineDetailRepository.save(lineDetail);

        stationsAdded++;
        model.addAttribute("stationsAdded", stationsAdded);

        if (stationsAdded >= totalStationsToAdd) {
            sessionStatus.setComplete();
            return "redirect:/lineDetails";
        }

        return "redirect:/lineDetails/create";
    }

    @GetMapping("/lineDetails/search")
    public String searchStation(Model model) {
        LineDetailSearchDto lineDetailSearchDto = new LineDetailSearchDto();
        model.addAttribute("lineDetailSearchDto", lineDetailSearchDto);
        return "lineDetails/search_lineDetail";
    }

    @PostMapping("/lineDetails/search")
    public String searchStation(@Valid @ModelAttribute LineDetailSearchDto lineDetailSearchDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "lineDetails/search_lineDetail";
        }

        String lineName = lineDetailSearchDto.getLineName();
        String stationName = lineDetailSearchDto.getStationName();
        int offset = lineDetailSearchDto.getOffset();

        boolean lineExists = lineDetailRepository.existsByLineName(lineName);
        if (!lineExists) {
            bindingResult.addError(new FieldError("lineDetailDto", "lineName", "Line not found."));
        }

        Optional<LineDetail> currentStation = lineDetailRepository.findByLineNameAndStationName(lineName, stationName);
        if (currentStation.isEmpty()) {
            bindingResult.addError(new FieldError("lineDetailDto", "stationName", "Station not found on the specified line."));
        }

        if (bindingResult.hasErrors()) {
            return "lineDetails/search_lineDetail";
        }

        int currentOrder = currentStation.get().getStationOrder();
        int targetOrder = currentOrder + offset;

        Optional<LineDetail> targetStation = lineDetailRepository.findByLineNameAndStationOrder(lineName, targetOrder);
        if (targetStation.isEmpty()) {
            bindingResult.addError(new FieldError("lineDetailDto", "offset", "No station found at the specified offset."));
            return "lineDetails/search_lineDetail";
        }

        model.addAttribute("targetStation", targetStation.get());
        return "lineDetails/search_lineDetail";
    }

    @GetMapping("lineDetails/remove")
    @Transactional
    public String removeStationFromLineDetail(@RequestParam int id, Model model) {
        LineDetail lineDetail = lineDetailRepository.findById(id).orElse(null);
        if (lineDetail != null) {
            String lineName = lineDetail.getLineName();
            int stationOrder = lineDetail.getStationOrder();
            lineDetailRepository.delete(lineDetail);
            lineDetailRepository.updateStationOrderAfterDelete(lineName, stationOrder);
            model.addAttribute("successMessage", "Station removed successfully.");
        } else
            model.addAttribute("errorMessage", "Station not found.");

        List<LineDetail> lineDetails = lineDetailRepository.findAll();
        model.addAttribute("lineDetails", lineDetails);
        return "redirect:/lineDetails";
    }

    @GetMapping("/rides")
    public String showRideListPage(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "100") int size,
                                   Model model) {
        Page<Ride> ridePage = rideService.getRidesPaginated(page, size);
        model.addAttribute("ridePage", ridePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "rides/index";
    }

    @GetMapping("rides/create")
    public String showCreateRidePage(Model model) {
        RideDto rideDto = new RideDto();
        model.addAttribute("rideDto", rideDto);
        return "rides/create_ride";
    }

    @PostMapping("rides/create")
    public String createRide(@Valid @ModelAttribute RideDto rideDto, BindingResult bindingResult) {
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
}