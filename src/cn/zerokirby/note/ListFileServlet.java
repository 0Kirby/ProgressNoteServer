package cn.zerokirby.note;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListFiles
 */
@WebServlet("/ListFileServlet")
public class ListFileServlet extends HttpServlet { // 列出文件servlet
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fileRoot = "/usr/local/avatar";
		File root = new File(fileRoot);
		if (!root.exists()) {
			return;
		}

		Map<String, String> fileMap = new HashMap<>();
		FileUtil.putFiles(root, fileMap);
		request.setAttribute("files", fileMap);
		request.getRequestDispatcher("listFiles.jsp").forward(request, response);
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
