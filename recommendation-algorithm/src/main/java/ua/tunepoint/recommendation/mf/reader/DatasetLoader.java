package ua.tunepoint.recommendation.mf.reader;


import ua.tunepoint.recommendation.mf.data.Dataset;

import java.io.IOException;

public interface DatasetLoader {
    Dataset load() throws IOException;
    Dataset load(int maxInteractionCount) throws IOException;
}
