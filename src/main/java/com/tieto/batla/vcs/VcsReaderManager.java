package com.tieto.batla.vcs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@SuppressWarnings("unused")
public class VcsReaderManager implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private VcsReaderConfig config;

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

    private final List<RepoReader> repoReaders = new ArrayList<>();

    @PreDestroy
    private void shutdown() {
        executor.shutdown();
        for (Closeable c : repoReaders) {
            try {
                c.close();
            } catch (IOException e) {
                log.warn("Closing resource", e);
            }
        }
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        for (VcsReaderConfig.ConfigItem cfg : config.getRepositories()) {
            cfg.setReadInterval(cfg.getReadInterval() == null ? config.getDefaultReadInterval() : cfg.getReadInterval());
            switch(cfg.getType()) {
                case "hg":
                    repoReaders.add(new HgRepoReader(cfg));
                    break;
                default:
                    log.error("Unknown repository type: " + cfg.getType() + " for " + cfg);
                    break;
            }
        }
        for (RepoReader reader : repoReaders) {
            executor.scheduleAtFixedRate(reader, 0L, reader.getConfig().getReadInterval(), TimeUnit.MINUTES);
        }

    }
}
