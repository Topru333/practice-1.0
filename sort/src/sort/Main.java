package sort;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import sort.methods.SortMethod;
import sort.methods.SortQuick;

public class Main {

	public static void main(String[] args) {
		File output = new File("result");
		if (output.exists()) {
			output.delete();
			try {
				output.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		File input = new File("binfile");
		if (!output.exists()) {
			throw new RuntimeException("Need file to sort");
		}
		SortMethod logic = new SortQuick();
		try {
			//logic.Create();
			logic.Sort(input, output);
		} catch (FileNotFoundException e) {
			System.out.println ("File Not Found");
		}

	}

}
