package ua.tunepoint.recommendation.alsmodel.loader;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.tunepoint.recommendation.alsmodel.repository.ModelRepository;
import ua.tunepoint.recommendation.config.properties.ModelInitializerProperties;
import ua.tunepoint.recommendation.mf.algorithm.als.EALSModel;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class DatabaseModelLoader implements ModelLoader {

    private final ModelInitializerProperties initializerProperties;
    private final ModelRepository modelRepository;

    @Override
    public EALSModel load(Map<String, Object> config) {
        return initializerProperties.getTrain() ? modelRepository.loadRaw(config) :
                modelRepository.loadTrained(config);
    }
}
