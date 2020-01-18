package com.xqjtqy.progressnote;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

/**
 * Servlet implementation class SyncServlet_CS
 */
@WebServlet("/SyncServlet_CS")
public class SyncServlet_CS extends HttpServlet { //ͬ��servlet���ͻ��˵���������
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");// ����ת���ʽ
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		try (PrintWriter out = response.getWriter()) {
			int userId = Integer.valueOf(request.getParameter("userId").trim());// ��ȡ�û�ID
			String jsonString = request.getParameter("json").trim();// ��ȡJSON������ַ���

			JSONArray jsonArray = new JSONArray(jsonString);//����json�ַ�������json����
			
			UserDAO.pushServer(userId,jsonArray); //���͵�������
			
			out.write("ͬ���ɹ���");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
