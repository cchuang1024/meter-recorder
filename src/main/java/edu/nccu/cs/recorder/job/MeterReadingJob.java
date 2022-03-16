package edu.nccu.cs.recorder.job;

import java.net.URI;
import java.util.concurrent.Callable;

import edu.nccu.cs.recorder.domain.MeterData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
@Slf4j
public class MeterReadingJob implements Callable<MeterData> {

    @Value("${meter.host}")
    private String meterHost;

    @Value("${meter.port}")
    private Integer meterPort;

    @Override
    public MeterData call() throws Exception {
        String url = String.format("http://%s:%d", meterHost, meterPort);
        log.warn("url: {}", url);
        WebClient client = WebClient.create(url);

        Mono<MeterData> resp =
                client.get()
                      .uri("/normal")
                      .accept(MediaType.APPLICATION_JSON)
                      .retrieve()
                      .bodyToMono(MeterData.class);

        return resp.block();
    }
}
