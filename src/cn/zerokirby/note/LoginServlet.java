package cn.zerokirby.note;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {// 登录servlet
    private static final long serialVersionUID = 1L;
    static User user;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");// 设置转码格式
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        try (PrintWriter out = response.getWriter()) {
            String username = request.getParameter("username").trim();// 获取用户名
            String password = request.getParameter("password").trim();// 获取密码

            int verifyResult = verifyUser(username, password);// 校验用户

            JSONObject jsonObject = new JSONObject();// 创建JSON对象

            if (verifyResult == 1) {
                jsonObject.put("Result", "登录成功！");
                jsonObject.put("Id", user.getId());
                jsonObject.put("RegisterTime", user.getRegisterTime());
                jsonObject.put("SyncTime", user.getSyncTime());
            } else if (verifyResult == 0)
                jsonObject.put("Result", "用户名或密码错误！");
            else if (verifyResult == -1)
                jsonObject.put("Result", "该用户已被停用！");
            else
                jsonObject.put("Result", "用户不存在！");

            out.write(jsonObject.toString());// 输出JSON字符串
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private int verifyUser(String username, String password) {// 校验用户提供的登录凭据
        user = UserDAO.queryUser(username);// 通过用户名查询用户
        if (user != null && user.isValid && password.equals(user.getPassword()))// 用户存在、有效且密码正确
            return 1;
        else if (user != null && user.isValid && !password.equals(user.getPassword()))// 用户存在、有效但密码错误
            return 0;
        else if (user != null && !user.isValid)// 用户已被停用
            return -1;
        else
            return -2;// 用户不存在
    }
}
