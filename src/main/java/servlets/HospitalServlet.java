package servlets;

import Beans.Hospital;
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
 * Created by Allen on 2015/7/28.
 */
@WebServlet(name = "hospitalServlet", urlPatterns = "/hospitalServlet")
public class HospitalServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);

        String json = null;
        String sql = null;
        ResultSet resultSet = null;
        ArrayList<Hospital> hospitalList;

        sql = "SELECT * FROM hospital";

//TEST
        System.out.println("sql = " + sql);
        try {
            resultSet = DatabaseUtil.queryData(sql);
            hospitalList = new ArrayList<>();

            while (resultSet.next()) {
                Hospital hospital = new Hospital();

                hospital.setHid(resultSet.getInt(Constants.HID));
                hospital.sethName(resultSet.getString(Constants.HNAME));
                hospital.sethAddress(resultSet.getString(Constants.HADDRESS));
                hospital.sethProfile(resultSet.getString(Constants.HPROFILE));

                hospitalList.add(hospital);
            }

            json = new Gson().toJson(hospitalList);
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
