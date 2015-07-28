package servlets;

import Beans.LoginResponse;
import Beans.PersonInfo;
import Beans.RegisterResponse;
import com.google.gson.Gson;
import utils.*;

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

/**
 * Created by Allen on 2015/7/26.
 */
@WebServlet(name = "loginRegisterServlet", urlPatterns = "/loginRegisterServlet")
public class LoginRegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);

        String json = null;
        String sql = null;
        ResultSet resultSet = null;

        String flag = req.getParameter(Constants._FLAG_);
//TEST
        System.out.println("flag = " + flag);

        if (flag.equals(Constants._LOGIN_)) {
            String username = req.getParameter(Constants.USERNAME);
            String password = req.getParameter(Constants.PASSWORD);

//TEST
            System.out.println("USERNAME =" + username);
            System.out.println("PASSWORD =" + password);

            // TODO
            // Update the sql statement;
            sql = "SELECT * FROM personinfo where " + Constants.PASSWORD  + "=" + "'" + password + "';";
//TEST
            System.out.println("sql = " + sql);

            try {
                resultSet = DatabaseUtil.queryData(sql);

                if (!resultSet.next()) {
                    json = new Gson().toJson(new LoginResponse(Constants._LOGIN_FAIL_));
                } else {
                    //resultSet.beforeFirst();

                    LoginResponse loginResponse = new LoginResponse(Constants._LOGIN_SUCCESS_);

                    loginResponse.setPerson_id(resultSet.getString(Constants.PERSON_ID));
                    loginResponse.setName(resultSet.getString(Constants.NAME));
                    loginResponse.setGender(resultSet.getInt(Constants.GENDER));
                    loginResponse.setAge(resultSet.getInt(Constants.AGE));
                    loginResponse.setPhone(resultSet.getString(Constants.PHONE));
                    loginResponse.setVip(resultSet.getInt(Constants.VIP));
                    loginResponse.setPassword(resultSet.getString(Constants.PASSWORD));
                    loginResponse.setBloodType(resultSet.getString(Constants.BLOODTYPE));
                    loginResponse.setGroup_id(resultSet.getInt(Constants.GROUP_ID));

                    json = new Gson().toJson(loginResponse);
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


        } else if (flag.equals(Constants._REGISTER_)) {
            // Invalid request.
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        String json = null;
        String sql = null;
        String result;

        BufferedReader reader = req.getReader();
        StringBuilder builder = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            builder.append(line).append(System.getProperty("line.separator"));
        }
//TEST
        System.out.println(builder.toString());


        PersonInfo personInfo = new Gson().fromJson(builder.toString(), PersonInfo.class);

        // TODO
        // Update the sql statement;
        sql = "INSERT INTO personinfo ( person_id, name, gender, age, phone, vip, bloodtype, password, group_id) VALUES " +
                "( " + personInfo.getPerson_id() + ", " +
                "'" + personInfo.getName() + "', " +
                "'" + personInfo.getGender() + "', " +
                "'" + personInfo.getAge() + "', " +
                "'" + personInfo.getPhone() + "', " +
                "'" + personInfo.getVip() + "', " +
                "'" + personInfo.getBloodType() + "', " +
                "'" + personInfo.getPassword() + "', " +
                "'" + personInfo.getGroup_id() + "'" +
                ");";

//TEST
        System.out.println("sql = " + sql);

        try {
            if (DatabaseUtil.update(sql)) {
                json = new Gson().toJson(new RegisterResponse(Constants._REGISTER_SUCCESS_));
            } else {
                json = new Gson().toJson(new RegisterResponse(Constants._REGISTER_FAIL_));
            }

//TEST
            System.out.println("json = " + json);

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
