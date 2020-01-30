package cn.zerokirby.note;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Servlet implementation class SyncServlet_SC
 */
@WebServlet("/SyncServlet_SC")
public class SyncServlet_SC extends HttpServlet { //ͬ��servlet�����������ͻ��ˣ�
	private static final long serialVersionUID = 1L;
	
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
			int userId = Integer.valueOf(request.getParameter("userId").trim());// ��ȡ�û�ID
			ArrayList<Note> noteList = new ArrayList<>();
			noteList = UserDAO.fetchServer(userId);// �ӷ�������ȡ��Ӧ�û��ıʼ��б�
			Note note;
			Iterator<Note> iterator = noteList.iterator();//NoteList������
			
			JSONArray jsonArray = new JSONArray();// ����JSON����
			JSONObject jsonObject;// ����JSON����
			
			while(iterator.hasNext())//����������ÿһ��Note����
			{
				note = iterator.next();
				jsonObject = new JSONObject();//��ÿ��Note��������Լ���JSON����
				
				jsonObject.put("NoteId", note.getNoteId());
				jsonObject.put("Time", note.getTime());
				jsonObject.put("Title", note.getTitle());
				jsonObject.put("Content", note.getContent());

				jsonArray.put(jsonObject);
				
			}
			out.write(jsonArray.toString());// ���JSON�ַ���
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

}
