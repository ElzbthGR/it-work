package ru.kpfu.itis.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoteApiCallService {

    private final RestTemplate restTemplate;

    public <RES> Optional<RES> get(@NonNull final String url,
                                   @NonNull final ParameterizedTypeReference<RES> typeReference) {
        return execute(url, HttpMethod.GET, null, null, typeReference);
    }

    public <RES> Optional<RES> get(@NonNull final String url, final String token,
                                   @NonNull final ParameterizedTypeReference<RES> typeReference) {
        return execute(url, HttpMethod.GET, null, token, typeReference);
    }

    public <RES, REQ> Optional<RES> post(@NonNull final String url, @NonNull final REQ body, final String token,
                                         @NonNull final ParameterizedTypeReference<RES> typeReference) {
        return execute(url, HttpMethod.POST, body, token, typeReference);
    }

    private <RES, REQ> Optional<RES> execute(@NonNull final String url, @NonNull final HttpMethod method,
                                             final REQ body, final String token,
                                             @NonNull final ParameterizedTypeReference<RES> typeReference) throws HttpServerErrorException {
        log.info("Sending request to [{}]", url);

        final HttpHeaders httpHeaders = new HttpHeaders();

        if (method.equals(HttpMethod.POST) || method.equals(HttpMethod.PUT)) {
            httpHeaders.add(HttpHeaders.ACCEPT, "application/json");
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        }
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        ResponseEntity<RES> response;
        if (token == null) {
            response = restTemplate.exchange(
                    url,
                    method,
                    method.equals(HttpMethod.POST) ? new HttpEntity<>(body, httpHeaders) : null,
                    typeReference
            );
        } else {
            response = restTemplate.exchange(
                    UriComponentsBuilder.fromHttpUrl(url)
                            .queryParam("access_token", token).toUriString(),
                    method,
                    method.equals(HttpMethod.POST) ? new HttpEntity<>(body, httpHeaders) : null,
                    typeReference
            );
        }
        return Optional.ofNullable(response.getBody());
    }
}
