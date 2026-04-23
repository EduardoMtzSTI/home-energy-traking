package com.marman.insight_service.service;

import com.marman.insight_service.client.UsageClient;
import com.marman.insight_service.dto.DeviceDto;
import com.marman.insight_service.dto.InsightDto;
import com.marman.insight_service.dto.UsageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InsightService {

    private final UsageClient usageClient;
    private final OllamaChatModel ollamaChatModel;

    public InsightDto getSavingsTips(Long userId) {
        final UsageDto usageData = usageClient.getXDaysUsageForUser(userId,3);

        double totalUsage = usageData.devices().stream()
                .mapToDouble(DeviceDto::energyConsumed)
                .sum();

        log.info("Calling Ollama for userId:{} with total usage:{}",userId,totalUsage);

        String prompt = new StringBuilder()
                .append("Analiza los siguientes datos de consumo energetico y proporciona ")
                .append("de 1 a 3 tips para ahorrar energia.")
                .append("Esta es la informacion recopilada de los ultimos 3 dias")
                .append("Datos de consumo:\n")
                .append(usageData.devices())
                .toString();

        ChatResponse response = ollamaChatModel.call(
                Prompt.builder()
                        .content(prompt)
                        .build());

        return InsightDto.builder()
                .userId(userId)
                .tips(response.getResult().getOutput().getText())
                .energyUsage(totalUsage)
                .build();
    }

    public InsightDto getOverview(Long userId) {
        final UsageDto usageData = usageClient.getXDaysUsageForUser(userId,3);

        double totalUsage = usageData.devices().stream()
                .mapToDouble(DeviceDto::energyConsumed)
                .sum();

        log.info("Calling Ollama for userId:{} with total usage:{}",userId,totalUsage);

        String prompt = new StringBuilder()
                .append("Analiza los siguientes datos de consumo energetico y provee ")
                .append("un resumen conciso con conocimientos practicos.")
                .append("Esta es la informacion recopilada de los ultimos 3 dias")
                .append("Datos de consumo:\n")
                .append(usageData.devices())
                .toString();

        ChatResponse response = ollamaChatModel.call(
                Prompt.builder()
                        .content(prompt)
                        .build());

        return InsightDto.builder()
                .userId(userId)
                .tips(response.getResult().getOutput().getText())
                .energyUsage(totalUsage)
                .build();
    }
}
