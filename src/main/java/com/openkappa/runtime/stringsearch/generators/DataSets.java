package com.openkappa.runtime.stringsearch.generators;

import java.io.IOException;
import java.nio.file.Path;

public enum DataSets {

    KING_JAMES_BIBLE("results/en/bible/pairs.csv"),
    LUTHER_1912_BIBLE("results/de/bible/pairs.csv"),
    RANDOM {
        @Override
        public DataGenerator create(Path path, long seed) {
            return new RandomDataGenerator(seed);
        }
    };

    private final String relativePath;

    DataSets() {
        this("");
    }

    DataSets(String relativePath) {
        this.relativePath = relativePath;
    }

    public DataGenerator create(Path path, long seed) throws IOException {
        return OneStepTransitionDataGenerator.from(path.resolve(relativePath), seed);
    }
}
