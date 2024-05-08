package com.abhinav.chatte.repository;

import com.abhinav.chatte.model.Conversation;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.UUID;

public interface ConversationRepository extends CassandraRepository<Conversation, UUID> {
  @AllowFiltering
  public List<Conversation> findByToUserAndReadStatus(String toUser, boolean readStatus);
}
