package com.ksoot.spark;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/spark-jobs")
@Tag(name = "Spark Job", description = "APIs")
@RequiredArgsConstructor
@Slf4j
class SparkJobController {

  private final SparkSubmitter sparkSubmitter;

  @Operation(operationId = "submit-spark-job", summary = "Submit Spark Job")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "202", description = "Spark Job submitted successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
      })
  @GetMapping
  ResponseEntity<String> submitSparkJob() {
    this.sparkSubmitter.executeSparkSubmit();
    return ResponseEntity.accepted().body("Spark Job submitted successfully");
  }
}
