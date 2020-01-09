package com.xqjtqy.progressnote;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static User user;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		try (PrintWriter out = response.getWriter()) {
			String username = request.getParameter("username").trim();
			String password = request.getParameter("password").trim();

			JSONObject jsonObject = new JSONObject();

			if (isExist(username)) {
				jsonObject.put("Result", "用户已存在！");
			} else {
				UserDAO.registerUser(username, password);
				jsonObject.put("Result", "注册成功！");
			}
			out.write(jsonObject.toString());
		}
	}

	private Boolean isExist(String username) {
		user = UserDAO.queryUser(username);
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}
}
