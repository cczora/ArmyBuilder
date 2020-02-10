package com.cczora.armybuilder.controller;

import com.cczora.armybuilder.config.exception.DuplicateUsernameException;
import com.cczora.armybuilder.models.entity.Account;
import com.cczora.armybuilder.models.entity.Army;
import com.cczora.armybuilder.models.entity.Detachment;
import com.cczora.armybuilder.service.ArmyService;
import com.cczora.armybuilder.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.UUID;

@Controller
@CrossOrigin
public class MainController {

    private final UserService userService;
    private final ArmyService armyService;

    public MainController(UserService userService, ArmyService armyService) {
        this.userService = userService;
        this.armyService = armyService;
    }
    
    @GetMapping("/")
    public String landingPage(Principal principal) {
        if(principal != null) {
            return "redirect:/home";
        }
        return "newLandingPage";
    }

    @GetMapping("/login")
    public String login(Account user) {
        return "login";
    }
    
    @GetMapping("/signup")
    public String signUp() {
        return "signup";
    }
    
    @PostMapping("/signup")
    public String signup(Account user) throws DuplicateUsernameException {
        userService.add(user);
        return "redirect:/";
    }

    @GetMapping("/home")
    public String gotoUserHomePage(Model model, Principal principal) {
        if(principal.getName() == null) {
            return "loggedOut";
        }
        model.addAttribute("username", principal.getName());
        model.addAttribute("factions", armyService.getAllFactions());
        model.addAttribute("armies", armyService.getArmiesByUsername(principal.getName()));
        return "armyHome";
    }

    @GetMapping("/armybuilder/{armyId}")
    public String gotoArmyBuilderHome(Principal principal, Model model, @PathVariable UUID armyId) {
        Army army = armyService.getArmyById(armyId);
        
        model.addAttribute("army", army);
        if(principal.getName() == null) {
            return "loggedOut";
        }
        model.addAttribute("username", principal.getName());
        model.addAttribute("factions", armyService.getAllFactions());
        return "armybuilder";
    }

}
