package ua.tunepoint.recommendation.model.response.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.tunepoint.recommendation.model.domain.Item;
import ua.tunepoint.recommendation.model.domain.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationPayload {

    private User user;
    private List<Integer> items;
}
