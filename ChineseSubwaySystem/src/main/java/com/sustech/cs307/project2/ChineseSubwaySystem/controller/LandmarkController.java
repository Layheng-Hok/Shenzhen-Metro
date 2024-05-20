package com.sustech.cs307.project2.ChineseSubwaySystem.controller;

import com.sustech.cs307.project2.ChineseSubwaySystem.dto.LandmarkExitInfoDto;
import com.sustech.cs307.project2.ChineseSubwaySystem.object.LandmarkExitInfo;
import com.sustech.cs307.project2.ChineseSubwaySystem.repository.LandmarkExitInfoRepository;
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
@RequestMapping("/landmarks")
@SessionAttributes({"totalLandmarksToAdd", "landmarksAdded"})
public class LandmarkController {
    @Autowired
    private LandmarkExitInfoRepository landmarkExitInfoRepository;

    private String currentStation;

    @GetMapping({"", "/"})
    public String showLandmarkListPage(@RequestParam("englishName") String stationName, Model model) {
        if (stationName == null || stationName.isEmpty())
            stationName = currentStation;
        List<LandmarkExitInfo> landmarkInfoList = landmarkExitInfoRepository.findByStationName(stationName);
        model.addAttribute("landmarkInfoList", landmarkInfoList);
        model.addAttribute("stationName", stationName);
        return "landmarks/index";
    }

    @GetMapping("/create")
    public String showLandmarkCreatePage(@RequestParam(required = false) Integer numLandmarks, @RequestParam("englishName") String stationName, Model model,
                                         @SessionAttribute(name = "totalLandmarksToAdd", required = false) Integer totalLandmarksToAdd,
                                         @SessionAttribute(name = "landmarksAdded", required = false) Integer landmarksAdded) {
        if (stationName == null || stationName.isEmpty())
            stationName = currentStation;

        if (numLandmarks != null) {
            model.addAttribute("totalLandmarksToAdd", numLandmarks);
            model.addAttribute("landmarksAdded", 0);
        } else {
            model.addAttribute("totalLandmarksToAdd", totalLandmarksToAdd);
            model.addAttribute("landmarksAdded", landmarksAdded);
        }

        LandmarkExitInfoDto landmarkExitInfoDto = new LandmarkExitInfoDto();
        landmarkExitInfoDto.setStationName(stationName);
        model.addAttribute("landmarkExitInfoDto", landmarkExitInfoDto);
        return "landmarks/create_landmark";
    }

    @Transactional
    @PostMapping("/create")
    public String createLandmark(@Valid @ModelAttribute LandmarkExitInfoDto landmarkExitInfoDto,
                                 BindingResult bindingResult,
                                 Model model,
                                 @SessionAttribute("totalLandmarksToAdd") Integer totalLandmarksToAdd,
                                 @SessionAttribute("landmarksAdded") Integer landmarksAdded) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("landmarkExitInfoDto", landmarkExitInfoDto);
            return "landmarks/create_landmark";
        }

        if (landmarkExitInfoRepository.findByStationNameAndExitGateAndLandmark(
                landmarkExitInfoDto.getStationName(),
                landmarkExitInfoDto.getExitGate(),
                landmarkExitInfoDto.getLandmark()).isPresent()) {
            bindingResult.addError(new FieldError("landmarkExitInfoDto", "landmark", "Landmark already exists."));
            return "landmarks/create_landmark";
        }

        LandmarkExitInfo landmarkExitInfo = new LandmarkExitInfo();
        landmarkExitInfo.setStationName(landmarkExitInfoDto.getStationName());
        landmarkExitInfo.setExitGate(landmarkExitInfoDto.getExitGate());
        landmarkExitInfo.setLandmark(landmarkExitInfoDto.getLandmark());
        landmarkExitInfoRepository.save(landmarkExitInfo);

        landmarksAdded++;
        model.addAttribute("landmarksAdded", landmarksAdded);

        if (landmarksAdded < totalLandmarksToAdd) {
            return "redirect:/landmarks/create?englishName=" + landmarkExitInfoDto.getStationName();
        } else {
            model.addAttribute("totalLandmarksToAdd", null);
            model.addAttribute("landmarksAdded", null);
            return "redirect:/landmarks?englishName=" + landmarkExitInfoDto.getStationName();
        }
    }


    @GetMapping("/update")
    public String showUpdateLandmarkPage(Model model, @RequestParam long id) {
        try {
            LandmarkExitInfo landmarkExitInfo = landmarkExitInfoRepository.findById(id).get();
            model.addAttribute("landmarkExitInfo", landmarkExitInfo);

            LandmarkExitInfoDto landmarkExitInfoDto = new LandmarkExitInfoDto();
            landmarkExitInfoDto.setStationName(landmarkExitInfo.getStationName());
            landmarkExitInfoDto.setExitGate(landmarkExitInfo.getExitGate());
            landmarkExitInfoDto.setLandmark(landmarkExitInfo.getLandmark());

            model.addAttribute("landmarkExitInfoDto", landmarkExitInfoDto);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/stations";
        }
        return "landmarks/update_landmark";
    }

    @Transactional
    @PostMapping("/update")
    public String updateLandmark(Model model, @RequestParam long id, @Valid @ModelAttribute LandmarkExitInfoDto landmarkExitInfoDto, BindingResult bindingResult) {
        try {
            LandmarkExitInfo landmarkExitInfo = landmarkExitInfoRepository.findById(id).get();
            model.addAttribute("landmarkExitInfo", landmarkExitInfo);

            if (bindingResult.hasErrors()) {
                return "landmarks/update_landmark";
            }

            if (landmarkExitInfoRepository.findByStationNameAndExitGateAndLandmark(landmarkExitInfoDto.getStationName(), landmarkExitInfoDto.getExitGate(), landmarkExitInfoDto.getLandmark()).isPresent()) {
                bindingResult.addError(new FieldError("landmarkExitInfoDto", "landmark", "Landmark already exists."));
                return "landmarks/create_landmark";
            }

            landmarkExitInfo.setStationName(landmarkExitInfoDto.getStationName());
            landmarkExitInfo.setExitGate(landmarkExitInfoDto.getExitGate());
            landmarkExitInfo.setLandmark(landmarkExitInfoDto.getLandmark());
            landmarkExitInfoRepository.save(landmarkExitInfo);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/landmarks?englishName=" + landmarkExitInfoDto.getStationName();
        }


        return "redirect:/landmarks?englishName=" + landmarkExitInfoDto.getStationName();
    }

    @GetMapping("/remove")
    public String removeLandmark(@RequestParam long id, Model model) {
        LandmarkExitInfo landmarkExitInfo = null;
        try {
            landmarkExitInfo = landmarkExitInfoRepository.findById(id).orElse(null);
            if (landmarkExitInfo != null) {
                landmarkExitInfoRepository.delete(landmarkExitInfo);
                model.addAttribute("successMessage", "Landmark removed successfully.");
            } else {
                model.addAttribute("errorMessage", "Landmark not found.");
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            model.addAttribute("errorMessage", "Landmark cannot be removed due to foreign key constraint.");
        }

        if (landmarkExitInfo != null) {
            currentStation = landmarkExitInfo.getStationName();
            List<LandmarkExitInfo> landmarkInfoList = landmarkExitInfoRepository.findByStationName(landmarkExitInfo.getStationName());
            model.addAttribute("landmarkInfoList", landmarkInfoList);
            return "landmarks/index";
        } else {
            List<LandmarkExitInfo> landmarkInfoList = landmarkExitInfoRepository.findByStationName(currentStation);
            model.addAttribute("landmarkInfoList", landmarkInfoList);
            return "landmarks/index";
        }
    }
}
