package com.rsh.framework.weixin.utils;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;

/**
 * 文件工具类
 * 
 * @author rsh
 * @version 1.0
 * @since 1.0
 */
public class FileUtil {

	/**
	 * 获取上传文件的contentType
	 *
	 * @param filenameExtension
	 *            文件后缀
	 * @return String
	 */
	public static String getcontentType(String filenameExtension) {
		if (filenameExtension == null) {
            return null;
        }
		if ("bmp".equalsIgnoreCase(filenameExtension)) {
			return "image/bmp";
		}
		if ("gif".equalsIgnoreCase(filenameExtension)) {
			return "image/gif";
		}
		if ("jpeg".equalsIgnoreCase(filenameExtension) || "jpg".equalsIgnoreCase(filenameExtension)) {
			return "image/jpeg";
		}
		if ("png".equalsIgnoreCase(filenameExtension)) {
			return "image/png";
		}
		if ("html".equalsIgnoreCase(filenameExtension)) {
			return "text/html";
		}
		if ("txt".equalsIgnoreCase(filenameExtension)) {
			return "text/plain";
		}
		if ("vsd".equalsIgnoreCase(filenameExtension)) {
			return "application/vnd.visio";
		}
		if ("pptx".equalsIgnoreCase(filenameExtension) || "ppt".equalsIgnoreCase(filenameExtension)) {
			return "application/vnd.ms-powerpoint";
		}
		if ("docx".equalsIgnoreCase(filenameExtension) || "doc".equalsIgnoreCase(filenameExtension)) {
			return "application/msword";
		}
		if ("xml".equalsIgnoreCase(filenameExtension)) {
			return "text/xml";
		}
		return "application/octet-stream";
	}

	/**
	 * 文件类型判断
	 * 
	 * @param fileName
	 *            文件名
	 * @param allowFiles
	 *            文件允许格式, 为空不判断
	 * @return
	 */
	public static boolean checkFileType(String fileName, String[] allowFiles) {
		if (StringUtils.isBlank(fileName)) {
            return false;
        }

		if (allowFiles == null || allowFiles.length == 0) {
            return true;
        }

		Iterator<String> type = Arrays.asList(allowFiles).iterator();
		while (type.hasNext()) {
			String ext = type.next();
			if (fileName.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @return .png，.jpg ...
	 */
	public static String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param filePath
	 * @return png，jpg ...
	 */
	public static String getFileSuffix(String filePath) {
		String suffix = null;
		int nPos = filePath.lastIndexOf('.');

		if (nPos != -1) {
			suffix = filePath.substring(nPos + 1);
		}

		return suffix;
	}

	/**
	 * 获取文件名
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		File file = new File(filePath);
		String name = file.getName();
		return name;
	}

	/**
	 * 获取文件目录路径
	 * @param filePath
	 * @return
	 */
	public static String getFilePath(String filePath) {
		String path = null;
		int nPos = filePath.lastIndexOf("/");

		if (nPos == -1) {
			nPos = filePath.lastIndexOf("\\");
		}

		if (nPos != -1) {
			path = filePath.substring(0, nPos + 1);
		}

		return path;
	}

	/**
	 * 文件转byte
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray(String filePath) throws IOException {
		File file = new File(filePath);
		return toByteArray(file);
	}

	/**
	 * 文件转byte
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray(File file) throws IOException {
		return toByteArray(new FileInputStream(file));
	}
	
	/**
	 * 文件流转byte
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray(InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(is.available());
		BufferedInputStream in = null;
		
		try {
			in = new BufferedInputStream(is);
			int bufSize = 1024;
			byte[] buffer = new byte[bufSize];
			int len = 0;

			while (-1 != (len = in.read(buffer, 0, bufSize))) {
				bos.write(buffer, 0, len);
			}

			return bos.toByteArray();
		} finally {
			if (bos != null) {
				bos.close();
			}

			if (in != null) {
				in.close();
			}
		}
	}
	
	/**
	 * 创建文件
	 * @param b
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static File createFile(byte[] b, String filePath) throws IOException {
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		File file = null;

		try {
			file = new File(filePath);

			if (!file.exists()) {
				file.createNewFile();
			}

			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(b);
			bos.flush();
		} finally {
			if (bos != null) {
				bos.close();
			}

			if (fos != null) {
				fos.close();
			}
		}
		return file;
	}

	/**
	 * 删除文件
	 * @param filePath
	 * @return
	 */
	public static boolean delete(String filePath) {
		File file = new File(filePath);

		if (file.isFile()) {
			return deleteFile(filePath);
		} else {
			return deleteDirectory(filePath);
		}
	}

	/**
	 * 删除文件
	 * @param filePath
	 * @return
	 */
	private static boolean deleteFile(String filePath) {
		File file = new File(filePath);

		if (file.exists()) {
			file.delete();
		}

		return true;
	}

	/**
	 * 删除文件夹
	 * @param filePath
	 * @return
	 */
	private static boolean deleteDirectory(String filePath) {
		if (!filePath.endsWith(File.separator)) {
			filePath = filePath + File.separator;
		}

		File dirFile = new File(filePath);

		if (dirFile.exists()) {
			File[] files = dirFile.listFiles();

			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					deleteFile(files[i].getAbsolutePath());
				} else {
					deleteDirectory(files[i].getAbsolutePath());
				}
			}
		}

		dirFile.delete();
		return true;
	}
}
