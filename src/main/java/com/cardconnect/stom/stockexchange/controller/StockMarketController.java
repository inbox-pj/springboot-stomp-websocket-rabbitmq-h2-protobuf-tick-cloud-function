package com.cardconnect.stom.stockexchange.controller;

import com.cardconnect.stom.stockexchange.config.User;
import com.cardconnect.stom.stockexchange.model.StockRequest;
import com.cardconnect.stom.stockexchange.service.StockService;
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

import java.util.List;

@RestController
@Tag(name = "Stock Controller", description = "APIs for managing stocks")
@Slf4j
@RequestMapping("/v1")
public class StockMarketController {

    private final SimpMessagingTemplate template;
    private final StockService stockService;

    public StockMarketController(SimpMessagingTemplate template, StockService stockService) {
        this.template = template;
        this.stockService = stockService;
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
        stockService.deleteStock(id);
    }
}