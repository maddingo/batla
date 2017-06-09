package com.tieto.batla.vcs;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix="vcsreader")
@Component
@Data
public class VcsReaderConfig {

    @Getter
    private final List<ConfigItem> repositories = new ArrayList<>();

    /**
     * Read interval in seconds.
     */
    private Integer defaultReadInterval;

    @Data
    public static class ConfigItem {

        String name;
        String type;
        String url;
        Integer readInterval;
    }
}
