package ru.webApp.dao;

import org.springframework.stereotype.Component;
import org.thymeleaf.util.DateUtils;
import ru.webApp.models.AcademicSubject;
import ru.webApp.models.ChangeHolidayContract;
import ru.webApp.models.Employee;
import ru.webApp.models.Honor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class SearchForInformation {

    EmployeeDAO employeeDAO = new EmployeeDAO();

    public ArrayList<Employee> searchEmpCathExp(String cath) {
        ArrayList<Employee> employees = employeeDAO.index();
        return employeeDAO.index().stream().filter(
                emp -> {
                    String post;
                    if (emp.getWorkContract().getChangePostContracts().isEmpty()) {
                        post = emp.getWorkContract().getPost();
                    } else {
                        post = emp.getWorkContract().getChangePostContracts().stream().
                                sorted(Comparator.comparing(o -> new Date(o.getDateBegin()))).findAny().get().getNewPost();
                    }
                    if (post.equals("Преподаватель"))
                        return true;
                    else
                        return false;
                }).collect(Collectors.toCollection(ArrayList::new));
    }

    public Double searchMedNagCath(String cath) {
        ArrayList<Employee> employees = employeeDAO.index().stream().filter(
                emp -> emp.getWorkContract().getCathedra().equals(cath)
                        && emp.getCatheg().equals("Ассистент"))
                .collect(Collectors.toCollection(ArrayList::new));
        AtomicReference<Double> i = new AtomicReference<>(0.0D);

        employees.forEach(employee -> {
            Calendar c=Calendar.getInstance();
            c.set(Calendar.DAY_OF_MONTH, 1);
            c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(employee.getWorkContract().getWorkdayStart().substring(0,2)));
            c.set(Calendar.MINUTE, Integer.parseInt(employee.getWorkContract().getWorkdayStart().substring(3,5)));
            Date d1=c.getTime();
            Calendar c1=Calendar.getInstance();
            c1.set(Calendar.DAY_OF_MONTH, 1);
            c1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(employee.getWorkContract().getWorkdayFinish().substring(0,2)));
            c1.set(Calendar.MINUTE, Integer.parseInt(employee.getWorkContract().getWorkdayFinish().substring(3,5)));
            Date d2=c1.getTime();
            long dt=d2.getTime()-d1.getTime();
            i.updateAndGet(v -> v + (double)dt);
        });

        if (i.get() == 0.0)
            return 0.0;
        else
            return (i.get() / employees.size())/1000/60/60*5*20*6;
    }

    public HashMap<String, Integer> searchCountEmpCath() {
        HashSet<String> cath = new HashSet<>();
        ArrayList<Employee> employees = employeeDAO.index();
        employees.forEach(employee -> cath.add(employee.getWorkContract().getCathedra()));

        HashMap<String, Integer> hashMap = new HashMap<>();
        cath.forEach(c -> {
            hashMap.put(c, employees.stream().filter(employee ->
                     employee.getWorkContract().getCathedra().equals(c)).toArray().length);
        });

        return hashMap;
    }

    public ArrayList<Employee> searchHon() {
        ArrayList<Employee> employees = employeeDAO.index();
        ArrayList<Employee> newEmp = new ArrayList<>();
        for (int i = 0; i < employees.size(); i++) {
            for (Honor honor : employees.get(i).getHonors()) {
                if (honor.getName().equals("\"За оборону Ленинграда\""))
                    newEmp.add(employees.get(i));
            }
        }


        return newEmp;
    }

    public ArrayList<Employee> searchExpired() {
        return employeeDAO.index().stream().filter(employee ->
        {
            try {
                return new Date().before(new SimpleDateFormat("YYYY-MM-dd").parse(employee.getWorkContract().getDateEnd()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return false;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Employee> searchHolEmp() {

        ArrayList<Employee> newEmployees = employeeDAO.index().stream().filter(employee ->
                {
                    try {
                        if (employee.getWorkContract().getChangeHolidayContracts().isEmpty()) {
                            return (
                                    new Date().compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(employee.getWorkContract().getHolidayBegin())) >= 1 &&
                                            new Date().compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(employee.getWorkContract().getHolidayEnd())) <= -1);
                        } else {
                            ArrayList<ChangeHolidayContract> changeHolidayContracts = employee.getWorkContract().getChangeHolidayContracts();
                            return (
                                    new Date().compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(changeHolidayContracts.get(changeHolidayContracts.size() - 1).getNewBeginHoliday())) >= 1 &&
                                            new Date().compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(changeHolidayContracts.get(changeHolidayContracts.size() - 1).getNewEndHoliday())) <= -1);

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
        ).collect(Collectors.toCollection(ArrayList::new));
        return newEmployees;
    }

    public ArrayList<Employee> searchEmpAcadSubj(String academSubj) {
        ArrayList<Employee> employees = employeeDAO.index();
        ArrayList<Employee> newEmp = new ArrayList<>();
        for (int i = 0; i < employees.size(); i++) {
            for (AcademicSubject academicSubject : employees.get(i).getAcademicSubjects()) {
                if (academicSubject.getName().equals(academSubj)) {
                    newEmp.add(employees.get(i));
                    break;
                }
            }
        }
        return newEmp;
    }

    public ArrayList<Employee> searchOldEmp() {
        ArrayList<Employee> employees = employeeDAO.index();
        long max = 0;
        for (Employee employee : employees) {
            Calendar c=Calendar.getInstance();
            c.set(Calendar.YEAR, Integer.parseInt(employee.getWorkContract().getDateBegin().substring(0,4)));
            c.set(Calendar.MONTH, Integer.parseInt(employee.getWorkContract().getDateBegin().substring(5,7))-1);
            c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(employee.getWorkContract().getDateBegin().substring(8,10)));
            Date d1 = c.getTime();
            Date d2 = new Date();

            long dt = d2.getTime() - d1.getTime();
            if (dt > max)
                max = dt;
        }
        ArrayList<Employee> newEmployees = new ArrayList<>();

        for (Employee employee : employees) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, Integer.parseInt(employee.getWorkContract().getDateBegin().substring(0,4)));
            c.set(Calendar.MONTH, Integer.parseInt(employee.getWorkContract().getDateBegin().substring(5,7))-1);
            c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(employee.getWorkContract().getDateBegin().substring(8,10)));
            Date d1 = c.getTime();
            Date d2 = new Date();

            long dt = d2.getTime()  - d1.getTime();
            if (dt == max)
               newEmployees.add(employee);
        }


        return newEmployees;
    }
}
