package org.example.pioner_pixel.repository;

import org.example.pioner_pixel.domain.document.UserDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSearchRepository extends ElasticsearchRepository<UserDocument,Long> {
}
