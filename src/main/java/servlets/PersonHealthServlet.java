package servlets;

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

        String newsType = req.getParameter(Constants._NEWSTYPE_);

        // TODO
        // Update the sql statement.
        sql = "SELECT * FROM  WHERE news_Type=" + "'" + newsType + "'";
//TEST
        System.out.println("sql = " + sql);

        try {
            resultSet = DatabaseUtil.queryData(sql);
            personHealthList = new ArrayList<PersonHealth>();

            while (resultSet.next()) {
                PersonHealth personHealth = new PersonHealth();

                personHealth.setPerson_health_id(resultSet.getString(Constants.PERSON_HEALTH_ID));
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
}
