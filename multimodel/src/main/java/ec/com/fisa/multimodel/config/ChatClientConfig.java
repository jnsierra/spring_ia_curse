package ec.com.fisa.multimodel.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {
    @Bean(name = "openAiChatClient")
    public ChatClient openAiChatClient(OpenAiChatModel openAiChatModel){
        return ChatClient.create(openAiChatModel);
    }

    @Bean(name = "ollamaChatClient")
    public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel){
        ChatClient.Builder chatClientBuilder = ChatClient.builder(ollamaChatModel);
        return chatClientBuilder.build();
    }
}
