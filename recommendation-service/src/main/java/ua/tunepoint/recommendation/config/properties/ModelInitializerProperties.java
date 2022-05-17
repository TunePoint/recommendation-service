package ua.tunepoint.recommendation.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "model.initializer")
public class ModelInitializerProperties {

    /**
     * Where to look for model params
     */
    private ModelSources source = ModelSources.DATABASE;

    /**
     * If persisting in database is needed after build (will overwrite existing data including mappings)
     */
    private Boolean saveAfterBuild = false;

    private Boolean saveAfterUpdate = false;

    private Boolean saveItemIdPool = true;

    private Boolean saveUserIdPool = false;

    /**
     * if train is specified, load only userInteraction matrix and train model
     */
    private Boolean train = false;

    private Tables tables;

    @Getter
    @Setter
    public static class Tables {

        private String userInteraction = ModelInitializerConstants.USER_INTERACTION_DEFAULT_TABLE_NAME;
        private String userVector = ModelInitializerConstants.USER_VECTOR_DEFAULT_TABLE_NAME;
        private String itemVector = ModelInitializerConstants.ITEM_VECTOR_DEFAULT_TABLE_NAME;
    }

}
