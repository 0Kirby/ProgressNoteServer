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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {// ��¼servlet
	private static final long serialVersionUID = 1L;
	static User user;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");// ����ת���ʽ
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		try (PrintWriter out = response.getWriter()) {
			String username = request.getParameter("username").trim();// ��ȡ�û���
			String password = request.getParameter("password").trim();// ��ȡ����

			int verifyResult = verifyUser(username, password);// У���û�

			JSONObject jsonObject = new JSONObject();// ����JSON����

			if (verifyResult == 1) {
				jsonObject.put("Result", "��¼�ɹ���");
				jsonObject.put("Id", user.getId());
				jsonObject.put("RegisterTime",user.getRegisterTime());
				jsonObject.put("SyncTime",user.getSyncTime());
			} else if (verifyResult == 0)
				jsonObject.put("Result", "�û������������");
			else if (verifyResult == -1)
				jsonObject.put("Result", "���û��ѱ�ͣ�ã�");
			else
				jsonObject.put("Result", "�û������ڣ�");

			out.write(jsonObject.toString());// ���JSON�ַ���
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

	private int verifyUser(String username, String password) {// У���û��ṩ�ĵ�¼ƾ��
		user = UserDAO.queryUser(username);// ͨ���û�����ѯ�û�
		if (user != null && user.isValid && password.equals(user.getPassword()))// �û����ڡ���Ч��������ȷ
			return 1;
		else if (user != null && user.isValid && !password.equals(user.getPassword()))// �û����ڡ���Ч���������
			return 0;
		else if (user != null && !user.isValid)// �û��ѱ�ͣ��
			return -1;
		else
			return -2;// �û�������
	}
}
