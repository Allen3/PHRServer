package servlets;

import Beans.PersonInfo;
import com.google.gson.Gson;
import utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Allen on 2015/7/26.
 */
@WebServlet(name = "loginServlet", urlPatterns = "/loginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);

        String json = null;
        String sql = null;
        ResultSet resultSet = null;


        String name = req.getParameter(Constants.NAME);
        String password = req.getParameter(Constants.PASSWORD);

        sql = "SELECT * FROM personinfo where " + " (" +
                Constants.NAME + "=" + "'" + name + "'" + " AND " +
                Constants.PASSWORD + "=" + "'" + password + "'" +
                ");";

//TEST
        System.out.println("sql = " + sql);

        try {
            resultSet = DatabaseUtil.queryData(sql);

            if (!resultSet.next()) {
                PersonInfo personInfo = new PersonInfo();
                personInfo.setPerson_id(Constants._LOGIN_FAIL_);
                json = new Gson().toJson(personInfo);
            } else {
                //resultSet.beforeFirst();

                PersonInfo personInfo = new PersonInfo();

                personInfo.setPerson_id(resultSet.getString(Constants.PERSON_ID));
                personInfo.setName(resultSet.getString(Constants.NAME));
                personInfo.setGender(resultSet.getInt(Constants.GENDER));
                personInfo.setAge(resultSet.getInt(Constants.AGE));
                personInfo.setPhone(resultSet.getString(Constants.PHONE));
                personInfo.setVip(resultSet.getInt(Constants.VIP));
                personInfo.setPassword(resultSet.getString(Constants.PASSWORD));
                personInfo.setBloodType(resultSet.getString(Constants.BLOODTYPE));
                personInfo.setGroup_id(resultSet.getInt(Constants.GROUP_ID));

                json = new Gson().toJson(personInfo);
//TEST
                System.out.println("json = " + json);
            }

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
