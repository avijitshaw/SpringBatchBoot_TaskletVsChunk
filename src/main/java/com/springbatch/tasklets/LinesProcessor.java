package com.springbatch.tasklets;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.springbatch.model.Line;

@Component
public class LinesProcessor implements Tasklet, StepExecutionListener {

	private List<Line> lines;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		this.lines = (List<Line>) executionContext.get("lines");
		System.out.println("Lines Processor initialized.");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("Lines Processor ended.");
		return ExitStatus.COMPLETED;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		for (Line line : lines) {
			long age = ChronoUnit.YEARS.between(line.getDob(), LocalDate.now());
			System.out.println("Calculated age " + age + " for line " + line.toString());
			line.setAge(age);
		}
		return RepeatStatus.FINISHED;
	}

}
