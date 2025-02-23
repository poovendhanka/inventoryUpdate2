package com.inventory.controller;

import com.inventory.service.ProductionService;
import com.inventory.service.SaleService;
import com.inventory.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalDate;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class DashboardController extends BaseController {

    private final StockService stockService;
    private final ProductionService productionService;
    private final SaleService saleService;

    @GetMapping
    public String showDashboard(Model model) {
        model.addAttribute("activeTab", "dashboard");

        // Add stock levels
        model.addAttribute("regularPithStock", stockService.getCurrentPithStock());
        model.addAttribute("lowECPithStock", stockService.getCurrentLowECPithStock());
        model.addAttribute("whiteFiberStock", stockService.getCurrentWhiteFiberStock());
        model.addAttribute("brownFiberStock", stockService.getCurrentBrownFiberStock());
        model.addAttribute("blockStock", stockService.getCurrentBlockStock());

        // Add recent activities
        model.addAttribute("recentProductions",
                productionService.getRecentProductions(LocalDate.now()));
        model.addAttribute("recentSales",
                saleService.getRecentSales());

        return getViewPath("dashboard/index");
    }
}