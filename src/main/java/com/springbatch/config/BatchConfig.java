package com.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.chunks.LineProcessor;
import com.springbatch.chunks.LineReader;
import com.springbatch.chunks.LineWriter;
import com.springbatch.model.Line;
import com.springbatch.tasklets.LinesProcessor;
import com.springbatch.tasklets.LinesReader;
import com.springbatch.tasklets.LinesWriter;

@EnableBatchProcessing
@Configuration
public class BatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	LinesReader linesReader;
	
	@Autowired
	LinesProcessor linesProcessor;
	
	@Autowired
	LinesWriter linesWriter;
	
	@Bean
	public Step readLines() {
		return stepBuilderFactory.get("readLines-tasklet").tasklet(linesReader).build();	
	}
	
	@Bean
	public Step processLines() {
		return stepBuilderFactory.get("processLines-tasklet").tasklet(linesProcessor).build();	
	}
	
	@Bean
	public Step writeLines() {
		return stepBuilderFactory.get("writeLines-tasklet").tasklet(linesWriter).build();
	}
	
	@Bean
	public ItemReader<Line> chunkReader() {
		return new LineReader();
	}
	
	@Bean
	 public ItemProcessor<Line, Line> chunkProcessor(){
		return new LineProcessor();
	}
	
	@Bean
	public ItemWriter<Line> chunkWriter(){
		return new LineWriter();
	}
	
	
	
	@Bean
	public Step readProcessWriteStep() {
		return stepBuilderFactory.get("chunk-step")
				.<Line,Line>chunk(2)
				.reader(chunkReader())
                .processor(chunkProcessor())
                .writer(chunkWriter())
                .build();

	}
	@Bean
	public Job job() {
		return jobBuilderFactory.get("taskletJob")
				.incrementer(new RunIdIncrementer())
//				.start(readLines())
//				.next(processLines())
//				.next(writeLines())
				.start(readProcessWriteStep())
				.build();
	}
}
