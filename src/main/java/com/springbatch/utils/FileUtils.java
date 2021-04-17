package com.springbatch.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.springbatch.model.Line;

public class FileUtils {

	private String fileName;
	private CSVReader CSVReader;
	private CSVWriter CSVWriter;
	private FileReader fileReader;
	private FileWriter fileWriter;
	private File file;

	public FileUtils(String fileName) {
		this.fileName = fileName;
	}

	public Line readLine() {
        try {
            if (CSVReader == null) initReader();
            String[] line = CSVReader.readNext();
            if (line == null) return null;
            return new Line(line[0], LocalDate.parse(line[1], DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        } catch (Exception e) {
            System.out.println("Error while reading line in file: " + this.fileName);
            return null;
        }
    }
	
	private void initReader() throws Exception {
        ClassLoader classLoader = this
          .getClass()
          .getClassLoader();
        if (file == null) file = new File(classLoader
          .getResource(fileName)
          .getFile());
        if (fileReader == null) fileReader = new FileReader(file);
        if (CSVReader == null) CSVReader = new CSVReader(fileReader);
    }
	
	public void closeReader() {
        try {
            CSVReader.close();
            fileReader.close();
        } catch (IOException e) {
            System.out.println("Error while closing reader.");
        }
    }
}
