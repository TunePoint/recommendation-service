package ua.tunepoint.recommendation.service;


import ua.tunepoint.recommendation.model.request.Request;
import ua.tunepoint.recommendation.model.response.Response;
import ua.tunepoint.recommendation.model.response.payload.RecommendationPayload;

public interface ModelService {
    boolean update(Integer u, Integer i);
    void build();
    Integer addUser(Integer id);
    Integer addItem(Integer id);
    boolean deleteUser(Integer id);
    boolean deleteItem(Integer id);
    RecommendationPayload recommendations(Request request);
}
