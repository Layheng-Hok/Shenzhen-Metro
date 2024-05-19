package com.sustech.cs307.project2.ChineseSubwaySystem.controller;

import com.sustech.cs307.project2.ChineseSubwaySystem.model.BusExitInfo;
import com.sustech.cs307.project2.ChineseSubwaySystem.repository.BusExitInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/buses")
public class BusController {
    @Autowired
    private BusExitInfoRepository busExitInfoRepository;

    @GetMapping({"", "/"})
    public String showBusesListPage(@RequestParam("englishName") String stationName, Model model) {
        List<BusExitInfo> busInfoList = busExitInfoRepository.findByStationName(stationName);
        model.addAttribute("busInfoList", busInfoList);
        return "buses/index";
    }
}
