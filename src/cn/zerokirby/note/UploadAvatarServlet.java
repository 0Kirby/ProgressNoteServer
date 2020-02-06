package cn.zerokirby.note;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
@WebServlet("/UploadAvatarServlet")
public class UploadAvatarServlet extends HttpServlet { // �ϴ�ͷ��servlet

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");// ����ת���ʽ
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		int userId = Integer.valueOf(request.getParameter("userId").trim());// ��ȡ�û�ID

		String savePathRoot = "/usr/local/avatar";// ����·��

		Collection<Part> parts = request.getParts();
		for (Part part : parts) {

			// ����ύ������
			String filename = part.getSubmittedFileName();

			// ���ڲ����ϴ��ļ���input�ᴫ����ֵ��������û��ָ���ϴ����ļ�Ҳ��null���������һ��
			if (filename == null) {
				continue;
			}

			String fileUUIDName = FileUtil.makeFileName(filename);// ���������ļ���

			String saveDir = FileUtil.fileSave(fileUUIDName, savePathRoot);// �����Ŀ¼

			// File.separator������Ӧ��windows��linux�ָ�������\\��/
			part.write(savePathRoot + saveDir + File.separator + fileUUIDName);
			// ɾ����ʱ�ļ�
			part.delete();

			UserDAO.avatarPathDb(userId, saveDir + File.separator + fileUUIDName);
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}