package servlets;

import Beans.Diagnose;
import Beans.NewsInfo;
import Beans.PersonHealth;
import com.google.gson.Gson;
import utils.Constants;
import utils.DatabaseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Allen on 2015/7/29.
 */
@WebServlet(name = "personHealthServlet", urlPatterns = "/personHealthServlet")
public class PersonHealthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);

        String json = null;
        String sql = null;
        ResultSet resultSet = null;
        ArrayList<PersonHealth> personHealthList;

        String personID = req.getParameter(Constants.PERSON_ID);
        if (personID == null) {
            sql = "SELECT * FROM person_health;";
        } else {
            sql = "SELECT * FROM person_health WHERE " + Constants.PERSON_ID + "=" + "'" + personID +"'" + ";";
        }

//TEST
        System.out.println("sql = " + sql);

        try {
            resultSet = DatabaseUtil.queryData(sql);
            personHealthList = new ArrayList<PersonHealth>();

            while (resultSet.next()) {
                PersonHealth personHealth = new PersonHealth();

                personHealth.setPrompt_date(resultSet.getString(Constants.PROMPT_DATE));
                personHealth.setDrug_name(resultSet.getString(Constants.DRUG_NAME));
                personHealth.setDrug_dose(resultSet.getString(Constants.DRUG_DOSE));
                personHealth.setDiag_date(resultSet.getString(Constants.DIAG_DATE));
                personHealth.setPerson_id(resultSet.getString(Constants.PERSON_ID));

                personHealthList.add(personHealth);
            }

            json =  new Gson().toJson(personHealthList);
//TEST
            System.out.println("json = " + json);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.print(json);
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String json = null;
        String sql = null;
        String result;

        BufferedReader reader = req.getReader();
        StringBuilder builder = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            builder.append(line).append(System.getProperty("line.separator"));
        }

        PersonHealth personHealth = new Gson().fromJson(builder.toString(), PersonHealth.class);

        sql = "INSERT INTO person_health ( person_id, diag_date, drug_name, drug_dose, prompt_date ) VALUES " +
                "( " + personHealth.getPerson_id() + ", " +
                "'" + personHealth.getDiag_date() + "', " +
                "'" + personHealth.getDrug_name() + "', " +
                "'" + personHealth.getDrug_dose() + "', " +
                "'" + personHealth.getPrompt_date() + "'" +
                ");";
//TEST
        System.out.println("sql = " + sql);

        try {
            if (DatabaseUtil.update(sql)) {
                json = new Gson().toJson(Constants._SUBMIT_PERSONHEALTH_SUCCESS_);
            } else {
                json = new Gson().toJson(Constants._SUBMIT_PERSONHEALTH_FAIL_);
            }

            resp.setContentType("application/json;charset=UTF-8");
            PrintWriter out = resp.getWriter();

            out.print(json);
            out.flush();
            out.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
