package com.sustech.cs307.project2.ChineseSubwaySystem.controller;

import com.sustech.cs307.project2.ChineseSubwaySystem.model.*;
import com.sustech.cs307.project2.ChineseSubwaySystem.repository.LineRepository;
import com.sustech.cs307.project2.ChineseSubwaySystem.repository.LineDetailRepository;
import com.sustech.cs307.project2.ChineseSubwaySystem.repository.StationRepository;
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
public class DataController {
    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private LineDetailRepository lineDetailRepository;

    @Autowired
    private StationRepository stationRepository;

    @GetMapping({"/lines"})
    public String showLineListPage(Model model) {
        List<Line> lines = lineRepository.findAll();
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
            bindingResult.addError(new FieldError("lineDto", "lineName", "Name is too long!"));
        }

        if (lineDto.getStartTime() == null) {
            bindingResult.addError(new FieldError("lineDto", "startTime", "The start time is required!"));
        }

        if (lineDto.getEndTime() == null) {
            bindingResult.addError(new FieldError("lineDto", "endTime", "The end time is required!"));
        }

        if (lineDto.getColor().length() > 5) {
            bindingResult.addError(new FieldError("lineDto", "color", "Color is too long!"));
        }

        if (lineDto.getUrl().length() > 100) {
            bindingResult.addError(new FieldError("lineDto", "url", "URL is too long!"));
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
                bindingResult.addError(new FieldError("lineDto", "startTime", "The start time is required!"));
            }

            if (lineDto.getEndTime() == null) {
                bindingResult.addError(new FieldError("lineDto", "endTime", "The end time is required!"));
            }

            if (lineDto.getColor().length() > 5) {
                bindingResult.addError(new FieldError("lineDto", "color", "Color is too long!"));
            }

            if (lineDto.getUrl().length() > 100) {
                bindingResult.addError(new FieldError("lineDto", "url", "URL is too long!"));
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
            } else {
                model.addAttribute("errorMessage", "Line not found or cannot be removed due to foreign key constraint!");
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            model.addAttribute("errorMessage", "Line cannot be removed due to foreign key constraint!");
        }
        List<Line> lines = lineRepository.findAll();
        model.addAttribute("lines", lines);
        return "lines/index";
    }

    @GetMapping("/lineDetails")
    public String showLineDetailListPage(Model model) {
        List<LineDetail> lineDetails = lineDetailRepository.findAll();
        model.addAttribute("lineDetails", lineDetails);
        return "lineDetails/index";
    }

    @GetMapping("lineDetails/remove")
    @Transactional
    public String removeStationFromLineDetail(@RequestParam int id, Model model) {
            LineDetail lineDetail = lineDetailRepository.findById(id).orElse(null);
            if (lineDetail != null) {
                String lineName = lineDetail.getLineName();
                int stationOrder = lineDetail.getStationOrder();
                lineDetailRepository.delete(lineDetail);
                lineDetailRepository.updateStationOrderAfterDeletion(lineName, stationOrder);  // Update station order
                model.addAttribute("successMessage", "Station removed successfully!");
            } else
                model.addAttribute("errorMessage", "Station not found!");

        List<LineDetail> lineDetails = lineDetailRepository.findAll();
        model.addAttribute("lineDetails", lineDetails);
        return "lineDetails/index";
    }

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
            System.out.println(englishName);
            Station station = stationRepository.findById(englishName).get();
            System.out.println(station);
            model.addAttribute("station", station);

            if (bindingResult.hasErrors()) {
                return "stations/update_station";
            }

            station.setChineseName(stationDto.getChineseName());
            station.setDistrict(stationDto.getDistrict());
            station.setIntro(stationDto.getIntro());
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
                model.addAttribute("errorMessage", "Station not found or cannot be removed due to foreign key constraint!");
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            model.addAttribute("errorMessage", "Station cannot be removed due to foreign key constraint!");
        }
        List<Station> stations = stationRepository.findAll();
        model.addAttribute("stations", stations);
        return "stations/index";
    }
}
