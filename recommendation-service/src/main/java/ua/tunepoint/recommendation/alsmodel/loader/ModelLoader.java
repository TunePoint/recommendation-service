package ua.tunepoint.recommendation.alsmodel.loader;

import ua.tunepoint.recommendation.mf.algorithm.als.EALSModel;

import java.util.Map;

public interface ModelLoader {
    EALSModel load(Map<String, Object> config);
}
