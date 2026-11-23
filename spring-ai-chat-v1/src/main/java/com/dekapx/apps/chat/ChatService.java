package com.dekapx.apps.chat;

import com.dekapx.apps.model.ChatRequest;
import com.dekapx.apps.model.ChatResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChatService {
    private final ChatClient chatClient;

    public ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public ChatResponse chat(ChatRequest request) {
        String conversationId = getConversationId(request);
        String reply = chatClient.prompt()
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .user(request.message())
                .call()
                .content();

        return new ChatResponse(reply == null ? "" : reply, conversationId);
    }

    private String getConversationId(ChatRequest request) {
        String conversationId = request.conversationId();
        return (conversationId == null || conversationId.isBlank())
                ? UUID.randomUUID().toString()
                : conversationId;
    }
}
