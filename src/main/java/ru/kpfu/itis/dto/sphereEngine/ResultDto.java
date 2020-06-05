package ru.kpfu.itis.dto.sphereEngine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultDto {
    private ResultDto.Status status;
    private Double time;
    private ResultDto.Streams streams;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Status {
        private Long code;
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Streams {
        private Streams.Source source;
        private Streams.Output output;
        private Streams.Error error;
        private Streams.Cmpinfo cmpinfo;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Source {
            private Long size;
            private String uri;
        }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Output {
            private Long size;
            private String uri;
        }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Error {
            private Long size;
            private String uri;
        }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Cmpinfo {
            private Long size;
            private String uri;
        }
    }
}
