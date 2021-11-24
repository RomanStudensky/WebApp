package ru.webApp.dao;

import ru.webApp.DBConnector;
import ru.webApp.models.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EmployeeDAO {

    public Employee show(int id) {
        return index().stream().filter(employee -> employee.getId() == id).findAny().orElse(new Employee());
    }

    public ArrayList<Employee> index() {
        ArrayList <Employee> employees = new ArrayList<>();

        try {
            Statement statement = DBConnector.getConnection().createStatement();
            String SQL = "SELECT * FROM employ";
            ResultSet resultSetEmploy = statement.executeQuery(SQL);
            while (resultSetEmploy.next()) {
                ResultSet resultSet;
                Employee employee = new Employee();
                int id = resultSetEmploy.getInt("emp_id");
                employee.setId(id);
                employee.setName(resultSetEmploy.getString("name"));
                employee.setSurname(resultSetEmploy.getString("surname"));
                employee.setFathername(resultSetEmploy.getString("fathername"));
                employee.setAddres(resultSetEmploy.getString("addres"));
                employee.setBirthd(resultSetEmploy.getString("birthd"));
                employee.setCatheg(resultSetEmploy.getString("catheg"));
                employee.setInn(resultSetEmploy.getString("inn"));

                statement = DBConnector.getConnection().createStatement();
                SQL = "SELECT academ.*  academ " +
                        "FROM empaca, academ " +
                        "WHERE " +
                        id +" = empaca.empid " +
                        "AND academ.id_academ = empaca.academ_id";
                resultSet = statement.executeQuery(SQL);
                ArrayList<AcademicSubject> academicSubs = new ArrayList<>();
                while (resultSet.next()) {
                    academicSubs.add(
                            new AcademicSubject(
                                    resultSet.getInt("id_academ"),
                                    resultSet.getString("academ_name")));
                }

                statement = DBConnector.getConnection().createStatement();
                SQL = "SELECT honor.*  honor " +
                        "FROM emphon, honor " +
                        "WHERE " +
                        id + " = emphon.id_emp " +
                        "AND honor.id_hon = emphon.id_hon";
                resultSet = statement.executeQuery(SQL);
                ArrayList<Honor> honors = new ArrayList<>();
                while (resultSet.next()) {
                    honors.add(
                            new Honor(
                                    resultSet.getInt("id_hon"),
                                    resultSet.getString("name_hon")));
                }

                statement = DBConnector.getConnection().createStatement();
                SQL = "SELECT contract.*  contract " +
                        "FROM employ, divcon, contract " +
                        "WHERE " +
                        id + " = employ.emp_id " +
                        "AND employ.id_div_contr = divcon.id_div_contr " +
                        "AND divcon.id_div_contr = contract.id_contr";

                resultSet = statement.executeQuery(SQL);
                resultSet.next();

                Statement statement1 = DBConnector.getConnection().createStatement();
                String SQL1 = "SELECT divcon.*  divcon " +
                        "FROM employ, divcon " +
                        "WHERE " +
                        id + " = employ.emp_id " +
                        "AND employ.id_div_contr = divcon.id_div_contr ";

                ResultSet resultSet1 = statement1.executeQuery(SQL1);
                if (resultSet1.next()) {
                    DivorceContract divorceContract = new DivorceContract(
                            resultSet.getInt("id_contr"),
                            resultSet1.getInt("id_div_contr"),
                            resultSet.getString("datebe"),
                            resultSet.getString("dateen"),
                            resultSet.getBoolean("resolu"),
                            resultSet1.getString("termin"));
                    employee.setDivorceContract(divorceContract);
                }
                statement = DBConnector.getConnection().createStatement();
                SQL = "SELECT contract.*  contract " +
                        "FROM employ, workco, contract " +
                        "WHERE " +
                        id + " = employ.emp_id " +
                        "AND employ.id_work_con = workco.id_work_contr " +
                        "AND workco.id_contr = contract.id_contr";

                resultSet = statement.executeQuery(SQL);
                resultSet.next();

                statement1 = DBConnector.getConnection().createStatement();
                SQL1 = "SELECT workco.*  workco " +
                        "FROM employ, workco " +
                        "WHERE " +
                        id + " = employ.emp_id " +
                        "AND employ.id_work_con = workco.id_work_contr";

                resultSet1 = statement1.executeQuery(SQL1);
                resultSet1.next();

                WorkContract workContract = new WorkContract(
                        resultSet.getInt("id_contr"),
                        resultSet1.getInt("id_work_contr"),
                        resultSet.getString("datebe"),
                        resultSet.getString("dateen"),
                        resultSet.getBoolean("resolu"),
                        resultSet1.getDouble("wage"),
                        resultSet1.getString("holida"),
                        resultSet1.getString("holida1"),
                        resultSet1.getString("post"),
                        resultSet1.getString("workda"),
                        resultSet1.getString("workda1"),
                        resultSet1.getString("cathed"));

                statement = DBConnector.getConnection().createStatement();
                SQL = "SELECT change_hol.*  change_hol " +
                        "FROM change_hol, workco, employ " +
                        "WHERE " +
                        id + " = employ.emp_id " +
                        "AND employ.id_work_con = workco.id_work_contr " +
                        "AND change_hol.id_work_con3 = workco.id_work_contr";
                resultSet = statement.executeQuery(SQL);

                ArrayList<ChangeHolidayContract> changeHolidayContracts = new ArrayList<>();
                while (resultSet.next()) {
                    statement1 = DBConnector.getConnection().createStatement();
                    SQL = "SELECT contract.*  contract " +
                            "FROM contract " +
                            "WHERE " + resultSet.getInt("id_contr") + " = contract.id_contr";

                    resultSet1 = statement1.executeQuery(SQL);
                    resultSet1.next();
                    changeHolidayContracts.add(
                            new ChangeHolidayContract(
                                    resultSet1.getInt("id_contr"),
                                    resultSet.getInt("id_chang_hol"),
                                    resultSet1.getString("datebe"),
                                    resultSet1.getString("dateen"),
                                    resultSet1.getBoolean("resolu"),
                                    resultSet.getString("newbeg"),
                                    resultSet.getString("newend")
                            ));
                }

                statement = DBConnector.getConnection().createStatement();
                SQL = "SELECT change_post.*  change_post " +
                        "FROM change_post, workco, employ " +
                        "WHERE " +
                        id + " = employ.emp_id " +
                        "AND employ.id_work_con = workco.id_work_contr " +
                        "AND change_post.id_work_con2 = workco.id_work_contr";
                resultSet = statement.executeQuery(SQL);
                workContract.setChangeHolidayContracts(changeHolidayContracts);

                ArrayList<ChangePostContract> changePostContracts = new ArrayList<>();
                while (resultSet.next()) {
                    statement1 = DBConnector.getConnection().createStatement();
                    SQL = "SELECT contract.*  contract " +
                            "FROM contract, employ " +
                            "WHERE " +
                            id + " = employ.emp_id " +
                            "AND " + resultSet.getInt("id_contr") + " = contract.id_contr";

                    resultSet1 = statement1.executeQuery(SQL);
                    resultSet1.next();
                    changePostContracts.add(
                            new ChangePostContract(
                                    resultSet1.getInt("id_contr"),
                                    resultSet.getInt("id_chang_post"),
                                    resultSet1.getString("datebe"),
                                    resultSet1.getString("dateen"),
                                    resultSet1.getBoolean("resolu"),
                                    resultSet.getString("newpos")
                            ));
                }

                statement1 = DBConnector.getConnection().createStatement();
                SQL1 = "SELECT * " +
                        "FROM workreccard " +
                        "WHERE " +
                         id + " = workreccard.employ_id1 ";

                resultSet1 = statement1.executeQuery(SQL1);
                resultSet1.next();
                int idWorkRecCard = resultSet1.getInt("id_work_rec_card");

                WorkRecordCard workRecordCard = new WorkRecordCard(
                        idWorkRecCard,
                        resultSet1.getInt("number"),
                        resultSet1.getString("date1"),
                        employee);

                ArrayList<WorkRecord> workRecords = new ArrayList<>();
                while (resultSet.next()) {
                    statement = DBConnector.getConnection().createStatement();
                    SQL = "SELECT * " +
                            "FROM workrec " +
                            "WHERE " +
                            idWorkRecCard + " = id_work_rec_card";
                    resultSet = statement.executeQuery(SQL);
                    resultSet.next();
                    workRecords.add(
                            new WorkRecord(
                                    resultSet.getInt("id_workrec"),
                                    resultSet.getString("date"),
                                    resultSet.getString("title")
                            ));
                }

                workRecordCard.setEmploymentRecords(workRecords);
                workContract.setChangePostContracts(changePostContracts);

                employee.setWorkRecordCard(workRecordCard);
                employee.setWorkContract(workContract);
                employee.setAcademicSubjects(academicSubs);
                employee.setHonors(honors);

                employees.add(employee);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employees;
    }
}
