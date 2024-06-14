package com.ksoot.spark;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SparkSubmitter {

  private static final String SPARK_HOME = System.getenv("SPARK_HOME");
  //  private static final String SPARK_HOME =
  // "/Users/shubham.rao/spark-3.4.1-bin-hadoop3-scala2.13";
  private static final String SPARK_SUBMIT_SCRIPT = "./bin/spark-job-submit.sh";
  private static final ExecutorService executor = Executors.newSingleThreadExecutor();

  public void executeSparkSubmit() {
    log.info("============================================================");
    log.info("SPARK_HOME: {}, SPARK_SUBMIT_SCRIPT: {}", SPARK_HOME, SPARK_SUBMIT_SCRIPT);
    try {
      final ProcessBuilder processBuilder = new ProcessBuilder(SPARK_SUBMIT_SCRIPT);
      File directory = new File(SPARK_HOME);
      // Get the environment variables map
      Map<String, String> environment = processBuilder.environment();

      // Set new environment variables or modify existing ones
      //      environment.put("MASTER_URL", "https://127.0.0.1:49891");
      environment.put("MASTER_URL", "https://kubernetes.default.svc");
      environment.put("DEPLOY_MODE", "cluster");
      environment.put("JOB_CLASS_NAME", "com.ksoot.spark.SparkSpringCloudTask");
      environment.put("JOB_NAME", "spark-spring-cloud-task");
      environment.put("JOB_IMAGE_TAG", "0.0.1");
      //    environment.put("EXECUTOR_INSTANCES", "2");
      //    environment.put("SERVICE_ACCOUNT_NAME", "spark");
      //    environment.put("SPARK_USER", "spark");
      log.info("Environment variables: " + environment);

      processBuilder.directory(directory);
      // Start the process
      final Process process = processBuilder.inheritIO().start();
      final StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), log::info);
      final StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), log::error);

      executor.submit(outputGobbler);
      executor.submit(errorGobbler);

      // Wait for the process to complete
      int exitCode = process.waitFor();

      // Get the inputs.waitFor();
      log.info(
          "spark-submit completed with code: {}, status: {}",
          exitCode,
          (exitCode == 0 ? "SUCCESS" : "FAILURE"));
      log.info("============================================================");
    } catch (Exception e) {
      log.error("Error executing spark-submit command: {}", e);
      e.printStackTrace();
    }
  }

  class StreamGobbler implements Runnable {
    private final InputStream inputStream;
    private final Consumer<String> consumer;

    StreamGobbler(final InputStream inputStream, final Consumer<String> consumer) {
      this.inputStream = inputStream;
      this.consumer = consumer;
    }

    @Override
    public void run() {
      new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
    }
  }
}
