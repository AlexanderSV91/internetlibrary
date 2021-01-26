package com.faceit.example.internetlibrary.mapper;

import com.faceit.example.internetlibrary.dto.request.OrderBookRequest;
import com.faceit.example.internetlibrary.dto.response.OrderBookResponse;
import com.faceit.example.internetlibrary.model.OrderBook;
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

/*    @Mapping(target = "user.userName", ignore = true)
    @Mapping(target = "user.firstName", ignore = true)
    @Mapping(target = "user.lastName", ignore = true)
    @Mapping(target = "user.email", ignore = true)
    @Mapping(target = "user.age", ignore = true)
    @Mapping(target = "book.name", ignore = true)
    @Mapping(target = "book.bookCondition", ignore = true)
    @Mapping(target = "book.description", ignore = true)*/
    @Mapping(target = "id", ignore = true)
    OrderBook updateOrderBookFromOrderBookRequest(OrderBookRequest orderBookRequest,
                                                  @MappingTarget OrderBook orderBook);
}
