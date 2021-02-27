package sort.methods;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SortBubble extends SortMethod {

	@Override
	public void Sort(File inputFile, File outputFile)  throws FileNotFoundException  {
		try {
			File tempFirstFile = File.createTempFile("bubblesort-", "-first.tmp", new File("."));
			File tempSecondFile = File.createTempFile("bubblesort-", "-second.tmp", new File("."));
			
			copyFileUsingChannel(inputFile, tempFirstFile);
			
			DataInputStream input;
			DataOutputStream output;

			long length = inputFile.length() / BYTE_LENGTH;

			long current = 0;
			long before = 0;
			long tmpNumber = 0;

			for (int i = 0; i < length - 1; i++) {
				input = createDataInput(tempFirstFile);
				output = createDataOutput(tempSecondFile);
				current = readBytes(input, BYTE_LENGTH);
				for (int j = 0; true; j++) {
					if (j >= length - i - 1) {
						break;
					}
					try {
						before = current;
						current = readBytes(input, BYTE_LENGTH);
						if (current < before) {
							tmpNumber = current;
							current = before;
							before = tmpNumber;
						}
						writeBytes(output, BYTE_LENGTH, before);
					} catch (EOFException e) {
						System.out.println("End of file");
						writeBytes(output, BYTE_LENGTH, current);
						break;
					}
				}
				
				input.close();
				output.close();
				File tmpSwap = tempFirstFile;
				tempFirstFile = tempSecondFile;
				tempSecondFile = tmpSwap;

			}
			copyFileUsingChannel(tempFirstFile, outputFile);

		} catch (IOException e) {
			System.out.println("Read error");
			e.printStackTrace();
		}
	}

}
