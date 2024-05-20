package com.sustech.cs307.project2.ChineseSubwaySystem.controller;

import com.sustech.cs307.project2.ChineseSubwaySystem.object.LandmarkExitInfo;
import com.sustech.cs307.project2.ChineseSubwaySystem.repository.LandmarkExitInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/landmarks")
public class LandmarkController {
    @Autowired
    private LandmarkExitInfoRepository landmarkExitInfoRepository;

    @GetMapping({"", "/"})
    public String showLandmarksListPage(@RequestParam("englishName") String stationName, Model model) {
        List<LandmarkExitInfo> landmarkInfoList = landmarkExitInfoRepository.findByStationName(stationName);
        model.addAttribute("landmarkInfoList", landmarkInfoList);
        return "landmarks/index";
    }
}
