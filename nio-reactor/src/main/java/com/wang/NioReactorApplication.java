package com.wang;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.concurrent.*;


@EnableAsync
@SpringBootApplication
public class NioReactorApplication {

	public static void main(String[] args) {
		SpringApplication.run(NioReactorApplication.class, args);
	}

	ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
			.setNameFormat("study-pool-%d").build();

	ExecutorService pool = new ThreadPoolExecutor(8, 200,
			0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.DiscardPolicy());

	@Bean
	CommandLineRunner run() {
		return (args)-> {
			Arrays.stream(args).forEach(a -> {
				System.out.println(a);
			});
//			while (true) {
//				System.out.println(args);
////				pool.execute(() -> runStr());
//			}
		};
	}

	void runStr() {
		System.out.println(Thread.currentThread().getName() + ": run");
	}

}
