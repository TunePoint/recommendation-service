package ua.tunepoint.recommendation.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.tunepoint.recommendation.alsmodel.loader.DatabaseModelLoader;
import ua.tunepoint.recommendation.alsmodel.loader.ModelLoader;
import ua.tunepoint.recommendation.alsmodel.repository.ModelRepository;
import ua.tunepoint.recommendation.config.properties.ModelConfig;
import ua.tunepoint.recommendation.config.properties.ModelInitializerProperties;
import ua.tunepoint.recommendation.config.properties.ModelProperties;
import ua.tunepoint.recommendation.mf.algorithm.als.config.EALSConfig;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class ModelConfiguration {

    @Autowired
    ModelInitializerProperties modelInitProps;

    @Autowired
    ModelProperties modelProps;

    @Bean(name = "modelConfig")
    public ModelConfig modelConfig() {

        Map<String, Object> config = new HashMap<>();

        config.put(EALSConfig.REGULARIZATION_PARAMETER, modelProps.getRegularizationParameter());
        config.put(EALSConfig.FACTORS, modelProps.getFactors());
        config.put(EALSConfig.OFFLINE_ITERATIONS, modelProps.getOfflineIterations());
        config.put(EALSConfig.ONLINE_ITERATIONS, modelProps.getOnlineIterations());
        config.put(EALSConfig.LATENT_INIT_DEVIATION, modelProps.getLatentInitDeviation());
        config.put(EALSConfig.LATENT_INIT_MEAN, modelProps.getLatentInitMean());
        config.put(EALSConfig.POPULARITY_SIGNIFICANCE, modelProps.getPopularitySignificance());
        config.put(EALSConfig.MISSING_DATA_WEIGHT, modelProps.getMissingDataWeight());
        config.put(EALSConfig.NEW_ITEM_WEIGHT, modelProps.getNewItemWeight());

        return new ModelConfig(config);
    }
}
