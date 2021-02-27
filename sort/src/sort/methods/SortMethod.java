package sort.methods;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.text.NumberFormat;

public abstract class SortMethod {
	protected final ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
	protected final int BYTE_LENGTH = 8;
	protected final Runtime runtime = Runtime.getRuntime();
	static final int MAX_SIZE = 1024 * 1024 * 16; // 16 megabytes in this sample, the more memory your program has, less
													// disk writing will be used.
	
	public abstract void Sort(File inputFile, File outputFile) throws FileNotFoundException;
	
	protected static DataOutputStream createDataOutput(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		return new DataOutputStream(new BufferedOutputStream(fos));
	}

	protected static DataInputStream createDataInput(File file) throws IOException {
		FileInputStream file_in = new FileInputStream(file);
		return new DataInputStream(new BufferedInputStream(file_in));
	}
	
	protected long readBytes(DataInputStream d, int byte_length) throws IOException {
		ByteBuffer bb = ByteBuffer.allocate(byte_length).order(byteOrder);
		for (int i = 0; i < byte_length; i++)
			bb.put(d.readByte());
		bb.rewind();
		return bb.getLong();
	}
	
	protected void writeBytes(DataOutputStream d, int byte_length, long number) throws IOException {
		d.write(ByteBuffer.allocate(byte_length).order(byteOrder).putLong(number).array());
	}
	
	protected String MemInfo() {
		NumberFormat format = NumberFormat.getInstance();
		StringBuilder sb = new StringBuilder();
		long maxMemory = runtime.maxMemory();
		long allocatedMemory = runtime.totalMemory();
		long freeMemory = runtime.freeMemory();
		sb.append("Used memory: ");
		sb.append(format.format((allocatedMemory / 1024) - (freeMemory / 1024)));
		sb.append(" kb\n");
		sb.append("Free memory: ");
		sb.append(format.format(freeMemory / 1024));
		sb.append(" kb\n");
		sb.append("Allocated memory: ");
		sb.append(format.format(allocatedMemory / 1024));
		sb.append(" kb\n");
		sb.append("Max memory: ");
		sb.append(format.format(maxMemory / 1024));
		sb.append(" kb\n");
		sb.append("Total free memory: ");
		sb.append(format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024));
		sb.append(" kb\n");
		return sb.toString();
	}

	@SuppressWarnings("resource")
	protected static void copyFileUsingChannel(File source, File dest) throws IOException {
		FileChannel sourceChannel = null;
		FileChannel destChannel = null;
		try {
			sourceChannel = new FileInputStream(source).getChannel();
			destChannel = new FileOutputStream(dest).getChannel();
			destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
		} finally {
			sourceChannel.close();
			destChannel.close();
		}
	}

}
