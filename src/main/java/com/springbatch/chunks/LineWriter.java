package com.springbatch.chunks;

import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;

import com.springbatch.model.Line;

public class LineWriter implements ItemWriter<Line>, StepExecutionListener {

	@Override
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("Lines Writer initialized.");		
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("Lines Writer ended.");
        return ExitStatus.COMPLETED;
	}

	@Override
	public void write(List<? extends Line> lines) throws Exception {
		for (Line line : lines) {
            System.out.println("Wrote line " + line.toString());
        }
	}

}
