package com.tieto.batla.vcs;

import com.aragost.javahg.BaseRepository;
import com.aragost.javahg.Repository;
import com.aragost.javahg.RepositoryConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedIOException;
import org.springframework.core.NestedRuntimeException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Slf4j
public class HgRepoReader extends RepoReader {

    private BaseRepository repo;
    private Path repoDir;

    public HgRepoReader(VcsReaderConfig.ConfigItem config) {
        super(config);
    }

    @Override
    public void run() {

        if (repo == null) {
            if (repoDir != null) {
                try {
                    close();
                } catch (IOException e) {
                    throw new RepoReaderException(e);
                }
            }
            repoDir = createRepoDir();
            repo = Repository.clone(RepositoryConfiguration.DEFAULT, repoDir.toFile(), getConfig().getUrl());
        }
    }

    private Path createRepoDir() {
        try {
            return Files.createTempDirectory(getConfig().getName());
        } catch(IOException ex) {
            throw new RepoReaderException(ex);
        }
    }

    @Override
    public void close() throws IOException {
        if (repoDir != null) {
            Files.deleteIfExists(repoDir);
        }
    }

    private static class RepoReaderException extends RuntimeException {
        public RepoReaderException(Throwable cause) {
            super(cause);
        }
    }
}
