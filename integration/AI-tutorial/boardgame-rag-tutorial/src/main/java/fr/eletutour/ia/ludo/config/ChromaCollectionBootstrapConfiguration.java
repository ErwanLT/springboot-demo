package fr.eletutour.ia.ludo.config;

import io.micrometer.observation.ObservationRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chroma.vectorstore.ChromaApi;
import org.springframework.ai.chroma.vectorstore.ChromaVectorStore;
import org.springframework.ai.embedding.BatchingStrategy;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.chroma.autoconfigure.ChromaVectorStoreProperties;
import org.springframework.ai.vectorstore.observation.DefaultVectorStoreObservationConvention;
import org.springframework.ai.vectorstore.observation.VectorStoreObservationConvention;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

@Configuration
public class ChromaCollectionBootstrapConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChromaCollectionBootstrapConfiguration.class);

    @Bean
    ChromaVectorStore vectorStore(EmbeddingModel embeddingModel,
                                  ChromaApi chromaApi,
                                  ChromaVectorStoreProperties properties,
                                  ObjectProvider<ObservationRegistry> observationRegistry,
                                  ObjectProvider<VectorStoreObservationConvention> customObservationConvention,
                                  BatchingStrategy chromaBatchingStrategy) {

        String tenant = properties.getTenantName();
        String database = properties.getDatabaseName();
        String collection = properties.getCollectionName();

        ensureChromaSchema(chromaApi, tenant, database, collection);

        return ChromaVectorStore.builder(chromaApi, embeddingModel)
                .tenantName(tenant)
                .databaseName(database)
                .collectionName(collection)
                .initializeSchema(false)
                .observationRegistry(observationRegistry.getIfUnique(() -> ObservationRegistry.NOOP))
                .customObservationConvention(customObservationConvention.getIfAvailable(DefaultVectorStoreObservationConvention::new))
                .batchingStrategy(chromaBatchingStrategy)
                .build();
    }

    private void ensureChromaSchema(ChromaApi chromaApi, String tenant, String database, String collection) {
        createTenantIfMissing(chromaApi, tenant);
        createDatabaseIfMissing(chromaApi, tenant, database);
        createCollectionIfMissing(chromaApi, tenant, database, collection);
    }

    private void createTenantIfMissing(ChromaApi chromaApi, String tenant) {
        try {
            chromaApi.createTenant(tenant);
            LOGGER.info("Created Chroma tenant [{}]", tenant);
        }
        catch (RuntimeException ex) {
            if (isConflict(ex)) {
                LOGGER.debug("Chroma tenant [{}] already exists", tenant);
                return;
            }
            throw ex;
        }
    }

    private void createDatabaseIfMissing(ChromaApi chromaApi, String tenant, String database) {
        try {
            chromaApi.createDatabase(tenant, database);
            LOGGER.info("Created Chroma database [{}] in tenant [{}]", database, tenant);
        }
        catch (RuntimeException ex) {
            if (isConflict(ex)) {
                LOGGER.debug("Chroma database [{}] already exists in tenant [{}]", database, tenant);
                return;
            }
            throw ex;
        }
    }

    private void createCollectionIfMissing(ChromaApi chromaApi, String tenant, String database, String collection) {
        try {
            chromaApi.createCollection(tenant, database, new ChromaApi.CreateCollectionRequest(collection));
            LOGGER.info("Created Chroma collection [{}] in database [{}]", collection, database);
        }
        catch (RuntimeException ex) {
            if (isConflict(ex)) {
                LOGGER.debug("Chroma collection [{}] already exists in database [{}]", collection, database);
                return;
            }
            throw ex;
        }
    }

    private boolean isConflict(Throwable throwable) {
        return hasStatus(throwable, 409);
    }

    @SuppressWarnings("SameParameterValue")
    private boolean hasStatus(Throwable throwable, int statusCode) {
        Throwable current = throwable;
        while (current != null) {
            if (current instanceof HttpClientErrorException httpEx) {
                HttpStatusCode code = httpEx.getStatusCode();
                return code != null && code.value() == statusCode;
            }
            current = current.getCause();
        }
        return false;
    }
}
