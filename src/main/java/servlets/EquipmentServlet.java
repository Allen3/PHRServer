package servlets;

import Beans.Doctor;
import Beans.Equipment;
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
@WebServlet(name = "equipmentServlet", urlPatterns = "/equipmentServlet")
public class EquipmentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);

        String json = null;
        String sql = null;
        ResultSet resultSet = null;
        ArrayList<Equipment> equipmentList = null;

        String equipmentName = req.getParameter(Constants.EQUIP_NAME);
        if (equipmentName == null) {
            sql = "SELECT * FROM equipment;";
        } else {
            sql = "SELECT * FROM equipment WHERE " + Constants.EQUIP_NAME + "=" + "'" + equipmentName +"'" + ";";
        }
//TEST
        System.out.println("sql = " + sql);

        try {
            resultSet = DatabaseUtil.queryData(sql);
            equipmentList = new ArrayList<>();

            while (resultSet.next()) {
                Equipment equipment = new Equipment();

                equipment.setEquip_id(resultSet.getInt(Constants.EQUIP_ID));
                equipment.setEquip_name(resultSet.getString(Constants.EQUIP_NAME));
                equipment.setEquip_price(resultSet.getString(Constants.EQUIP_PRICE));

                equipmentList.add(equipment);
            }

            json = new Gson().toJson(equipmentList);
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
