package com.ksoot.spark;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SparkJobEvent {

  private String fileName = "output";

  private String format = "csv";
}
