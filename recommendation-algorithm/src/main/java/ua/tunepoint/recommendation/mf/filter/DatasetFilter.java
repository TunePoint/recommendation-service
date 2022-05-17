package ua.tunepoint.recommendation.mf.filter;


import ua.tunepoint.recommendation.mf.data.Dataset;

public interface DatasetFilter {
    void filter(Dataset dataset);
}
