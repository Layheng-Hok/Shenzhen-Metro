package com.sustech.cs307.project2.ChineseSubwaySystem.controller;

import com.sustech.cs307.project2.ChineseSubwaySystem.object.BusExitInfo;
import com.sustech.cs307.project2.ChineseSubwaySystem.dto.BusExitInfoDto;
import com.sustech.cs307.project2.ChineseSubwaySystem.repository.BusExitInfoRepository;
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
@RequestMapping("/buses")
@SessionAttributes({"totalBusesToAdd", "busesAdded"})
public class BusController {
    @Autowired
    private BusExitInfoRepository busExitInfoRepository;

    private String currentStation;

    @GetMapping({"", "/"})
    public String showBusListPage(@RequestParam("englishName") String stationName, Model model) {
        if (stationName == null || stationName.isEmpty())
            stationName = currentStation;
        List<BusExitInfo> busInfoList = busExitInfoRepository.findByStationName(stationName);
        model.addAttribute("busInfoList", busInfoList);
        model.addAttribute("stationName", stationName);
        return "buses/index";
    }

    @GetMapping("/create")
    public String showBusCreatePage(@RequestParam(required = false) Integer numBuses, @RequestParam("englishName") String stationName, Model model,
                                    @SessionAttribute(name = "totalBusesToAdd", required = false) Integer totalBusesToAdd,
                                    @SessionAttribute(name = "busesAdded", required = false) Integer busesAdded) {
        if (stationName == null || stationName.isEmpty())
            stationName = currentStation;

        if (numBuses != null) {
            model.addAttribute("totalBusesToAdd", numBuses);
            model.addAttribute("busesAdded", 0);
        } else {
            model.addAttribute("totalBusesToAdd", totalBusesToAdd);
            model.addAttribute("busesAdded", busesAdded);
        }

        BusExitInfoDto busExitInfoDto = new BusExitInfoDto();
        busExitInfoDto.setStationName(stationName);
        model.addAttribute("busExitInfoDto", busExitInfoDto);
        return "buses/create_bus";
    }

    @Transactional
    @PostMapping("/create")
    public String createBus(@ModelAttribute BusExitInfoDto busExitInfoDto, Model model, BindingResult bindingResult,
                            @SessionAttribute("totalBusesToAdd") Integer totalBusesToAdd,
                            @SessionAttribute("busesAdded") Integer busesAdded) {

        if (busExitInfoRepository.findByStationNameAndBusNameAndBusLine(busExitInfoDto.getStationName(), busExitInfoDto.getBusName(), busExitInfoDto.getBusLine()).isPresent()) {
            bindingResult.addError(new FieldError("busExitInfoDto", "busLine", "Bus already exists."));
            return "buses/create_bus";
        }

        BusExitInfo busExitInfo = new BusExitInfo();
        busExitInfo.setStationName(busExitInfoDto.getStationName());
        busExitInfo.setExitGate(busExitInfoDto.getExit());
        busExitInfo.setBusName(busExitInfoDto.getBusName());
        busExitInfo.setBusLine(busExitInfoDto.getBusLine());
        busExitInfoRepository.save(busExitInfo);

        busesAdded++;
        model.addAttribute("busesAdded", busesAdded);

        if (busesAdded < totalBusesToAdd) {
            return "redirect:/buses/create?englishName=" + busExitInfoDto.getStationName();
        } else {
            model.addAttribute("totalBusesToAdd", null);
            model.addAttribute("busesAdded", null);
            return "redirect:/buses?englishName=" + busExitInfoDto.getStationName();
        }
    }

    @GetMapping("/update")
    public String showUpdateLinePage(Model model, @RequestParam long id) {
        try {
            BusExitInfo busExitInfo = busExitInfoRepository.findById(id).get();
            model.addAttribute("busExitInfo", busExitInfo);

            BusExitInfoDto busExitInfoDto = new BusExitInfoDto();
            busExitInfoDto.setStationName(busExitInfo.getStationName());
            busExitInfoDto.setExit(busExitInfo.getExitGate());
            busExitInfoDto.setBusName(busExitInfo.getBusName());
            busExitInfoDto.setBusLine(busExitInfo.getBusLine());

            model.addAttribute("busExitInfoDto", busExitInfoDto);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/stations";
        }
        return "buses/update_bus";
    }

    @Transactional
    @PostMapping("/update")
    public String updateBus(Model model, @RequestParam long id, @Valid @ModelAttribute BusExitInfoDto busExitInfoDto, BindingResult bindingResult) {
        try {
            BusExitInfo busExitInfo = busExitInfoRepository.findById(id).get();
            model.addAttribute("busExitInfo", busExitInfo);

            if (busExitInfoRepository.findByStationNameAndBusNameAndBusLine(busExitInfoDto.getStationName(), busExitInfoDto.getBusName(), busExitInfoDto.getBusLine()).isPresent()) {
                bindingResult.addError(new FieldError("busExitInfoDto", "busLine", "Bus already exists."));
                return "buses/create_bus";
            }

            busExitInfo.setStationName(busExitInfoDto.getStationName());
            busExitInfo.setExitGate(busExitInfoDto.getExit());
            busExitInfo.setBusName(busExitInfoDto.getBusName());
            busExitInfo.setBusLine(busExitInfoDto.getBusLine());
            busExitInfoRepository.save(busExitInfo);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/buses?englishName=" + busExitInfoDto.getStationName();
        }
        return "redirect:/buses?englishName=" + busExitInfoDto.getStationName();
    }

    @GetMapping("/remove")
    public String removeBus(@RequestParam long id, Model model) {
        BusExitInfo busExitInfo = null;
        try {
            busExitInfo = busExitInfoRepository.findById(id).orElse(null);
            if (busExitInfo != null) {
                busExitInfoRepository.delete(busExitInfo);
                model.addAttribute("successMessage", "Bus removed successfully.");
            } else {
                model.addAttribute("errorMessage", "Bus not found.");
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            model.addAttribute("errorMessage", "Bus cannot be removed due to foreign key constraint.");
        }

        if (busExitInfo != null) {
            currentStation = busExitInfo.getStationName();
            List<BusExitInfo> busInfoList = busExitInfoRepository.findByStationName(busExitInfo.getStationName());
            model.addAttribute("busInfoList", busInfoList);
            return "buses/index";
        } else {
            List<BusExitInfo> busInfoList = busExitInfoRepository.findByStationName(currentStation);
            model.addAttribute("busInfoList", busInfoList);
            return "buses/index";
        }
    }
}
