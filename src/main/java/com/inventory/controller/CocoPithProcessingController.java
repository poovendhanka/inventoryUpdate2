package com.inventory.controller;

import com.inventory.model.CocoPithProcessing;
import com.inventory.service.CocoPithProcessingService;
import com.inventory.service.PithStockService;
import com.inventory.service.LowECPithStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cocopith")
@RequiredArgsConstructor
public class CocoPithProcessingController extends BaseController {

    private final CocoPithProcessingService cocoPithProcessingService;
    private final PithStockService pithStockService;
    private final LowECPithStockService lowECPithStockService;

    @GetMapping
    public String showCocoPithPage(Model model) {
        model.addAttribute("activeTab", "cocopith");
        model.addAttribute("processing", new CocoPithProcessing());
        model.addAttribute("regularPithStock", pithStockService.getCurrentStock());
        model.addAttribute("lowECPithStock", lowECPithStockService.getCurrentStock());
        model.addAttribute("recentProcessings",
                cocoPithProcessingService.getRecentProcessings());
        return getViewPath("cocopith/index");
    }

    @PostMapping("/process")
    public String processPith(@ModelAttribute CocoPithProcessing processing,
            RedirectAttributes redirectAttributes) {
        try {
            cocoPithProcessingService.processPith(processing);
            redirectAttributes.addFlashAttribute("success",
                    "Successfully processed pith");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Failed to process pith: " + e.getMessage());
        }
        return "redirect:/cocopith";
    }
}