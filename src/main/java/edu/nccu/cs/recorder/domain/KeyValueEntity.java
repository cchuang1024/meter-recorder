package edu.nccu.cs.recorder.domain;

import java.util.List;

import edu.nccu.cs.recorder.exception.SystemException;
import edu.nccu.cs.recorder.util.TypedPair;

public interface KeyValueEntity {
    byte[] getKey();

    byte[] getValue() throws SystemException;

    List<TypedPair<byte[]>> getKeyValuePairs() throws SystemException;
}
