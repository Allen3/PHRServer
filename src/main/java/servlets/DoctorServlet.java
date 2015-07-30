package servlets;

import Beans.Diagnose;
import Beans.Doctor;
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
@WebServlet(name = "doctorServlet", urlPatterns = "/doctorServlet")
public class DoctorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);

        String json = null;
        String sql = null;
        ResultSet resultSet = null;
        ArrayList<Doctor> doctorList = null;

        String departmentID = req.getParameter(Constants.DEP_ID);
        if (departmentID == null) {
            sql = "SELECT * FROM doctor;";
        } else {
            sql = "SELECT * FROM doctor WHERE " + Constants.DEP_ID + "=" + "'" + departmentID +"'" + ";";
        }
//TEST
        System.out.println("sql = " + sql);

        try {
            resultSet = DatabaseUtil.queryData(sql);
            doctorList = new ArrayList<>();

            while (resultSet.next()) {
                Doctor doctor = new Doctor();

                doctor.setDoctor_id(resultSet.getInt(Constants.DOCTOR_ID));
                doctor.setDoc_name(resultSet.getString(Constants.DOC_NAME));
                doctor.setDep_id(resultSet.getInt(Constants.DEP_ID));
                doctor.setDoc_profile(resultSet.getString(Constants.DOC_PROFILE));
                doctor.setDoc_phone(resultSet.getString(Constants.DOC_PHONE));

                doctorList.add(doctor);
            }

            json = new Gson().toJson(doctorList);
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

        Diagnose diagnose = new Gson().fromJson(builder.toString(), Diagnose.class);

        sql = "INSERT INTO diagnose ( person_id, doctor_id, diag_date ) VALUES " +
                "( " + diagnose.getPerson_id() + ", " +
                "'" + diagnose.getDoctor_id() + "', " +
                "'" + diagnose.getDiagnose_date() + "'" +
                ");";
//TEST
        System.out.println("sql = " + sql);

        try {
            if (DatabaseUtil.update(sql)) {
                json = new Gson().toJson(Constants._DIAGNOSE_SUCCESS_);
            } else {
                json = new Gson().toJson(Constants._DIAGNOSE_FAIL_);
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
