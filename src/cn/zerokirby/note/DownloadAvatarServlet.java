package cn.zerokirby.note;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Servlet implementation class DownloadAvatarServlet
 */
@WebServlet("/DownloadAvatarServlet")
public class DownloadAvatarServlet extends HttpServlet { // 下载头像servlet
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        final String fileRoot = "/usr/local/avatar";// 文件存储根目录

        int userId = Integer.valueOf(request.getParameter("userId").trim());// 获取用户ID

        final String avatarPath = UserDAO.queryAvatarPath(userId);

        if (avatarPath != null) {// 如果存在头像
            final String filePath = fileRoot + avatarPath;
            final File file = new File(filePath);// 创建文件
            if (file.exists()) {
                response.setHeader("content-disposition",
                        "attachment;filename=" + URLEncoder.encode(userId + ".jpg", StandardCharsets.UTF_8));

                FileInputStream inputStream = new FileInputStream(filePath);// 开启文件输入流
                ServletOutputStream servletOutputStream = response.getOutputStream();// Servlet输出流
                byte[] buffer = new byte[1024];// 缓冲区
                int len;
                while ((len = inputStream.read(buffer)) > 0) {// 没写完继续写
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
