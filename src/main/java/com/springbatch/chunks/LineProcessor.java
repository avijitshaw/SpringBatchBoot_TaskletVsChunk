package com.springbatch.chunks;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;

import com.springbatch.model.Line;

public class LineProcessor implements ItemProcessor<Line, Line>, StepExecutionListener {

	@Override
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("Line Processor initialized.");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("Line Processor ended.");
		return ExitStatus.COMPLETED;
	}

	@Override
	public Line process(Line line) throws Exception {
		long age = ChronoUnit.YEARS.between(line.getDob(), LocalDate.now());
        System.out.println("Calculated age " + age + " for line " + line.toString());
        line.setAge(age);
        return line;
	}

}
