package com.sustech.cs307.project2.ChineseSubwaySystem.controller;

import com.sustech.cs307.project2.ChineseSubwaySystem.model.Line;
import com.sustech.cs307.project2.ChineseSubwaySystem.model.LineDto;
import com.sustech.cs307.project2.ChineseSubwaySystem.repository.LineRepository;
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
@RequestMapping("/lines")
public class LineController {
    @Autowired
    private LineRepository lineRepository;


    @GetMapping({"", "/"})
    public String showLineListPage(Model model) {
        List<Line> lines = lineRepository.findAllOrderedByName();
        model.addAttribute("lines", lines);
        return "lines/index";
    }

    @GetMapping("/create")
    public String showCreateLinePage(Model model) {
        LineDto lineDto = new LineDto();
        model.addAttribute("lineDto", lineDto);
        return "lines/create_line";
    }

    @Transactional
    @PostMapping("/create")
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

        if (lineDto.getFirstOpening() == null) {
            bindingResult.addError(new FieldError("lineDto", "firstOpening", "The first opening date is required."));
        }

        if (lineDto.getUrl().length() > 100) {
            bindingResult.addError(new FieldError("lineDto", "url", "URL is too long."));
        }

        if (lineRepository.findByLineName(lineDto.getLineName()) != null) {
            bindingResult.addError(new FieldError("lineDto", "lineName", "Line already exists."));
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

    @GetMapping("/update")
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

    @Transactional
    @PostMapping("/update")
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

            if (lineDto.getFirstOpening() == null) {
                bindingResult.addError(new FieldError("lineDto", "firstOpening", "The first opening date is required."));
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

    @GetMapping("/remove")
    public String removeLine(@RequestParam int id, Model model) {
        try {
            Line line = lineRepository.findById(id).orElse(null);
            if (line != null) {
                lineRepository.delete(line);
                model.addAttribute("successMessage", "Line removed successfully.");
            } else {
                model.addAttribute("errorMessage", "Line not found.");
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            model.addAttribute("errorMessage", "Line cannot be removed due to foreign key constraint.");
        }

        List<Line> lines = lineRepository.findAll();
        model.addAttribute("lines", lines);
        return "lines/index";
    }
}
