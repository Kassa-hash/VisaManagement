package com.itu.visamanagement.controller;

import com.itu.visamanagement.dto.DemandeurDTO;
import com.itu.visamanagement.entity.Demandeur;
import com.itu.visamanagement.services.DemandeurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/demande")
public class DemandeurController {

    private final DemandeurService demandeurService;

    public DemandeurController(DemandeurService demandeurService) {
        this.demandeurService = demandeurService;
    }

    /**
     * GET /demande — Affiche le formulaire vide (étape 1)
     */
    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("demandeurDTO", new DemandeurDTO());
        model.addAllAttributes(demandeurService.getLookupData());
        return "demande-form";
    }

    /**
     * POST /demande — Traite la soumission du formulaire
     */
    @PostMapping
    public String submitForm(
            @Valid @ModelAttribute("demandeurDTO") DemandeurDTO dto,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAllAttributes(demandeurService.getLookupData());
            return "demande-form";
        }

        try {
            Demandeur demandeur = demandeurService.createDemandeur(dto);
            return "redirect:/demande/success?code=" + demandeur.getCode();
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la création de la demande: " + e.getMessage());
            model.addAllAttributes(demandeurService.getLookupData());
            return "demande-form";
        }
    }

    /**
     * GET /demande/success — Écran de confirmation
     */
    @GetMapping("/success")
    public String showSuccess(@RequestParam String code, Model model) {
        model.addAttribute("demandeurCode", code);
        return "demande-success";
    }
}
