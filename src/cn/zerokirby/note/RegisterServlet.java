package cn.zerokirby.note;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {// 注册servlet
    @Serial
    private static final long serialVersionUID = 1L;
    static User user;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json;charset=utf-8");// 设置转码格式
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        try (PrintWriter out = response.getWriter()) {
            String username = request.getParameter("username").trim();// 获取用户名
            String password = request.getParameter("password").trim();// 获取密码
            String language = request.getParameter("language").trim();// 获取语言
            String version = request.getParameter("version").trim();// 获取版本
            String display = request.getParameter("display").trim();// 获取显示信息
            String model = request.getParameter("model").trim();// 获取型号
            String brand = request.getParameter("brand").trim();// 获取品牌

            JSONObject jsonObject = new JSONObject();// 创建JSON对象

            if (isExist(username, language, version, display, model, brand)) {
                jsonObject.put("Result", "用户已存在！");
                jsonObject.put("Status", 0);
            } else {
                int id = UserDAO.registerUser(username, password, language, version, display, model, brand);
                jsonObject.put("Result", "注册成功！");
                jsonObject.put("Status", 1);
                jsonObject.put("Id", id);
            }
            out.write(jsonObject.toString());// 输出JSON字符串
        }
    }

    private Boolean isExist(String username, String language, String version, String display,
                            String model, String brand) {// 查询用户是否存在
        user = UserDAO.queryUser(username, language, version, display, model, brand);
        return user != null;
    }
}
