package cn.zerokirby.note;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {// ����servlet
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownloadServlet() {
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
		// ��UUID���ļ���
		String fileUUIDName = request.getParameter("fileName");
		String fileName = FileUtil.extractFileName(fileUUIDName);
		System.out.println(fileName);

		String fileRoot = "/usr/local/avatar";// �ļ��洢��Ŀ¼

		// �����ļ����ҵ��洢Ŀ¼
		String fileDir = FileUtil.fileSave(fileUUIDName, fileRoot);

		String targetFileUrl = fileRoot + fileDir + File.separator + fileUUIDName;
		System.out.println(targetFileUrl);
		File file = new File(targetFileUrl);

		if (!file.exists()) {
			System.out.println("Ŀ���ļ�������");
			return;
		}
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

		FileInputStream inputStream = new FileInputStream(file);
		ServletOutputStream servletOutputStream = response.getOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = inputStream.read(buffer)) > 0) {
			servletOutputStream.write(buffer, 0, len);
		}
		inputStream.close();
		servletOutputStream.close();
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
