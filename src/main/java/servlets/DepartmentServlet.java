package servlets;

import Beans.Department;
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
@WebServlet(name = "departmentServlet", urlPatterns = "/departmentServlet")
public class DepartmentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);

        String json = null;
        String sql = null;
        ResultSet resultSet = null;
        ArrayList<Department> departmentList;

        String hospitalID = req.getParameter(Constants.HID);
        if (hospitalID == null) {
            sql = "SELECT * FROM department;";
        } else {
            sql = "SELECT * FROM department WHERE " + Constants.HID + "=" + "'" + hospitalID + "'" + ";";
        }

//TEST
        System.out.println("sql = " + sql);
        try {
            resultSet = DatabaseUtil.queryData(sql);
            departmentList = new ArrayList<>();

            while (resultSet.next()) {
                Department department = new Department();

                department.setDep_id(resultSet.getInt(Constants.DEP_ID));
                department.setDep_name(resultSet.getString(Constants.DEP_NAME));
                department.setHid(resultSet.getInt(Constants.HID));

                departmentList.add(department);
            }

            json = new Gson().toJson(departmentList);
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
