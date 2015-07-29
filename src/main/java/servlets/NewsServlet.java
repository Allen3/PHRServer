package servlets;

import Beans.NewsInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
@WebServlet(name = "newsServlet", urlPatterns = "/newsServlet")
public class NewsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);

        String json = null;
        String sql = null;
        ResultSet resultSet = null;
        ArrayList<NewsInfo> newsList;

        String newsType = req.getParameter(Constants._NEWSTYPE_);

        sql = "SELECT * FROM newsinfo WHERE news_Type=" + "'" + newsType + "'";
//TEST
        System.out.println("sql = " + sql);

        try {
            resultSet = DatabaseUtil.queryData(sql);
            newsList = new ArrayList<NewsInfo>();

            while (resultSet.next()) {
                NewsInfo newsInfo = new NewsInfo();

                newsInfo.setNews_Type(resultSet.getString(Constants.NEWS_TYPE));
                newsInfo.setNews_Title(resultSet.getString(Constants.NEWS_TITLE));
                newsInfo.setNews_Content(resultSet.getString(Constants.NEWS_CONTENT));

                newsList.add(newsInfo);
            }

            json =  new Gson().toJson(newsList);
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
