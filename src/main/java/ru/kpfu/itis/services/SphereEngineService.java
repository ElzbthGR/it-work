package ru.kpfu.itis.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.dto.sphereEngine.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SphereEngineService {
    private static final String ROOT_URL = "https://0c8f3bad.compilers.sphere-engine.com/api/v4";
    private static final String SUBMISSION_URL = "/submissions";
    private static final String COMPILERS_URL = "/compilers";
    private static final String ONE_URL = "/%s";
    private static final String TOKEN = "0e77e582960661b9c8b49aba2ea0d5fc";
    private static final ParameterizedTypeReference<SubmissionIdDto> submissionIdSearchType =
            new ParameterizedTypeReference<SubmissionIdDto>() {
            };
    private static final ParameterizedTypeReference<SubmissionDto> submissionSearchType =
            new ParameterizedTypeReference<SubmissionDto>() {
            };
    private static final ParameterizedTypeReference<String> stringSearchType =
            new ParameterizedTypeReference<String>() {
            };
    private static final ParameterizedTypeReference<CompilersDto> compilerSearchType =
            new ParameterizedTypeReference<CompilersDto>() {
            };

    private final RemoteApiCallService remoteApiCallService;

    public Long getSubmissionId(final SubmissionForm form) {
        Optional<SubmissionIdDto> submissionId = remoteApiCallService.post(
                ROOT_URL + SUBMISSION_URL, form, TOKEN, submissionIdSearchType);
        return submissionId.get().getId();
    }

    public SubmissionDto get(Long submissionId) {
        String submissionIdUrl = String.format(ONE_URL, submissionId);
        Optional<SubmissionDto> submission = remoteApiCallService.get(
                ROOT_URL + SUBMISSION_URL + submissionIdUrl, TOKEN,submissionSearchType
        );
        return submission.get();
    }

    public String get(String uri) {
        Optional<String> submission = remoteApiCallService.get(
                uri,stringSearchType
        );
        return submission.get();
    }

    public List<CompilerDto> getCompilers() {
        Optional<CompilersDto> compilers = remoteApiCallService.get(
                ROOT_URL + COMPILERS_URL, TOKEN, compilerSearchType
        );
        return compilers.get().getItems();
    }
}
