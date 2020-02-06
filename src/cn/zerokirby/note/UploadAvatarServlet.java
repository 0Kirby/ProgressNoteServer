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
public class UploadAvatarServlet extends HttpServlet { // 上传头像servlet

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");// 设置转码格式
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		int userId = Integer.valueOf(request.getParameter("userId").trim());// 获取用户ID

		String savePathRoot = "/usr/local/avatar";// 保存路径

		Collection<Part> parts = request.getParts();
		for (Part part : parts) {

			// 获得提交的名字
			String filename = part.getSubmittedFileName();

			// 对于不是上传文件的input会传来空值，或者你没有指定上传的文件也是null，这里过滤一下
			if (filename == null) {
				continue;
			}

			String fileUUIDName = FileUtil.makeFileName(filename);// 创建保存文件名

			String saveDir = FileUtil.fileSave(fileUUIDName, savePathRoot);// 保存的目录

			// File.separator是自适应的windows和linux分隔符就是\\和/
			part.write(savePathRoot + saveDir + File.separator + fileUUIDName);
			// 删除临时文件
			part.delete();

			UserDAO.avatarPathDb(userId, saveDir + File.separator + fileUUIDName);
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}