package ua.tunepoint.recommendation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.tunepoint.recommendation.alsmodel.loader.ModelLoader;
import ua.tunepoint.recommendation.alsmodel.repository.ItemRepository;
import ua.tunepoint.recommendation.alsmodel.repository.ModelRepository;
import ua.tunepoint.recommendation.alsmodel.repository.UserRepository;
import ua.tunepoint.recommendation.config.properties.ModelConfig;
import ua.tunepoint.recommendation.config.properties.ModelInitializerProperties;
import ua.tunepoint.recommendation.exception.BusinessException;
import ua.tunepoint.recommendation.mf.algorithm.als.EALSModel;
import ua.tunepoint.recommendation.model.domain.User;
import ua.tunepoint.recommendation.model.request.Request;
import ua.tunepoint.recommendation.model.response.payload.RecommendationPayload;
import ua.tunepoint.web.exception.NotFoundException;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ModelServiceImpl implements ModelService {

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    EALSModel model;

    @Autowired
    ModelInitializerProperties initProps;

    @Autowired
    ModelLoader modelLoader;

    @Autowired
    @Qualifier("modelConfig")
    ModelConfig config;

    @PostConstruct
    public void init() {
        model = modelLoader.load(config.getConfig());

        if (initProps.getTrain()) {
            this.build();
        }
    }

    @Override
    public boolean update(Integer u, Integer i) {
        Integer modelUserId = userRepository.findModelId(u)
                .orElseThrow(() -> new BusinessException("Mapping for user " + u + " wasn't found"));

        Integer modelItemId = itemRepository.findModelId(i)
                .orElseThrow(() -> new BusinessException("Mapping for item " + i + " wasn't found"));

        model.updateModel(modelUserId, modelItemId);

        return modelRepository.update(model, u, i);
    }

    @Override
    public void build() {
        model.buildModel();

        if (initProps.getSaveAfterBuild()) {
            modelRepository.save(model);

            if (initProps.getSaveItemIdPool()) {
                int itemPool = model.getItemCount();
                itemRepository.saveIdPool(itemPool);
            }

            if (initProps.getSaveUserIdPool()) {
                int userPool = model.getUserCount();
                userRepository.saveIdPool(userPool);
            }
        }
    }

    @Override
    public Integer addUser(Integer id) {
        if (userRepository.existsByOuterId(id)) {
            throw new BusinessException("User with id " + id + " already exists");
        }

        int modelId = model.addUser();
        userRepository.save(id, modelId);
        modelRepository.saveUser(model, modelId);

        return modelId;
    }

    @Override
    public Integer addItem(Integer id) {
        if (itemRepository.existsByOuterId(id)) {
            throw new BusinessException("Item with id " + id + " already exists");
        }

        int modelId = model.addItem();
        itemRepository.save(id, modelId);
        modelRepository.saveItem(model, modelId);

        return modelId;
    }

    @Override
    public RecommendationPayload recommendations(Request request) {
        Integer outerId = request.getUserId().intValue();

        Optional<Integer> modelIdOptional = userRepository.findModelId(outerId);

        if (modelIdOptional.isEmpty()) {
            throw new NotFoundException("user with id " + outerId + " was not found");
        }

        List<Integer> items = model
                .getRecommendations(modelIdOptional.get(), request.getItemCount(), request.getExcludeInteracted())
                .stream().map(item -> itemRepository.findOuterId(item))
                .filter(Optional::isPresent).map(Optional::get)
                .collect(Collectors.toList());

        return new RecommendationPayload(
                new User(outerId), items
        );
    }
}
