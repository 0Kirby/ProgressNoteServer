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
 * Servlet implementation class DownloadAvatarServlet
 */
@WebServlet("/DownloadAvatarServlet")
public class DownloadAvatarServlet extends HttpServlet { // ����ͷ��servlet
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String fileRoot = "/usr/local/avatar";// �ļ��洢��Ŀ¼

		int userId = Integer.valueOf(request.getParameter("userId").trim());// ��ȡ�û�ID

		final String avatarPath = UserDAO.queryAvatarPath(userId);

		if (avatarPath != null) {// �������ͷ��
			final String filePath = fileRoot + avatarPath;
			final File file = new File(filePath);// �����ļ�
			if (file.exists()) {
				response.setHeader("content-disposition",
						"attachment;filename=" + URLEncoder.encode(userId + ".jpg", "UTF-8"));

				FileInputStream inputStream = new FileInputStream(filePath);// �����ļ�������
				ServletOutputStream servletOutputStream = response.getOutputStream();// Servlet�����
				byte[] buffer = new byte[1024];// ������
				int len;
				while ((len = inputStream.read(buffer)) > 0) {// ûд�����д
					servletOutputStream.write(buffer, 0, len);
				}
				inputStream.close();
				servletOutputStream.close();
			}
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
