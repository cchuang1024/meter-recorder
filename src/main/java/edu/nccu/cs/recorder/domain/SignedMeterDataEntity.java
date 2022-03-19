package edu.nccu.cs.recorder.domain;

import java.time.Instant;
import java.util.Base64;

import edu.nccu.cs.recorder.exception.SystemException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static edu.nccu.cs.recorder.util.ByteUtils.getBytesFromLong;
import static edu.nccu.cs.recorder.util.DataConvertUtils.cborFromObject;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SignedMeterDataEntity implements KeyValueEntity, TemporalEntity {

    private long timestamp;
    private SignedMeterDataValue data;

    public static synchronized SignedMeterDataEntity getInstanceByInstantAndData(Instant instant, SignedMeterData data) {
        return SignedMeterDataEntity.builder()
                                    .timestamp(instant.toEpochMilli())
                                    .data(SignedMeterDataValue.builder()
                                                              .energy(data.getMeterData().getEnergy())
                                                              .power(data.getMeterData().getPower())
                                                              .signature(Base64.getDecoder().decode(data.getSignature()))
                                                              .build())
                                    .build();
    }

    public static synchronized SignedMeterDataEntity getInstanceByTimestampAndData(long timestamp, SignedMeterDataValue data) {
        return SignedMeterDataEntity.builder()
                                    .timestamp(timestamp)
                                    .data(data)
                                    .build();
    }

    @Override
    public byte[] getKey() {
        return getBytesFromLong(this.timestamp);
    }

    @Override
    public byte[] getValue() throws SystemException {
        return cborFromObject(this.data);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class SignedMeterDataValue {
        private long power;
        private long energy;
        private byte[] signature;
    }
}
