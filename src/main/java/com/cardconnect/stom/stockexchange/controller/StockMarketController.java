package com.cardconnect.stom.stockexchange.controller;

import com.cardconnect.stom.stockexchange.config.User;
import com.cardconnect.stom.stockexchange.metrics.MetricsService;
import com.cardconnect.stom.stockexchange.model.StockRequest;
import com.cardconnect.stom.stockexchange.proto.ProtoBufStock;
import com.cardconnect.stom.stockexchange.metrics.RabbitMQMetricsPublisher;
import com.cardconnect.stom.stockexchange.service.StockService;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Stock Controller", description = "APIs for managing stocks")
@Slf4j
@RequestMapping("/v1")
public class StockMarketController {

    private final SimpMessagingTemplate template;
    private final StockService stockService;
    private final MetricsService metricsService;

    public StockMarketController(SimpMessagingTemplate template, StockService stockService, MetricsService metricsService) {
        this.template = template;
        this.stockService = stockService;
        this.metricsService = metricsService;
    }

    @MessageMapping("/updateStock")
    //@SendTo("/topic/stockPrices")
    @PostMapping("/stock")
    @Operation(summary = "Adding stock", description = "add stocks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved stock details"),
            @ApiResponse(responseCode = "404", description = "Stock not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("hasAnyRole('" + "ROLE_" + User.Roles.ADD + "', '" + "ROLE_" + User.Roles.ADMIN + "')")
    public StockRequest updateStockPrice(@RequestBody StockRequest stock) {
        metricsService.send("stock.updates", 1);
        stock.setPrice(stock.getPrice() + Math.random() * 10);
        stockService.saveStock(stock);
        template.convertAndSend("/topic/stockPrices", stock);
        return stock;
    }

    @GetMapping("/stock")
    @Operation(summary = "Get all stocks", description = "Retrieve a list of all stocks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved stock details"),
            @ApiResponse(responseCode = "404", description = "Stock not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("hasAnyRole('" + "ROLE_" + User.Roles.FETCH + "', '" + "ROLE_" + User.Roles.ADMIN + "')")
    public List<StockRequest> getAllStocks() {
        metricsService.send("stock.fetches", 1);
        return stockService.getAllStocks();
    }

    @DeleteMapping("/stock/{id}")
    @Operation(summary = "delete stock", description = "delete stocks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved stock details"),
            @ApiResponse(responseCode = "404", description = "Stock not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("hasAnyRole('" + "ROLE_" + User.Roles.DELETE + "', '" + "ROLE_" + User.Roles.ADMIN + "')")
    public void deleteStock(@PathVariable Long id) {
        metricsService.send("stock.deletes", 1);
        stockService.deleteStock(id);
    }

    @GetMapping(value = "/stock-buf", consumes = "application/x-protobuf", produces = "application/x-protobuf")
    @Operation(summary = "Get stock using protobuf", description = "Retrieve a single stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved stock details"),
            @ApiResponse(responseCode = "404", description = "Stock not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("hasAnyRole('" + "ROLE_" + User.Roles.FETCH + "', '" + "ROLE_" + User.Roles.ADMIN + "')")
    public ProtoBufStock.ProtoStockRequest getStockProtoBuf() throws InvalidProtocolBufferException {
        metricsService.send("stock.fetches", 1);
        // Create StockMetadata
        ProtoBufStock.StockMetadata metadata = ProtoBufStock.StockMetadata.newBuilder()
                .setCreatedBy("admin")
                .setUpdatedBy("user1")
                .setCreatedAt(System.currentTimeMillis())
                .setUpdatedAt(System.currentTimeMillis())
                .build();

        // Create StockHistory entries
        ProtoBufStock.StockHistory history1 = ProtoBufStock.StockHistory.newBuilder()
                .setTimestamp(System.currentTimeMillis() - 86400000) // 1 day ago
                .setPrice(100.50)
                .setNote("Initial price")
                .build();

        ProtoBufStock.StockHistory history2 = ProtoBufStock.StockHistory.newBuilder()
                .setTimestamp(System.currentTimeMillis())
                .setPrice(105.75)
                .setNote("Updated price")
                .build();

        // Create attributes map
        Map<String, String> attributes = new HashMap<>();
        attributes.put("sector", "Technology");
        attributes.put("exchange", "NASDAQ");

        // Build and return a single StockRequest
        ProtoBufStock.ProtoStockRequest request = ProtoBufStock.ProtoStockRequest.newBuilder()
                .setId(1L)
                .setName("TechCorp")
                .setPrice(105.75)
                .setStatus(ProtoBufStock.StockStatus.ACTIVE)
                .addAllTags(Arrays.asList("bluechip", "growth"))
                .putAllAttributes(attributes)
                .setMetadata(metadata)
                .setEquity("EquityType")
                .addAllHistory(Arrays.asList(history1, history2))
                .build();

        // Measure Protobuf serialization
        long start = System.nanoTime();
        byte[] protoBytes = request.toByteArray();
        long protoSerializationTime = System.nanoTime() - start;

        // Measure Protobuf deserialization
        start = System.nanoTime();
        ProtoBufStock.ProtoStockRequest deserializedProto = ProtoBufStock.ProtoStockRequest.parseFrom(protoBytes);
        long protoDeserializationTime = System.nanoTime() - start;

        // Convert Protobuf to JSON
        String json = JsonFormat.printer().print(request);
        log.info("protobuf JSON: {}", json);

        // Measure JSON serialization
        start = System.nanoTime();
        byte[] jsonBytes = json.getBytes();
        long jsonSerializationTime = System.nanoTime() - start;

        // Measure JSON deserialization
        start = System.nanoTime();
        ProtoBufStock.ProtoStockRequest.Builder builder = ProtoBufStock.ProtoStockRequest.newBuilder();
        JsonFormat.parser().merge(new String(jsonBytes), builder);
        long jsonDeserializationTime = System.nanoTime() - start;

        // Print results
        log.debug("Protobuf Serialization Time: {} ns", protoSerializationTime);
        log.debug("Protobuf Deserialization Time: {} ns", protoDeserializationTime);
        log.debug("Protobuf Payload Size: {} bytes", protoBytes.length);

        log.debug("JSON Serialization Time: {} ns", jsonSerializationTime);
        log.debug("JSON Deserialization Time: {} ns", jsonDeserializationTime);
        log.debug("JSON Payload Size: {} bytes", jsonBytes.length);

        return request;
    }
}