package cn.zerokirby.note;

import java.io.File;
import java.util.Map;
import java.util.UUID;

public class FileUtil {
	/**
	 * 根据hash打散文件，然后获取保存目录
	 *
	 * @param filename     文件名称
	 * @param fileSaveRoot 文件保存根目录
	 * @return 文件实际保存目录
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
			// 二级目录需要连续创建两个
			file.mkdirs();
		}
		return relativePath;
	}

	// 防止文件提交上来重复名字，所以加上唯一的UUID
	public static String makeFileName(String fileName) {
		return UUID.randomUUID().toString() + "_" + fileName;
	}

	/**
	 *
	 * 主要是为了之后展示给用户看，带上UUID不是很难受？
	 * 
	 * @param fileUUIDName UUID文件名
	 * @return 截取之后的名字
	 */
	public static String extractFileName(String fileUUIDName) {
		int index = fileUUIDName.lastIndexOf(fileUUIDName);
		return fileUUIDName.substring(index);
	}

	/**
	 * 递归遍历文件树，将值存进map中便于jsp展示
	 *
	 * @param f   文件
	 * @param map 存放文件的map，键是UUID名字，值是截取UUID后的
	 */
	public static void putFiles(File f, Map<String, String> map) {
		File[] files = f.listFiles();
		for (File file : files) {
			if (file == null) {
				// 回溯点
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
