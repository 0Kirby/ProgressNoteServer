package cn.zerokirby.note;

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
public class RegisterServlet extends HttpServlet {// ע��servlet
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
		response.setContentType("text/html;charset=utf-8");// ����ת���ʽ
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		try (PrintWriter out = response.getWriter()) {
			String username = request.getParameter("username").trim();// ��ȡ�û���
			String password = request.getParameter("password").trim();// ��ȡ����
			String language = request.getParameter("language").trim();// ��ȡ����
			String version = request.getParameter("version").trim();// ��ȡ�汾
			String display = request.getParameter("display").trim();// ��ȡ��ʾ��Ϣ
			String model = request.getParameter("model").trim();// ��ȡ�ͺ�
			String brand = request.getParameter("brand").trim();// ��ȡƷ��

			JSONObject jsonObject = new JSONObject();// ����JSON����

			if (isExist(username))
				jsonObject.put("Result", "�û��Ѵ��ڣ�");
			else {
				int id = UserDAO.registerUser(username, password, language, version, display, model, brand);
				jsonObject.put("Result", "ע��ɹ���");
				jsonObject.put("Id", id);
			}
			out.write(jsonObject.toString());// ���JSON�ַ���
		}
	}

	private Boolean isExist(String username) {// ��ѯ�û��Ƿ����
		user = UserDAO.queryUser(username);
		if (user == null)
			return false;
		else
			return true;
	}
}
