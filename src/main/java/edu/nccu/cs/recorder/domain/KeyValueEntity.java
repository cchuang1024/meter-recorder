package edu.nccu.cs.recorder.domain;

import edu.nccu.cs.recorder.exception.SystemException;

public interface KeyValueEntity {
    byte[] getKey();

    byte[] getValue() throws SystemException;
}
