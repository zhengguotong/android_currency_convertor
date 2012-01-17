package i11114675.zheng.currencyConvertor.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * read Stream tools
 * 
 * @author zhengguotong
 *
 */
public class StreamTool {
	
	/**
	 * reads data from inputStream
	 * 
	 * @param inStream
	 * @return outputStream as a byte array
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		// outStream for writing content to an (internal) byte array
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		// writes stream buffer outStream until the end of
		// the stream has been reached
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		inStream.close();
		return outStream.toByteArray();
	}
}
