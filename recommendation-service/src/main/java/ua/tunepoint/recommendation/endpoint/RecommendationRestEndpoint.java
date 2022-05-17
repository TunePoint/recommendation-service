package ua.tunepoint.recommendation.endpoint;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.tunepoint.recommendation.api.RecommendationEndpoint;
import ua.tunepoint.recommendation.model.request.Request;
import ua.tunepoint.recommendation.model.response.Response;
import ua.tunepoint.recommendation.service.ModelService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RecommendationRestEndpoint implements RecommendationEndpoint {

    private final ModelService service;

    @Override
    public ResponseEntity<Response> recommendations(@NonNull @RequestBody @Valid Request request) {
        return ResponseEntity.ok(Response.builder().payload(service.recommendations(request))
                .build());
    }
}
