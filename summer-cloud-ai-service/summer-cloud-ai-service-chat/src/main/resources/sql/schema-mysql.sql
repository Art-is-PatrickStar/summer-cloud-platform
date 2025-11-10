create table if not exists SPRING_AI_CHAT_MEMORY (
  `conversation_id` varchar(36) not null,
  `content` text not null,
  `type` varchar(10) not null,
  `timestamp` timestamp not null,

    index `SPRING_AI_CHAT_MEMORY_CONVERSATION_ID_TIMESTAMP_IDX` (`conversation_id`, `timestamp`)
);