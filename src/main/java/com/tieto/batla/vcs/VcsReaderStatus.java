package com.tieto.batla.vcs;

import lombok.Value;

@Value
public class VcsReaderStatus {

    public static final VcsReaderStatus SUCCESS = new VcsReaderStatus(true, null);

    boolean success;
    String message;
}
