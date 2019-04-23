package net.mfjassociates.tools;

import static java.nio.charset.CoderResult.OVERFLOW;
import static java.nio.charset.CoderResult.UNDERFLOW;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TestUTF8StreamDecode {
	
	private static byte[] utf8bytes;
	private static CharsetStreamSupport css;
	private static CharsetDecoder csDecoder;
	private static final Logger logger=LoggerFactory.getLogger(TestUTF8StreamDecode.class);
	
	@BeforeAll
	public static void createBuffers() {
		css = new CharsetStreamSupport("UTF-8");
		utf8bytes=new String("nç").getBytes(css.getCharset());

	}

	@Test
	void testDecode3() {
		decodeTest(3, UNDERFLOW, 0, utf8bytes.length, true);
	}
	@Test
	void testDecode2() {
		decodeTest(2, UNDERFLOW, 0, utf8bytes.length, true);
	}
	@Test
	void testDecode2bis() {
		decodeTest(2, UNDERFLOW, 0, utf8bytes.length, true);
	}
	@Test
	void testDecode1() {
		decodeTest(1, OVERFLOW, 0, utf8bytes.length, true);
	}
	private void decodeTest(int size, CoderResult expected, int position, int limit, boolean eoi) {
		CharBuffer cb=CharBuffer.allocate(size);
		ByteBuffer bb=ByteBuffer.wrap(utf8bytes, position, limit);
		csDecoder.reset();
		CoderResult res = css.decode(cb, bb, eoi);
		logger.info("CharBuffer size={}, byte[] limit={}, ByteBuffer remaining={}", size, limit, bb.remaining());
		csDecoder.decode(bb, cb, true);
		csDecoder.flush(cb);
		assertEquals(expected, res);
	}

}
