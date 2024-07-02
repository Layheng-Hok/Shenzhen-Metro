package com.sustech.cs307.project2.shenzhenmetro.controller;

import com.sustech.cs307.project2.shenzhenmetro.object.Station;
import com.sustech.cs307.project2.shenzhenmetro.dto.StationDto;
import com.sustech.cs307.project2.shenzhenmetro.repository.StationRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/stations")
public class StationController {
    @Autowired
    private StationRepository stationRepository;

    @GetMapping({"", "/"})
    public String showStationListPage(Model model) {
        List<Station> stations = stationRepository.findAll();
        model.addAttribute("stations", stations);
        return "stations/index";
    }

    @GetMapping("/create")
    public String showStationCreatePage(Model model) {
        StationDto stationDto = new StationDto();
        model.addAttribute("stationDto", stationDto);
        return "stations/create_station";
    }

    @Transactional
    @PostMapping("/create")
    public String createStation(@Valid @ModelAttribute StationDto stationDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "stations/create_station";
        }

        if (stationRepository.findById(stationDto.getEnglishName()).isPresent()) {
            bindingResult.addError(new FieldError("stationDto", "englishName", "Station already exists."));
        }

        if (stationRepository.findByChineseName(stationDto.getChineseName()).isPresent()) {
            bindingResult.addError(new FieldError("stationDto", "chineseName", "Station already exists."));
        }

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

    @GetMapping("/update")
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

    @Transactional
    @PostMapping("/update")
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

    @GetMapping("/remove")
    public String removeStation(@RequestParam String englishName, Model model) {
        try {
            Station station = stationRepository.findById(englishName).orElse(null);
            if (station != null) {
                station.setStatus("Closed");
                stationRepository.save(station);
                model.addAttribute("successMessage", "Station status has been updated to \"Closed\".");
            } else {
                model.addAttribute("errorMessage", "Station not found.");
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            model.addAttribute("errorMessage", "Station status cannot be updated due to an error.");
        }
        List<Station> stations = stationRepository.findAll();
        model.addAttribute("stations", stations);
        return "stations/index";
    }

}
