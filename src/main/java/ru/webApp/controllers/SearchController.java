package ru.webApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.webApp.dao.SearchForInformation;
import ru.webApp.dto.SearchDTO;
import ru.webApp.models.Employee;

import java.util.ArrayList;


@Controller
@RequestMapping("/search")
public class SearchController {

    private final SearchForInformation searchForInformation;

    @Autowired
    public SearchController(SearchForInformation searchForInformation) {
        this.searchForInformation = searchForInformation;
    }

    @GetMapping("/search")
    public String searchEmpCathExp() {
        return "empDep/search";
    }

    @GetMapping("/searchEmpCathExp")
    public String searchEmpCathExp(Model model) {
        model.addAttribute("employees", new ArrayList<Employee>());
        model.addAttribute("search", new SearchDTO());
        return "empDep/search/searchEmpCathExp";
    }

    @GetMapping("/searchEmpCathExpi")
    public String searchEmpCathExp(@ModelAttribute("search") SearchDTO search,
                                   Model model) {
        model.addAttribute("employees", searchForInformation.searchEmpCathExp(search.getSearch()));
        return "empDep/search/searchEmpCathExp";
    }

    @GetMapping("/searchMedNagCath")
    public String searchMedNagCath(Model model) {
        model.addAttribute("nag", new Double(0));
        model.addAttribute("search", new SearchDTO());
        return "empDep/search/searchMedNagCath";
    }

    @GetMapping("/searchMedNagCathi")
    public String searchMedNagCath(@ModelAttribute("search") SearchDTO search,
                                   Model model) {
        model.addAttribute("nag", searchForInformation.searchMedNagCath(search.getSearch()));
        return "empDep/search/searchMedNagCath";
    }

    @GetMapping("/searchCountEmpCath")
    public String searchCountEmpCath(Model model) {
        model.addAttribute("cath", searchForInformation.searchCountEmpCath());
        return "empDep/search/searchCountEmpCath";
    }

    @GetMapping("/searchHon")
    public String searchHon(Model model) {
        model.addAttribute("employees", searchForInformation.searchHon());
        return "empDep/search/searchHon";
    }


    @GetMapping("/searchExpired")
    public String searchExpired(Model model) {
        model.addAttribute("employees", searchForInformation.searchExpired());
        return "empDep/search/searchExpired";
    }

    @GetMapping("/searchHolEmp")
    public String searchHolEmp(Model model) {
        model.addAttribute("employees", searchForInformation.searchHolEmp());
        return "empDep/search/searchHolEmp";
    }

    @GetMapping("/searchEmpAcadSubj")
    public String searchEmpAcadSubj(Model model) {
        model.addAttribute("employees", new ArrayList<Employee>());
        model.addAttribute("search", new SearchDTO());
        return "empDep/search/searchEmpAcadSubj";
    }

    @GetMapping("/searchEmpAcadSubji")
    public String searchEmpAcadSubji(@ModelAttribute("search") SearchDTO search,
                                    Model model) {
        model.addAttribute("employees", searchForInformation.searchEmpAcadSubj(search.getSearch()));
        return "empDep/search/searchEmpAcadSubj";
    }

    @GetMapping("/searchOldEmp")
    public String searchOldEmp(Model model) {
        model.addAttribute("employees", searchForInformation.searchOldEmp());
        return "empDep/search/searchOldEmp";
    }

}
