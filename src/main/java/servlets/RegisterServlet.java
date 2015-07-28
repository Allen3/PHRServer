package servlets;

import Beans.PersonInfo;
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

/**
 * Created by Allen on 2015/7/28.
 */
@WebServlet(name = "registerServlet", urlPatterns = "/registerServlet")
public class RegisterServlet extends HttpServlet {
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


        PersonInfo personInfo = new Gson().fromJson(builder.toString(), PersonInfo.class);

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


        try {
            if (DatabaseUtil.update(sql)) {
                json = new Gson().toJson(Constants._REGISTER_SUCCESS_);
            } else {
                json = new Gson().toJson(Constants._REGISTER_FAIL_);
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
