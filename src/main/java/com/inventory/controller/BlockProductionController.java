package com.inventory.controller;

import com.inventory.model.BlockProduction;
import com.inventory.model.PithType;
import com.inventory.service.BlockProductionService;
import com.inventory.service.PithStockService;

import jakarta.validation.Valid;

import com.inventory.service.LowECPithStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/blocks")
@RequiredArgsConstructor
public class BlockProductionController extends BaseController {

    private final BlockProductionService blockProductionService;
    private final PithStockService pithStockService;
    private final LowECPithStockService lowECPithStockService;

    @GetMapping
    public String showBlockProductionPage(Model model) {
        model.addAttribute("activeTab", "blocks");
        model.addAttribute("production", new BlockProduction());
        model.addAttribute("regularPithStock", pithStockService.getCurrentStock());
        model.addAttribute("lowECPithStock", lowECPithStockService.getCurrentStock());
        model.addAttribute("recentProductions",
                blockProductionService.getRecentProductions());
        return getViewPath("blocks/index");
    }

    @PostMapping("/create")
    public String createBlocks(@Valid @ModelAttribute BlockProduction production,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return getViewPath("blocks/index");
        }

        try {
            blockProductionService.createBlocks(production);
            redirectAttributes.addFlashAttribute("success",
                    "Successfully created blocks");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Failed to create blocks: " + e.getMessage());
        }
        return "redirect:/blocks";
    }
}