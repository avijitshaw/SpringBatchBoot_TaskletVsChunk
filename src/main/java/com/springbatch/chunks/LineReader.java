package com.springbatch.chunks;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.repeat.RepeatStatus;

import com.springbatch.model.Line;
import com.springbatch.utils.FileUtils;

public class LineReader implements ItemReader<Line>, StepExecutionListener{

	private FileUtils fu;
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		fu = new FileUtils("user.csv");
		System.out.println("Lines Reader initialized.");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		fu.closeReader();
        System.out.println("Line Reader ended.");
        return ExitStatus.COMPLETED;
	}

	@Override
	public Line read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		Line line = fu.readLine();
		while (line != null) {
			System.out.println("Read Line in chunk: " + line);
			line = fu.readLine();
		}
		return line;
	}

}
