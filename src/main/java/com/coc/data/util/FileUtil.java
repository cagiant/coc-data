package com.coc.data.util;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author guokaiqiang
 * @date 2021/6/12 17:08
 */
public class FileUtil {

	public static String readFileFromClassPath(String fileInClassPath) throws IOException {
		// ClassPathResource类的构造方法接收路径名称，自动去classpath路径下找文件
		ClassPathResource classPathResource = new ClassPathResource("file/league_group.json");

		// 获得File对象，当然也可以获取输入流对象
		File file = classPathResource.getFile();

		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		StringBuilder content = new StringBuilder();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			content.append(line);
		}

		return content.toString();
	}
}
