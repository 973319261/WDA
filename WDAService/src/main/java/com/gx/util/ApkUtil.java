package com.gx.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParser;

import android.content.res.AXmlResourceParser;
import android.util.TypedValue;

public class ApkUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApkUtil.class);

	private static final float RADIX_MULTS[] = { 0.00390625F, 3.051758E-005F, 1.192093E-007F, 4.656613E-010F };

	private static final String DIMENSION_UNITS[] = { "px", "dip", "sp", "pt", "in", "mm", "", "" };

	private static final String FRACTION_UNITS[] = { "%", "%p", "", "", "", "", "", "" };

	/**
	 * 获取apk信息
	 *
	 * @param apkPath
	 * @return
	 */
	public static String[] getApkInfo(String apkPath) throws Exception {
		// apk信息的返回结果
		final String[] apkResult = new String[3];
		ZipFile zipFile = null;
		try {
			// 获得一个解压文件对象
			zipFile = new ZipFile(apkPath);
			// 将解压文件对象转列举对象
			final Enumeration enumeration = zipFile.entries();
			ZipEntry zipEntry = null;
			// 遍历列举对象元素
			while (enumeration.hasMoreElements()) {
				// 获得一个解压条目对象
				zipEntry = (ZipEntry) enumeration.nextElement();
				if (zipEntry.isDirectory()) {

				} else {
					// 获得名为AndroidManifest.xml的文件
					if ("AndroidManifest.xml".equalsIgnoreCase(zipEntry.getName())) {
						final AXmlResourceParser parser = new AXmlResourceParser();
						parser.open(zipFile.getInputStream(zipEntry));
						// 遍历文件里的内容
						while (true) {
							final int type = parser.next();
							if (type == XmlPullParser.END_DOCUMENT) {
								break;
							}
							switch (type) {
							// 满足条件开始遍历内容提取需要的信息
							case XmlPullParser.START_TAG: {
								for (int i = 0; i != parser.getAttributeCount(); ++i) {
									if ("package".equals(parser.getAttributeName(i))) {
										apkResult[0] = ApkUtil.getAttributeValue(parser, i);
									} else if ("versionCode".equals(parser.getAttributeName(i))) {
										apkResult[1] = ApkUtil.getAttributeValue(parser, i);
									} else if ("versionName".equals(parser.getAttributeName(i))) {
										apkResult[2] = ApkUtil.getAttributeValue(parser, i);
									}
								}
							}
							}
						}
					}
				}
			}
		} finally {
			if (zipFile != null) {
				try {
					zipFile.close();
				} catch (final IOException e) {
					LOGGER.error("Zipfile close fail.", e);
				}
			}
		}
		return apkResult;
	}

	private static String getAttributeValue(AXmlResourceParser parser, int index) {
		final int type = parser.getAttributeValueType(index);
		final int data = parser.getAttributeValueData(index);
		if (type == TypedValue.TYPE_STRING) {
			return parser.getAttributeValue(index);
		}
		if (type == TypedValue.TYPE_ATTRIBUTE) {
			return String.format("?%s%08X", ApkUtil.getPackage(data), data);
		}
		if (type == TypedValue.TYPE_REFERENCE) {
			return String.format("@%s%08X", ApkUtil.getPackage(data), data);
		}
		if (type == TypedValue.TYPE_FLOAT) {
			return String.valueOf(Float.intBitsToFloat(data));
		}
		if (type == TypedValue.TYPE_INT_HEX) {
			return String.format("0x%08X", data);
		}
		if (type == TypedValue.TYPE_INT_BOOLEAN) {
			return data != 0 ? "true" : "false";
		}
		if (type == TypedValue.TYPE_DIMENSION) {
			return Float.toString(ApkUtil.complexToFloat(data))
					+ ApkUtil.DIMENSION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
		}
		if (type == TypedValue.TYPE_FRACTION) {
			return Float.toString(ApkUtil.complexToFloat(data))
					+ ApkUtil.FRACTION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
		}
		if (type >= TypedValue.TYPE_FIRST_COLOR_INT && type <= TypedValue.TYPE_LAST_COLOR_INT) {
			return String.format("#%08X", data);
		}
		if (type >= TypedValue.TYPE_FIRST_INT && type <= TypedValue.TYPE_LAST_INT) {
			return String.valueOf(data);
		}
		return String.format("<0x%X, type 0x%02X>", data, type);
	}

	private static String getPackage(int id) {
		if (id >>> 24 == 1) {
			return "android:";
		}
		return "";
	}

	public static float complexToFloat(int complex) {
		return (complex & 0xFFFFFF00) * ApkUtil.RADIX_MULTS[complex >> 4 & 3];
	}
}
