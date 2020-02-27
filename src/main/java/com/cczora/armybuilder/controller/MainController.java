package com.cczora.armybuilder.controller;

import com.cczora.armybuilder.config.exception.DuplicateUsernameException;
import com.cczora.armybuilder.config.jwt.JwtUtil;
import com.cczora.armybuilder.models.entity.Account;
import com.cczora.armybuilder.models.entity.Army;
import com.cczora.armybuilder.service.ArmyService;
import com.cczora.armybuilder.service.MyUserPrincipal;
import com.cczora.armybuilder.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@Controller
@CrossOrigin
public class MainController {

    private final UserService userService;
    private final ArmyService armyService;
    private final JwtUtil jwt;

    public MainController(UserService userService, ArmyService armyService, JwtUtil jwt) {
        this.userService = userService;
        this.armyService = armyService;
        this.jwt = jwt;
    }

    @GetMapping("/")
    public String landingPage(MyUserPrincipal principal) {
        if (principal.getUser() != null) {
            return "redirect:/home";
        }
        return "newLandingPage";
    }

    @GetMapping("/login")
    public String login() {
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
    public String gotoUserHomePage(Model model, HttpServletRequest request) {
        //TODO: move this to the AuthenticationService once it works: return the username?
        Optional<String> authHeader = Optional.ofNullable(request.getHeader("Authentication"));
        String jwtToken = null;
        if (authHeader.isPresent() && authHeader.get().startsWith("Bearer ")) {
            jwtToken = authHeader.get().substring(7);
        }
        Optional<Object> expired = Optional.ofNullable(request.getAttribute("expired"));
        if (expired.isPresent() && expired.get().equals(Boolean.TRUE)) {
            return "loggedOut";
        }
        String username = jwt.extractUsername(jwtToken);
        model.addAttribute("username", username);
        model.addAttribute("factions", armyService.getAllFactions());
        model.addAttribute("armies", armyService.getArmiesByUsername(username));
        return "armyHome";
    }

    @GetMapping("/armybuilder/{armyId}")
    public String gotoArmyBuilderHome(MyUserPrincipal principal, Model model, @PathVariable UUID armyId) {
        Army army = armyService.getArmyById(armyId);

        model.addAttribute("army", army);
        if (principal.getUsername() == null) {
            return "loggedOut";
        }
        model.addAttribute("username", principal.getUsername());
        model.addAttribute("factions", armyService.getAllFactions());
        return "armybuilder";
    }

}
