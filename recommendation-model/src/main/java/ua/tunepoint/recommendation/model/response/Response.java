package ua.tunepoint.recommendation.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.tunepoint.recommendation.model.response.payload.RecommendationPayload;
import ua.tunepoint.web.model.CommonResponse;

import java.io.Serializable;

@SuperBuilder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response extends CommonResponse<RecommendationPayload> implements Serializable {

}
