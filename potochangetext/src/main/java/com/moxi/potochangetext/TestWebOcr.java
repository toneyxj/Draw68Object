package com.moxi.potochangetext;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class TestWebOcr {
	// 手写文字识别webapi接口地址
	private static final String WEBOCR_URL = "http://webapi.xfyun.cn/v1/service/v1/ocr/handwriting";
	// 测试应用ID
	private static final String TEST_APPID = "5c735121";
	// 测试接口密钥
	private static final String TEST_API_KEY = "";
	// 测试图片文件存放位置
	private static final String IMAGE_FILE_PATH = "";

	/**
	 * 组装http请求头
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ParseException 
	 */
	private static Map<String, String> constructHeader(String language, String location) throws UnsupportedEncodingException, ParseException {
		// 系统当前时间戳
		String X_CurTime = System.currentTimeMillis() / 1000L + "";
		// 业务参数
		String param = "{\"language\":\""+language+"\""+",\"location\":\"" + location + "\"}";
		String X_Param = new String(Base64.encodeBase64(param.getBytes("UTF-8")));
		// 接口密钥
		String apiKey = TEST_API_KEY;
		// 讯飞开放平台应用ID
		String X_Appid = TEST_APPID;
		// 生成令牌
		String X_CheckSum = DigestUtils.md5Hex(apiKey + X_CurTime + X_Param);

		// 组装请求头
		Map<String, String> header = new HashMap<String, String>();
		header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		header.put("X-Param", X_Param);
		header.put("X-CurTime", X_CurTime);
		header.put("X-CheckSum", X_CheckSum);
		header.put("X-Appid", X_Appid);
		return header;
	}

	public static void main(String[] args) throws IOException,ParseException{
		Map<String, String> header = constructHeader("en", "false");
		// 读取图像文件，转二进制数组，然后Base64编码
		byte[] imageByteArray = FileUtil.read2ByteArray(IMAGE_FILE_PATH);
		String imageBase64 = new String(Base64.encodeBase64(imageByteArray), "UTF-8");
		String bodyParam = "image=" + imageBase64;
		String result = HttpUtil.doPost(WEBOCR_URL, header, bodyParam);
		System.out.println(result);
	}
}
