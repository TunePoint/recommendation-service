package ua.tunepoint.recommendation.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tunepoint.recommendation.model.request.Request;
import ua.tunepoint.recommendation.model.response.Response;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface RecommendationEndpoint {

    @ResponseBody
    @RequestMapping(
            value = "/recommendations",
            method = RequestMethod.POST,
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    ResponseEntity<Response> recommendations(@RequestBody Request request);
}
