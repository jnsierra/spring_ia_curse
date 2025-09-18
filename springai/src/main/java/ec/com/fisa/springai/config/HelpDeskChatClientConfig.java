package ec.com.fisa.springai.config;

import ec.com.fisa.springai.advisors.TokenUsageAuditAdvisor;
import ec.com.fisa.springai.tools.HelpDeskTools;
import ec.com.fisa.springai.tools.TimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.execution.DefaultToolExecutionExceptionProcessor;
import org.springframework.ai.tool.execution.ToolExecutionExceptionProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
public class HelpDeskChatClientConfig {

    @Value(value = "classpath:/promptTemplates/helpDeskSystemPromptTemplate.st")
    Resource systemPromptTemplate;

    @Bean("helpDeskChatClient")
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory,
                                 TimeTools timeTools) {
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        Advisor tokenUsageAdvisor = new TokenUsageAuditAdvisor();
        return chatClientBuilder
                .defaultSystem(systemPromptTemplate)
                .defaultTools(timeTools)
                .defaultAdvisors(List.of(loggerAdvisor, memoryAdvisor, tokenUsageAdvisor))
                .build();
    }

//    @Bean
//    ToolExecutionExceptionProcessor toolExecutionExceptionProcessor(){
//        return new DefaultToolExecutionExceptionProcessor(true);
//    }


}
