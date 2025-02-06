package spring.ai.vector.database;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class VectorDatabaseController {

    private final VectorStore vectorStore;

    @Autowired
    public VectorDatabaseController(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    /**
     * To configure PgVector in your application, you can use this approach as well:
     *
     @Bean
     public VectorStore vectorStore(JdbcTemplate jdbcTemplate, EmbeddingModel embeddingModel) {
         return PgVectorStore.builder(jdbcTemplate, embeddingModel)
             .dimensions(1536)                    // Optional: defaults to model dimensions or 1536
             .distanceType(COSINE_DISTANCE)       // Optional: defaults to COSINE_DISTANCE
             .indexType(HNSW)                     // Optional: defaults to HNSW
             .initializeSchema(true)              // Optional: defaults to false
             .schemaName("public")                // Optional: defaults to "public"
             .vectorTableName("vector_store")     // Optional: defaults to "vector_store"
             .maxDocumentBatchSize(10000)         // Optional: defaults to 10000
             .build();
     }
     */

    @GetMapping(value = "ai/vector/database", produces = "application/json")
    public Map vectorDatabase(@RequestParam(value = "message",defaultValue = "Spring") String message ) {
        List<Document> documents = List.of(
                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
                new Document("The World is Big and Salvation Lurks Around the Corner"),
                new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));

        // Add the documents to PGVector
        vectorStore.add(documents);

        // Retrieve documents similar to a query
        List<Document> results = this.vectorStore
                    .similaritySearch(SearchRequest.builder()
                        .query(message)
                        .topK(5)
                        .build());

        return Map.of("vectorDatabase", results);
    }
}
