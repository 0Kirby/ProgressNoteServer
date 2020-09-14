package cn.zerokirby.note;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class SyncServlet_CS
 */
@WebServlet("/SyncServlet_CS")
public class SyncServlet_CS extends HttpServlet { //同步servlet（客户端到服务器）
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");// 设置转码格式
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        try (PrintWriter out = response.getWriter()) {
            int userId = Integer.parseInt(request.getParameter("userId").trim());// 获取用户ID
            String jsonString = request.getParameter("json").trim();// 获取JSON数组的字符串

            JSONArray jsonArray = new JSONArray(jsonString);//根据json字符串创建json数组

            UserDAO.pushServer(userId, jsonArray); //推送到服务器

            out.write("同步成功！");
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

}
