package ru.webApp.dao;

import ru.webApp.DBConnector;
import ru.webApp.models.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ContractBuilder {

    public WorkContract buildWorkCo(int id) {
        return indexWorkCo().stream().filter(workContract -> workContract.getId() == id).findAny().orElse(new WorkContract());
    }

    public DivorceContract buildDivCo(int id) {
        return indexDivCon().stream().filter(divorceContract -> divorceContract.getId() == id).findAny().orElse(new DivorceContract());
    }

    public ArrayList<WorkContract> indexWorkCo() {
        ArrayList<WorkContract> workContracts = new ArrayList<>();

        try {
            Statement statement0 = DBConnector.getConnection().createStatement();
            String SQL0 = "SELECT contract.* " +
                    "FROM workco ";

            ResultSet resultSet0 = statement0.executeQuery(SQL0);
            while (resultSet0.next()) {

                int idWorkCo = resultSet0.getInt("id_work_contr");

                Statement statement = DBConnector.getConnection().createStatement();
                String SQL = "SELECT contract.*  contract " +
                        "FROM workco, contract " +
                        "WHERE " +
                        "AND " + idWorkCo + " = workco.id_work_contr " +
                        "AND workco.id_contr = contract.id_contr";

                ResultSet resultSet = statement.executeQuery(SQL);
                resultSet.next();

                WorkContract workContract = new WorkContract(
                        resultSet.getInt("id_contr"),
                        idWorkCo,
                        resultSet.getString("datebe"),
                        resultSet.getString("dateen"),
                        resultSet.getBoolean("resolu"),
                        resultSet0.getDouble("wage"),
                        resultSet0.getString("holida"),
                        resultSet0.getString("holida1"),
                        resultSet0.getString("post"),
                        resultSet0.getString("workda"),
                        resultSet0.getString("workda1"),
                        resultSet0.getString("cathed"));

                statement = DBConnector.getConnection().createStatement();
                SQL = "SELECT change_hol.*  change_hol " +
                        "FROM change_hol " +
                        "WHERE change_hol.id_work_con3 = " + idWorkCo;
                resultSet = statement.executeQuery(SQL);

                ArrayList<ChangeHolidayContract> changeHolidayContracts = new ArrayList<>();
                while (resultSet.next()) {
                    Statement statement1 = DBConnector.getConnection().createStatement();
                    SQL = "SELECT contract.*  contract " +
                            "FROM contract " +
                            "WHERE " + resultSet.getInt("id_contr") + " = contract.id_contr";

                    ResultSet resultSet1 = statement1.executeQuery(SQL);
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
                        "FROM change_post " +
                        "WHERE change_post.id_work_con2 = " + idWorkCo;
                resultSet = statement.executeQuery(SQL);
                workContract.setChangeHolidayContracts(changeHolidayContracts);

                ArrayList<ChangePostContract> changePostContracts = new ArrayList<>();
                while (resultSet.next()) {
                    Statement statement1 = DBConnector.getConnection().createStatement();
                    SQL = "SELECT contract.*  contract " +
                            "FROM contract " +
                            "WHERE " +
                            resultSet.getInt("id_contr") + " = contract.id_contr";

                    ResultSet resultSet1 = statement1.executeQuery(SQL);
                    resultSet1.next();
                    changePostContracts.add(
                            new ChangePostContract(
                                    resultSet1.getInt("id_contr"),
                                    resultSet.getInt("id_chang_hol"),
                                    resultSet1.getString("datebe"),
                                    resultSet1.getString("dateen"),
                                    resultSet1.getBoolean("resolu"),
                                    resultSet.getString("newpos")
                            )
                    );
                }
            }
        } catch (SQLException e) {

        }
        return workContracts;
    }

    public ArrayList<DivorceContract> indexDivCon() {
        ArrayList<DivorceContract> divorceContracts = new ArrayList<>();

        try {
            Statement statement0 = DBConnector.getConnection().createStatement();
            String SQL0 = "SELECT divcon.* " +
                    "FROM divcon ";

            ResultSet resultSet0 = statement0.executeQuery(SQL0);
            while (resultSet0.next()) {

                int idDivCo = resultSet0.getInt("id_div_contr");

                Statement statement = DBConnector.getConnection().createStatement();
                String SQL = "SELECT contract.*  contract " +
                        "FROM divcon, contract " +
                        "WHERE " +
                        "AND " + idDivCo + " = divcon.id_div_contr " +
                        "AND divco.id_contr = contract.id_contr";

                ResultSet resultSet = statement.executeQuery(SQL);
                resultSet.next();

                divorceContracts.add(new DivorceContract(
                        resultSet.getInt("id_contr"),
                        idDivCo,
                        resultSet.getString("datebe"),
                        resultSet.getString("dateen"),
                        resultSet.getBoolean("resolu"),
                        resultSet.getString("termin")));


            }
        } catch (SQLException e) {

        }
        return divorceContracts;
    }
}
