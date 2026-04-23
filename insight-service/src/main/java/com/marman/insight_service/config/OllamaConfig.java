package com.marman.insight_service.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OllamaConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder builder){
        return builder.defaultSystem(
                "Eres un consejero experto en eficiencia de energia electrica. " +
                     "Das consejos practicos y consisos a usuarios sobre como reducir " +
                     "su consumo de energia basado en sus patrones de uso."
        ).build();
    }
}
