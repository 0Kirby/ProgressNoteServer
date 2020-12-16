package cn.zerokirby.note;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {// 登录servlet
    @Serial
    private static final long serialVersionUID = 1L;
    static User user;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=utf-8");// 设置转码格式
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
            int verifyResult = verifyUser(username, password, language, version, display, model, brand);// 校验用户

            JSONObject jsonObject = new JSONObject();// 创建JSON对象

            switch (verifyResult) {
                case 1 -> {
                    jsonObject.put("Result", "登录成功！");
                    jsonObject.put("Status", 1);
                    jsonObject.put("Id", user.getUserId());
                    jsonObject.put("RegisterTime", user.getRegisterTime());
                    jsonObject.put("SyncTime", user.getLastSync());
                }
                case 0 -> {
                    jsonObject.put("Result", "用户名或密码错误！");
                    jsonObject.put("Status", 0);
                }
                case -1 -> {
                    jsonObject.put("Result", "该用户已被停用！");
                    jsonObject.put("Status", -1);
                }
                case -2 -> {
                    jsonObject.put("Result", "用户不存在！");
                    jsonObject.put("Status", -2);
                }
            }

            out.write(jsonObject.toString());// 输出JSON字符串
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doGet(request, response);
    }

    private int verifyUser(String username, String password, String language, String version, String display, String model, String brand) {// 校验用户提供的登录凭据
        user = UserDAO.queryUser(username, language, version, display, model, brand);// 通过用户名查询用户
        if (user != null && user.isValid() && password.equals(user.getPassword()))// 用户存在、有效且密码正确
            return 1;
        else if (user != null && user.isValid() && !password.equals(user.getPassword()))// 用户存在、有效但密码错误
            return 0;
        else if (user != null && !user.isValid())// 用户已被停用
            return -1;
        else
            return -2;// 用户不存在
    }
}
