package jiagou.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ClassUtil {
	private static final Logger log = LoggerFactory.getLogger(ClassUtil.class);

	public static ClassLoader getClassLoad() {
		return Thread.currentThread().getContextClassLoader();
	}

	public static Class<?> loadClass(String className, boolean isInit) {
		Class<?> clz = null;
		try {
			clz = Class.forName(className, isInit, getClassLoad());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error("load class error");
		}
		return clz;
	}

	public static Set<Class<?>> getClassSet(String packageName) {
		Set<Class<?>> classSet = new HashSet<>();
		try {
			Enumeration<URL> urls = getClassLoad().getResources(packageName.replace(".", "/"));
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				String protocol = url.getProtocol();
				if ("file".equals(protocol)) {
					String packagePath = url.getPath().replace("%20", "");
					addClass(classSet, packagePath, packageName);
				} else if ("jar".equals(protocol)) {
					// TODO
					JarURLConnection conn = (JarURLConnection) url.openConnection();
					JarFile jarFile = conn.getJarFile();
					Enumeration<JarEntry> entries = jarFile.entries();
					while (entries.hasMoreElements()) {
						JarEntry jarEntry = entries.nextElement();
						String name = jarEntry.getName();
						if (name.endsWith(".class")) {
							String className = name.substring(0, name.lastIndexOf(".")).replaceAll("/", ".");
							doAddClass(classSet, className);
						}
					}

				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 区分.class 文件
	 * 
	 * @param classSet
	 * @param packagePath
	 * @param packageName
	 */
	private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
		File[] files = new File(packagePath).listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory() || (pathname.isFile() && pathname.getName().endsWith(".class"));
			}
		});
		for (File file : files) {
			String fileName = file.getName();
			if (file.isFile()) {
				String className = fileName.substring(0, fileName.lastIndexOf("."));
				if (StringUtils.isNotEmpty(packageName))
					className = packageName + className;
				doAddClass(classSet, className);
			} else {
				String subPackagePath = fileName;
				if (StringUtils.isNotEmpty(packagePath))
					subPackagePath = packagePath + "/" + subPackagePath;
				String subPackageName = fileName;
				if (StringUtils.isNotEmpty(packageName))
					subPackageName = packageName + "." + subPackageName;
				addClass(classSet, subPackagePath, subPackageName);
			}
		}
	}

	private static void doAddClass(Set<Class<?>> classSet, String className) {
		Class<?> clz = loadClass(className, false);
		classSet.add(clz);
	}
}
