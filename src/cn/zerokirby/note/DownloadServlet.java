package cn.zerokirby.note;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {// 下载servlet
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // 带UUID的文件名
        String fileUUIDName = request.getParameter("fileName");
        String fileName = FileUtil.extractFileName(fileUUIDName);

        String fileRoot = "/usr/local/avatar";// 文件存储根目录

        // 根据文件名找到存储目录
        String fileDir = FileUtil.fileSave(fileUUIDName, fileRoot);

        String targetFileUrl = fileRoot + fileDir + File.separator + fileUUIDName;

        File file = new File(targetFileUrl);

        if (!file.exists()) {
            System.out.println("目标文件不存在");
            return;
        }
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));

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
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doGet(request, response);
    }

}
