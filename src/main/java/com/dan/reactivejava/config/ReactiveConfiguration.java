package com.dan.reactivejava.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class ReactiveConfiguration {

    @Value("${webclient.connectTimeout}")
    private Integer connectTimeout;

    @Value("${webclient.responseTimeout}")
    private Integer responseTimeout;

    @Value("${webclient.readTimeout}")
    private Integer readTimeout;

    @Value("${webclient.writeTimeout}")
    private Integer writeTimeout;

    @Value(("${webclient.sslBypass}"))
    private Boolean sslBypass;

    @Bean(name = "webClientBasic")
    public WebClient getWebClientBasic() throws SSLException {
        return WebClient.builder()
                .baseUrl("http://localhost:80")
                .clientConnector(new ReactorClientHttpConnector(getClientConnector()))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private HttpClient getClientConnector() throws SSLException{
        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
                .responseTimeout(Duration.ofMillis(responseTimeout))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(writeTimeout, TimeUnit.MILLISECONDS)))
                .secure(sslContextSpec -> {
                    if(sslBypass){
                        sslContextSpec.sslContext(sslContext);
                    }
                });
    }

}
