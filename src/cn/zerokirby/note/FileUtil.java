package cn.zerokirby.note;

import java.io.File;
import java.util.Map;
import java.util.UUID;

public class FileUtil {
	/**
	 * ����hash��ɢ�ļ���Ȼ���ȡ����Ŀ¼
	 *
	 * @param filename     �ļ�����
	 * @param fileSaveRoot �ļ������Ŀ¼
	 * @return �ļ�ʵ�ʱ���Ŀ¼
	 */
	public static String fileSave(String filename, String fileSaveRoot) {
		int hash = filename.hashCode();
		int dir1 = hash & 0xf;// 0-15
		int dir2 = (hash & 0xf0) >> 4;// 0-15

		String relativePath = File.separator + dir1 + File.separator + dir2;
		String fileSavePath = fileSaveRoot + File.separator + dir1 + File.separator + dir2;
		System.out.println(fileSavePath);
		File file = new File(fileSavePath);
		if (!file.exists()) {
			// ����Ŀ¼��Ҫ������������
			file.mkdirs();
		}
		return relativePath;
	}

	// ��ֹ�ļ��ύ�����ظ����֣����Լ���Ψһ��UUID
	public static String makeFileName(String fileName) {
		return UUID.randomUUID().toString() + "_" + fileName;
	}

	/**
	 *
	 * ��Ҫ��Ϊ��֮��չʾ���û���������UUID���Ǻ����ܣ�
	 * 
	 * @param fileUUIDName UUID�ļ���
	 * @return ��ȡ֮�������
	 */
	public static String extractFileName(String fileUUIDName) {
		int index = fileUUIDName.lastIndexOf(fileUUIDName);
		return fileUUIDName.substring(index);
	}

	/**
	 * �ݹ�����ļ�������ֵ���map�б���jspչʾ
	 *
	 * @param f   �ļ�
	 * @param map ����ļ���map������UUID���֣�ֵ�ǽ�ȡUUID���
	 */
	public static void putFiles(File f, Map<String, String> map) {
		File[] files = f.listFiles();
		for (File file : files) {
			if (file == null) {
				// ���ݵ�
				return;
			}
			if (file.isDirectory()) {
				putFiles(file, map);
			} else {
				String fileUUIDName = file.getName();
				String fileName = extractFileName(fileUUIDName);
				map.put(fileUUIDName, fileName);
			}
		}
	}
}
