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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static User user;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		try (PrintWriter out = response.getWriter()) {
			String username = request.getParameter("username").trim();
			String password = request.getParameter("password").trim();

			Boolean verifyResult = verifyUser(username, password);

			JSONObject jsonObject = new JSONObject();

			if (verifyResult) {
				jsonObject.put("Result", "登录成功！");
				jsonObject.put("Username", user.getUsername());
				jsonObject.put("Password", user.getPassword());
				jsonObject.put("IsValid", String.valueOf(user.isValid()));
			} else {
				jsonObject.put("Result", "用户名或密码错误！");
			}

			out.write(jsonObject.toString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private Boolean verifyUser(String username, String password) {
		user = UserDAO.queryUser(username);
		return user != null && user.isValid && password.equals(user.getPassword());
	}
}
