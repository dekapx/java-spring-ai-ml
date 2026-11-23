package com.dekapx.apps.model;

import jakarta.validation.constraints.NotBlank;

public record ChatRequest(
        @NotBlank String message,
        String conversationId) {}
