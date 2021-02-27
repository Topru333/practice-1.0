package sort.methods;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortQuick extends SortMethod {

	@Override
	public void Sort(File inputFile, File outputFile) throws FileNotFoundException {
		try {
			if (inputFile.length() > MAX_SIZE) {
				File lowerFile = File.createTempFile("quicksort-", "-lower.tmp", new File("."));
				File greaterFile = File.createTempFile("quicksort-", "-greater.tmp", new File("."));
				DataOutputStream lower = createDataOutput(lowerFile);
				DataOutputStream greater = createDataOutput(greaterFile);
				DataOutputStream target = null;
				DataInputStream input = createDataInput(inputFile);
				long pivot = readBytes(input, BYTE_LENGTH);

				while (true) {
					try {
						long current = readBytes(input, BYTE_LENGTH);
						// If will be only < can stuck with lowest number
						if (current <= pivot) {
							target = lower;
						} else {
							target = greater;
						}
						writeBytes(target, BYTE_LENGTH, current);
					} catch (EOFException e) {
						System.out.println("End of file");
						break;
					}
				}
				writeBytes(greater, BYTE_LENGTH, pivot);
				greater.close();
				lower.close();
				input.close();

				Sort(lowerFile, outputFile);
				lowerFile.delete();
				Sort(greaterFile, outputFile);
				greaterFile.delete();
			} else {
				List<Long> smallFileNumbers = new ArrayList<Long>();
				DataInputStream input = createDataInput(inputFile);

				while (true) {
					try {
						smallFileNumbers.add(readBytes(input, BYTE_LENGTH));

					} catch (EOFException e) {
						System.out.println("End of file");
						break;
					}
				}

				Collections.sort(smallFileNumbers);
				DataOutputStream output = createDataOutput(outputFile);
				for (long number : smallFileNumbers) {
					writeBytes(output, BYTE_LENGTH, number);
				}
				input.close();
				output.close();
			}
		} catch (IOException e) {
			System.out.println("Read error");
			e.printStackTrace();
		}
	}

}
