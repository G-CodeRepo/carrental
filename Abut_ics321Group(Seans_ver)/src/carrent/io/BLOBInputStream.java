package carrent.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Convenience wrapper for ByteArrayInputStream which takes a String 
 * as input and converts it to a byte array, which is then fed into 
 * the input stream.
 * 
 * @version 1.0
 * @deprecated As of {@link carrent.entity.Car Car} version 2.8, the function of this class
 * 			   is fully contained within Car's constructor.
 */
public class BLOBInputStream extends InputStream{
	
	/**
	 * Internal input stream. All input stream methods are
	 * actually invoked on this object.
	 */
	private ByteArrayInputStream input;
	
	public BLOBInputStream(String blob){
		
		byte[] bytes = new byte[blob.length()];
		
		for(int i = 0; i < bytes.length; i++){
			bytes[i] = (byte) blob.charAt(i);
		}
		
		input = new ByteArrayInputStream(bytes);
		
	}
	
	@Override
	public int available(){
		return input.available();
	}
	
	@Override
	public void close() throws IOException{
		input.close();
	}
	
	@Override
	public void mark(int readAheadLimit){
		input.mark(readAheadLimit);
	}
	
	@Override
	public boolean markSupported(){
		return input.markSupported();
	}

	@Override
	public int read() throws IOException {
		return input.read();
	}
	
	@Override
	public int read(byte[] b, int off, int len){
		return input.read(b, off, len);
	}
	
	@Override
	public void reset(){
		input.reset();
	}
	
	@Override
	public long skip(long n){
		return input.skip(n);
	}
	
	
	
}
