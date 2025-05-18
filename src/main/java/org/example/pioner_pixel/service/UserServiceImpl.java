package org.example.pioner_pixel.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.pioner_pixel.domain.document.UserDocument;
import org.example.pioner_pixel.dto.UserSearchCriteria;
import org.example.pioner_pixel.dto.UserSearchResult;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final ElasticsearchClient elasticsearchClient;

    @Cacheable(value = "user_search", key = "{#criteria.name, #criteria.dateOfBirth.toString(), #criteria.email, #criteria.phone, #pageable.pageNumber, #pageable.pageSize}")
    @Override
    public List<UserSearchResult> searchUsers(UserSearchCriteria criteria, Pageable pageable) throws IOException {
        Query query = buildQuery(criteria);

        SearchRequest request = SearchRequest.of(s -> s
                .index("users")
                .query(query)
                .from(pageable.getPageNumber() * pageable.getPageSize())
                .size(pageable.getPageSize())
        );

        SearchResponse<UserDocument> response = elasticsearchClient.search(request, UserDocument.class);

        return response.hits().hits().stream()
                .map(Hit::source)
                .filter(Objects::nonNull)
                .map(this::convertToSearchResult)
                .collect(Collectors.toList());
    }

    private Query buildQuery(UserSearchCriteria criteria) {
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

        if (criteria.getName() != null && !criteria.getName().isEmpty()) {
            boolQueryBuilder.must(Query.of(q -> q
                    .wildcard(w -> w
                            .field("name")
                            .value(criteria.getName().toLowerCase() + "*")
                    )
            ));
        }

        if (criteria.getDateOfBirth() != null) {
            RangeQuery rangeQuery = new RangeQuery.Builder()
                    .untyped(u -> u
                            .field("dateOfBirth")
                            .gt(JsonData.of(criteria.getDateOfBirth().format(DateTimeFormatter.ISO_LOCAL_DATE)))
                    )
                    .build();

            boolQueryBuilder.filter(Query.of(q -> q.range(rangeQuery)));
        }

        if (criteria.getEmail() != null && !criteria.getEmail().isEmpty()) {
            boolQueryBuilder.filter(Query.of(q -> q
                    .term(t -> t
                            .field("emails")
                            .value(criteria.getEmail())
                    )
            ));
        }

        if (criteria.getPhone() != null && !criteria.getPhone().isEmpty()) {
            boolQueryBuilder.filter(Query.of(q -> q
                    .term(t -> t
                            .field("phones")
                            .value(criteria.getPhone())
                    )
            ));
        }

        return Query.of(q -> q.bool(boolQueryBuilder.build()));
    }

    private UserSearchResult convertToSearchResult(UserDocument doc) {
        return UserSearchResult.builder()
                .id(doc.getId())
                .name(doc.getName())
                .dateOfBirth(doc.getDateOfBirth().toString())
                .emails(new ArrayList<>(doc.getEmails()))
                .phones(new ArrayList<>(doc.getPhones()))
                .balance(doc.getBalance())
                .build();
    }
    @Override
    public long getTotalCount(UserSearchCriteria criteria) throws IOException {
        Query query = buildQuery(criteria);
        SearchRequest countRequest = SearchRequest.of(s -> s
                .index("users")
                .query(query)
                .size(0)
        );
        SearchResponse<UserDocument> response = elasticsearchClient.search(countRequest, UserDocument.class);
        return response.hits().total() != null ? response.hits().total().value() : 0;
    }
}
