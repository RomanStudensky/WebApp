package ru.webApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.webApp.dao.EmployeeDepartment;
import ru.webApp.dto.AcademicSubjectsDTO;
import ru.webApp.dto.HonorsDTO;
import ru.webApp.dto.EmployeeFormDTO;
import ru.webApp.dto.NewEmployeeDTO;
import ru.webApp.models.ChangeHolidayContract;
import ru.webApp.models.ChangePostContract;
import ru.webApp.models.DivorceContract;
import ru.webApp.models.Employee;

import javax.validation.Valid;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

@Controller
@RequestMapping("/empDep")
public class EmpDepController {

    private final EmployeeDepartment employeeDepartment;

    @Autowired
    public EmpDepController(EmployeeDepartment employeeDepartment) {
        this.employeeDepartment = employeeDepartment;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("employees", employeeDepartment.getEmployeeDAO().index());
        return "/index";
    }

    @GetMapping("/showEmploy/{id}")
    public String showEmployee(@PathVariable("id") int id, Model model) throws ParseException {
        Employee employee = employeeDepartment.getEmployeeDAO().show(id);

        EmployeeFormDTO employeeForm = new EmployeeFormDTO();
        employeeForm.setName(employee.getName());
        employeeForm.setFathername(employee.getFathername());
        employeeForm.setSurname(employee.getSurname());
        employeeForm.setAddres(employee.getAddres());
        employeeForm.setBirthd(employee.getBirthd());
        employeeForm.setId(employee.getId());
        employeeForm.setInn(employee.getInn());
        employeeForm.setCatheg(employee.getCatheg());
        employeeForm.setAcademicSubjects(employee.getAcademicSubjects());
        employeeForm.setHonors(employee.getHonors());
        employeeForm.setCathedra(employee.getWorkContract().getCathedra());
        employeeForm.setWorkdayStart(employee.getWorkContract().getWorkdayStart());
        employeeForm.setWorkdayFinish(employee.getWorkContract().getWorkdayFinish());
        employeeForm.setWage(employee.getWorkContract().getWage());

        if (!new Date().before(new SimpleDateFormat("yyyy-MM-dd").parse(employee.getWorkContract().getDateEnd())))
            employeeForm.setStatus("Истёк срок контракта");
        else if (employee.getDivorceContract() != null)
            employeeForm.setStatus("Уволен");
        else
            employeeForm.setStatus("Работает");


        if (employee.getWorkContract().getChangePostContracts().isEmpty()) {
            employeeForm.setPost(employee.getWorkContract().getPost());
        } else {
            employeeForm.setPost(employee.getWorkContract().getChangePostContracts().stream().
                    sorted(Comparator.comparing(o -> new Date(o.getDateBegin()))).findAny().get().getNewPost());
        }

        if (employee.getWorkContract().getChangeHolidayContracts().isEmpty()) {
            employeeForm.setHolidayBegin(employee.getWorkContract().getHolidayBegin());
            employeeForm.setHolidayEnd(employee.getWorkContract().getHolidayEnd());
        } else {
            ChangeHolidayContract changeHolidayContract = employee.getWorkContract().getChangeHolidayContracts().stream().
                    sorted(Comparator.comparing(o -> new Date(o.getDateBegin()))).findAny().get();
            employeeForm.setHolidayBegin(changeHolidayContract.getNewBeginHoliday());
            employeeForm.setHolidayEnd(changeHolidayContract.getNewEndHoliday());

        }

        model.addAttribute("employee", employeeForm);
        model.addAttribute("work_co", employee.getWorkContract());
        model.addAttribute("div_co", employee.getDivorceContract());
        return "empDep/show";
    }

    @PostMapping("/empContrPlus/{id}")
    public String empContrPlus(@PathVariable("id") int id) {
        employeeDepartment.empContrPlus(id);
        return "redirect: /showEmploy/" + id;
    }

    @GetMapping("/div/{idEmp}")
    public String div(@PathVariable("idEmp") int id,
                      Model model) {
        model.addAttribute("id", id);
        model.addAttribute("div_con", new DivorceContract());
        return "empDep/action/div";
    }

    @GetMapping("/changePost/{idEmp}")
    public String changePost(@PathVariable("idEmp") int id,
                             Model model) {
        model.addAttribute("id", id);
        model.addAttribute("change_post", new ChangePostContract());
        return "empDep/action/changePost";
    }

    @GetMapping("/changeHoliday/{idEmp}")
    public String changeHoliday(@PathVariable("idEmp") int id,
                                Model model) {
        model.addAttribute("id", id);
        model.addAttribute("change_holiday", new ChangeHolidayContract());
        return "empDep/action/changeHoliday";
    }

    @GetMapping("showDivCo/{id}")
    public String showDivCo(@PathVariable("id") int id, Model model) {
//        model.addAttribute("div_co", employeeDepartment.show(id));
        return "empDep/show";
    }

    @GetMapping("showWorkCo/{id}")
    public String showWorkCo(@PathVariable("id") int id, Model model) {
//        model.addAttribute("work_co", employeeDepartment.show(id));
        return "empDep/show";
    }

    @GetMapping("showCHCo/{id}")
    public String showCHCo(@PathVariable("id") int id, Model model) {
//        model.addAttribute("ch_co", employeeDepartment.show(id));
        return "empDep/show";
    }

    @GetMapping("showCPCo/{id}")
    public String showCPCo(@PathVariable("id") int id, Model model) {
//        model.addAttribute("cp_co", employeeDepartment.show(id));
        return "empDep/show";
    }

    @GetMapping("showWorkRecCard/{id}")
    public String showWorkRecCard(@PathVariable("id") int id, Model model) {
//        model.addAttribute("cp_co", employeeDepartment.show(id));
        return "empDep/show";
    }

    @GetMapping("/addEmployee")
    public String newEmployee(Model model) {
        model.addAttribute("new_employee", new NewEmployeeDTO());
        model.addAttribute("academ_subs_dto", new AcademicSubjectsDTO(new ArrayList<>()));
        model.addAttribute("academ_subs", employeeDepartment.getAcademicSubjects());
        model.addAttribute("honors_dto", new HonorsDTO(new ArrayList<>()));
        model.addAttribute("honors", employeeDepartment.getHonors());
        return "empDep/action/employed";
    }

    @PostMapping("/add")
    public String employed(@ModelAttribute("academ_subs_dto") AcademicSubjectsDTO academ_subs_dto,
                           @ModelAttribute("honors_dto") HonorsDTO honors_dto,
                           Model model,
                           @ModelAttribute("new_employee") @Valid NewEmployeeDTO new_employee,
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("academ_subs", employeeDepartment.getAcademicSubjects());
            model.addAttribute("honors", employeeDepartment.getHonors());
            return "empDep/action/employed";
        }

       employeeDepartment.employed(new_employee, academ_subs_dto, honors_dto);

        return "redirect:/empDep";
    }

    @PostMapping("/divorce/{id}")
    public String employed(@PathVariable("id") int id,
                           @ModelAttribute("div_con") DivorceContract div_con) throws SQLException {
        employeeDepartment.divorce(id, div_con.getTerminateEvent());

        return "redirect:/empDep";
    }

    @PostMapping("/changeHoliday/{id}")
    public String changeHol(@PathVariable("id") int id,
                            @ModelAttribute("change_holiday") ChangeHolidayContract change) throws SQLException {

        employeeDepartment.changeHoliday(id, change.getNewBeginHoliday(), change.getNewEndHoliday());

        return "redirect:/empDep";
    }


    @PostMapping("/changePost/{id}")
    public String changePost(@PathVariable("id") int id,
                             @ModelAttribute("change_post") ChangePostContract change) throws SQLException {
        employeeDepartment.changePost(id, change.getNewPost());

        return "redirect:/empDep";
    }


//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("person") @Valid Employee person, BindingResult bindingResult,
//                         @PathVariable("id") int id) {
//        if (bindingResult.hasErrors())
//            return "people/edit";
//
//        employeeDepartment.update(id, person);
//        return "redirect:/people";
//    }

//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") int id) {
//        employeeDepartment.divorce(id);
//        return "redirect:/empDep";
//    }
}