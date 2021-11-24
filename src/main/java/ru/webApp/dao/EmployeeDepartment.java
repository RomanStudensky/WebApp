package ru.webApp.dao;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.webApp.DBConnector;
import ru.webApp.dto.AcademicSubjectsDTO;
import ru.webApp.dto.HonorsDTO;
import ru.webApp.dto.NewEmployeeDTO;
import ru.webApp.models.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@Component
public class EmployeeDepartment {

    private Statement statement;
    @Getter
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    public void employed(NewEmployeeDTO newEmployee,
                         AcademicSubjectsDTO academicSubjectsDTO,
                         HonorsDTO honorsDTO) {
        try {
            Statement statement = DBConnector.getConnection().createStatement();
            String SQL;

            SQL = "INSERT INTO employ (surname, name, fathername, birthd, addres, catheg, inn) VALUES('"
                    + newEmployee.getSurname() +
                    "', '" + newEmployee.getName() + "', '" + newEmployee.getFathername()  +
                    "', '" + newEmployee.getBirthd() + "', '" + newEmployee.getAddres() + "', '"
                    + newEmployee.getAddres() + "', '" +
                    newEmployee.getInn() + "')";
            statement.executeUpdate(SQL);

            statement = DBConnector.getConnection().createStatement();
            SQL = "SELECT emp_id FROM employ ORDER BY emp_id DESC";
            ResultSet resultSet = statement.executeQuery(SQL);
            resultSet.next();
            int id = resultSet.getInt("emp_id");

            for (Integer integer : academicSubjectsDTO.getAcadem_subs_dto()) {
                SQL = "INSERT INTO empaca (academ_id, empid) VALUES('" +
                        integer + "', '" + id + "')";
                statement.executeUpdate(SQL);
            }

            for (Integer integer : honorsDTO.getHonors_dto()) {
                SQL = "INSERT INTO emphon (id_hon, id_emp) VALUES('" +
                        integer + "', '" + id + "')";
                statement.executeUpdate(SQL);
            }

            SQL = "INSERT INTO contract (datebe, dateen, resolu, employ_id) VALUES('" +
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "', '" +
                    LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())).plusMonths(6) + "', '" +
                    "true"  + "', '" +
                    id +"')";
            statement.executeUpdate(SQL);

            statement = DBConnector.getConnection().createStatement();
            SQL = "SELECT id_contr FROM contract ORDER BY id_contr DESC";
            ResultSet resultSet2 = statement.executeQuery(SQL);
            resultSet2.next();
            int idContract = resultSet2.getInt("id_contr");

            SQL = "INSERT INTO workco (wage, holida, holida1, post, workda, workda1, cathed, id_contr) VALUES('" +
                    newEmployee.getWage() + "', '" +
                    newEmployee.getHolidayBegin() + "', '" +
                    newEmployee.getHolidayEnd()  + "', '" +
                    newEmployee.getPost() + "', '" +
                    newEmployee.getWorkdayStart() + "', '" +
                    newEmployee.getWorkdayFinish() + "', '" +
                    newEmployee.getCathedra() + "', '" +
                    idContract + "')";
            statement.executeUpdate(SQL);

            statement = DBConnector.getConnection().createStatement();
            SQL = "SELECT id_work_contr FROM workco ORDER BY id_work_contr DESC";
            ResultSet resultSet3 = statement.executeQuery(SQL);
            resultSet3.next();
            int idWorkContr = resultSet3.getInt("id_work_contr");

            String number = new String();
            for (int i = 0; i < 7; i++) {
                number+= new Random().nextInt(10);
            }

            SQL = "INSERT INTO workreccard (number , date1, employ_id1) VALUES('" +
                    number + "', '" +
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "', '" +
                    id  + "')";
            statement.executeUpdate(SQL);

            statement = DBConnector.getConnection().createStatement();
            SQL = "SELECT id_work_rec_card FROM workreccard ORDER BY id_work_rec_card DESC";
            ResultSet resultSet1 = statement.executeQuery(SQL);
            resultSet1.next();
            int idWorkRecCard = resultSet1.getInt("id_work_rec_card");

            SQL = "UPDATE employ SET " +
                    "id_work_con = " + idWorkContr + ", " +
                    "id_work_rec_card = " + idWorkRecCard  +
                " WHERE employ.emp_id = " + id;
            statement.executeUpdate(SQL);

            SQL = "INSERT INTO workrec (date, id_work_rec_card, title) VALUES('" +
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "', '" +
                    idWorkRecCard  + "', " +
                    "'Принят на должность " + newEmployee.getPost() +
                    "')";

            statement.executeUpdate(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void empContrPlus(int empId) {
        try {
            String SQL = "UPDATE contract " +
                    "SET dateen = " +
                    LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())).plusMonths(6) +
                    " WHERE employ_id = " + empId;
            statement.executeUpdate(SQL);
        } catch (SQLException ignored) {
        }
    }

    public void divorce(int idEmployee, String termEvent) throws SQLException {
        statement = DBConnector.getConnection().createStatement();
        String SQL = "SELECT id_work_rec_card " +
                "FROM employ " +
                "WHERE " + idEmployee +" = employ.emp_id";
        ResultSet resultSet = statement.executeQuery(SQL);
        resultSet.next();
        int idWorkRecCard = resultSet.getInt("id_work_rec_card");

        SQL = "SELECT workco.post " +
                "FROM employ, workco " +
                "WHERE workco.id_work_contr = employ.id_work_con";
        resultSet = statement.executeQuery(SQL);
        resultSet.next();

        SQL = "INSERT INTO workrec (date, id_work_rec_card, title) VALUES('" +
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "', '" +
                idWorkRecCard  + "', " +
                "'Уволен" +
                "')";
        statement.executeUpdate(SQL);

        SQL = "INSERT INTO contract (datebe, resolu, employ_id) VALUES('" +
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "', '" +
                "true"  + "', '" +
                idEmployee +"')";
        statement.executeUpdate(SQL);

        statement = DBConnector.getConnection().createStatement();
        SQL = "SELECT id_contr FROM contract ORDER BY id_contr DESC";
        resultSet = statement.executeQuery(SQL);
        resultSet.next();
        int idContract = resultSet.getInt("id_contr");

        SQL = "INSERT INTO divcon (termin, id_contr) VALUES('" +
                termEvent + "', '" +
                idContract + "')";
        statement.executeUpdate(SQL);


        SQL = "SELECT id_div_contr FROM divcon ORDER BY id_div_contr DESC";
        resultSet = statement.executeQuery(SQL);
        resultSet.next();
        int idDivContr = resultSet.getInt("id_div_contr");

        SQL = "UPDATE employ " +
                "SET id_div_contr = " + idDivContr +
                " WHERE employ.emp_id = " + idEmployee;
        statement.executeUpdate(SQL);
    }

    public void changeHoliday(int idEmployee, String newDateBegin, String newDateEnd) throws SQLException {

        statement = DBConnector.getConnection().createStatement();
        String SQL = "SELECT id_work_rec_card " +
                "FROM employ " +
                "WHERE " + idEmployee +" = employ.emp_id";
        ResultSet resultSet = statement.executeQuery(SQL);
        resultSet.next();
        int idWorkRecCard = resultSet.getInt("id_work_rec_card");


        SQL = "INSERT INTO workrec (date, id_work_rec_card, title) VALUES('" +
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "', '" +
                idWorkRecCard  + "', " +
                "'Измены сроки отпуска" +
                "')";
        statement.executeUpdate(SQL);


        SQL = "INSERT INTO contract (datebe, resolu, employ_id) VALUES('" +
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "', '" +
                "true"  + "', '" +
                idEmployee +"')";
        statement.executeUpdate(SQL);

        statement = DBConnector.getConnection().createStatement();
        SQL = "SELECT id_contr FROM contract ORDER BY id_contr DESC";
        resultSet = statement.executeQuery(SQL);
        resultSet.next();
        int idContract = resultSet.getInt("id_contr");

        statement = DBConnector.getConnection().createStatement();
        SQL = "SELECT id_work_con " +
                "FROM employ " +
                "WHERE " + idEmployee +" = employ.emp_id";
        resultSet = statement.executeQuery(SQL);
        resultSet.next();
        int idWorkCon = resultSet.getInt("id_work_con");

        SQL = "INSERT INTO change_hol (newbeg, newend, id_contr, id_work_con3) VALUES('" +
                newDateBegin + "', '" +
                newDateEnd + "', '" +
                idContract + "', '" +
                idWorkCon + "')";
        statement.executeUpdate(SQL);
    }

    public void changePost(int idEmployee, String newPost) throws SQLException {

        statement = DBConnector.getConnection().createStatement();
        String SQL = "SELECT id_work_rec_card " +
                "FROM employ " +
                "WHERE " + idEmployee +" = employ.emp_id";
        ResultSet resultSet = statement.executeQuery(SQL);
        resultSet.next();
        int idWorkRecCard = resultSet.getInt("id_work_rec_card");

        SQL = "INSERT INTO workrec (date, id_work_rec_card, title) VALUES('" +
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "', '" +
                idWorkRecCard  + "', " +
                "'Измены сроки отпуска" +
                "')";
        statement.executeUpdate(SQL);


        SQL = "INSERT INTO contract (datebe, resolu, employ_id) VALUES('" +
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "', '" +
                "true"  + "', '" +
                idEmployee +"')";
        statement.executeUpdate(SQL);

        statement = DBConnector.getConnection().createStatement();
        SQL = "SELECT id_contr FROM contract ORDER BY id_contr DESC";
        resultSet = statement.executeQuery(SQL);
        resultSet.next();
        int idContract = resultSet.getInt("id_contr");

        statement = DBConnector.getConnection().createStatement();
        SQL = "SELECT id_work_con " +
                "FROM employ " +
                "WHERE " + idEmployee +" = employ.emp_id";
        resultSet = statement.executeQuery(SQL);
        resultSet.next();
        int idWorkCon = resultSet.getInt("id_work_con");

        SQL = "INSERT INTO change_post (newpos, id_contr, id_work_con2) VALUES('" +
                newPost + "', '" +
                idContract + "', '" +
                idWorkCon + "')";
        statement.executeUpdate(SQL);
    }

    public ArrayList<AcademicSubject> getAcademicSubjects() {
        ArrayList<AcademicSubject> academicSubjects = new ArrayList<>();

        try {
            Statement statement = DBConnector.getConnection().createStatement();
            String SQL = "SELECT * FROM academ";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                AcademicSubject academicSubject = new AcademicSubject();
                academicSubject.setId(resultSet.getInt("id_academ"));
                academicSubject.setName(resultSet.getString("academ_name"));
                academicSubjects.add(academicSubject);
            }
        } catch (SQLException e) {
            System.out.println("e = " + e);
        }

        return academicSubjects;
    }

    public ArrayList<Honor> getHonors() {
        ArrayList<Honor> honors = new ArrayList<>();

        try {
            Statement statement = DBConnector.getConnection().createStatement();
            String SQL = "SELECT * FROM honor";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Honor honor = new Honor();
                honor.setId(resultSet.getInt("id_hon"));
                honor.setName(resultSet.getString("name_hon"));
                honors.add(honor);
            }
        } catch (SQLException e) {
            System.out.println("e = " + e);
        }
        return honors;
    }
}