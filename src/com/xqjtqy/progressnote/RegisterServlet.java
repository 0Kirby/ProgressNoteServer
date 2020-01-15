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
public class RegisterServlet extends HttpServlet {// 注册servlet
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
		response.setContentType("text/html;charset=utf-8");// 设置转码格式
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		try (PrintWriter out = response.getWriter()) {
			String username = request.getParameter("username").trim();// 获取用户名
			String password = request.getParameter("password").trim();// 获取密码

			JSONObject jsonObject = new JSONObject();// 创建JSON对象

			if (isExist(username))
				jsonObject.put("Result", "用户已存在！");
			else {
				int id = UserDAO.registerUser(username, password);
				jsonObject.put("Result", "注册成功！");
				jsonObject.put("Id", id);
			}
			out.write(jsonObject.toString());// 输出JSON字符串
		}
	}

	private Boolean isExist(String username) {// 查询用户是否存在
		user = UserDAO.queryUser(username);
		if (user == null)
			return false;
		else
			return true;
	}
}
