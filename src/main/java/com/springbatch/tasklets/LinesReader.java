package com.springbatch.tasklets;

import java.util.ArrayList;
import java.util.List;

import javax.batch.api.listener.StepListener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.springbatch.model.Line;
import com.springbatch.utils.FileUtils;

@Component
public class LinesReader implements Tasklet, StepExecutionListener {

	private List<Line> lines;
	private FileUtils fu;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		Line line = fu.readLine();
		while (line != null) {
			lines.add(line);
			System.out.println("Read Line : " + line);
			line = fu.readLine();
		}
		return RepeatStatus.FINISHED;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		lines = new ArrayList<Line>();
		fu = new FileUtils("user.csv");
		System.out.println("Lines Reader initialized.");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		fu.closeReader();
		stepExecution.getJobExecution().getExecutionContext().put("lines", this.lines);
		System.out.println("Lines Reader ended");
		return ExitStatus.COMPLETED;
	}

}
