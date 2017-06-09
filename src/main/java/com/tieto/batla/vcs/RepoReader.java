package com.tieto.batla.vcs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Closeable;

@RequiredArgsConstructor
@Getter
public abstract class RepoReader implements Runnable, Closeable {
    private final VcsReaderConfig.ConfigItem config;
}
