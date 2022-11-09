package cn.zerokirby.note;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serial;

@WebServlet("/InvalidateUserServlet")
public class InvalidateUserServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json;charset=utf-8");// 设置转码格式
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        int userId = Integer.parseInt(request.getParameter("userId").trim());// 获取用户ID

        UserDAO.invalidateUser(userId);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doGet(request, response);
    }
}
