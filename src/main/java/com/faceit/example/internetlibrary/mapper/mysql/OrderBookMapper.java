package com.faceit.example.internetlibrary.mapper.mysql;

import com.faceit.example.internetlibrary.dto.request.mysql.OrderBookRequest;
import com.faceit.example.internetlibrary.dto.response.mysql.OrderBookResponse;
import com.faceit.example.internetlibrary.model.mysql.OrderBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderBookMapper {

    @Mappings({
            @Mapping(source = "startDate", dateFormat = "dd/MM/yyyy hh:mm", target = "startDate"),
            @Mapping(source = "endDate", dateFormat = "dd/MM/yyyy hh:mm", target = "endDate")})
    OrderBookResponse orderBookToOrderBookResponse(OrderBook orderBook);

    @Mappings({
            @Mapping(source = "startDate", dateFormat = "dd/MM/yyyy hh:mm", target = "startDate"),
            @Mapping(source = "endDate", dateFormat = "dd/MM/yyyy hh:mm", target = "endDate"),
            @Mapping(target = "id", ignore = true)
    })
    OrderBook orderBookRequestToOrderBook(OrderBookRequest orderBookRequest);

    List<OrderBookResponse> orderBooksToOrderBookResponse(List<OrderBook> orderBooks);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "book", ignore = true)
    })
    OrderBook updateOrderBookFromOrderBookRequest(OrderBookRequest orderBookRequest,
                                                  @MappingTarget OrderBook orderBook);
}
