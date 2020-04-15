package cn.zerokirby.note;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Servlet implementation class SyncServlet_SC
 */
@WebServlet("/SyncServlet_SC")
public class SyncServlet_SC extends HttpServlet { //同步servlet（服务器到客户端）
    private static final long serialVersionUID = 1L;

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
            int userId = Integer.valueOf(request.getParameter("userId").trim());// 获取用户ID
            ArrayList<Note> noteList = new ArrayList<>();
            noteList = UserDAO.fetchServer(userId);// 从服务器获取对应用户的笔记列表
            Note note;
            Iterator<Note> iterator = noteList.iterator();//NoteList迭代器

            JSONArray jsonArray = new JSONArray();// 创建JSON数组
            JSONObject jsonObject;// 创建JSON对象

            while (iterator.hasNext())//遍历集合中每一个Note对象
            {
                note = iterator.next();
                jsonObject = new JSONObject();//把每个Note对象的属性加入JSON对象

                jsonObject.put("NoteId", note.getNoteId());
                jsonObject.put("Time", note.getTime());
                jsonObject.put("Title", note.getTitle());
                jsonObject.put("Content", note.getContent());

                jsonArray.put(jsonObject);

            }
            out.write(jsonArray.toString());// 输出JSON字符串
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
