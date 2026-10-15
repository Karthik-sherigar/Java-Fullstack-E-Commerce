package com.karthik.springEcom.service;

import com.karthik.springEcom.model.Order;
import com.karthik.springEcom.model.OrderItem;
import com.karthik.springEcom.model.Product;
import com.karthik.springEcom.model.dto.OrderItemRequest;
import com.karthik.springEcom.model.dto.OrderItemResponse;
import com.karthik.springEcom.model.dto.OrderRequest;
import com.karthik.springEcom.model.dto.OrderResponse;
import com.karthik.springEcom.repo.OrderRepo;
import com.karthik.springEcom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ProductRepo productRepo;
    public OrderResponse placeOrder(OrderRequest request) {
        Order order = new Order();
        String orderId = "ORD"+ UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        order.setOrderId(orderId);
        order.setCustomerName(request.customerName());
        order.setEmail(request.email());
        order.setStatus("PLACED");
        order.setOrderDate(LocalDate.now());
        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderItemRequest itemRequest: request.items()) {

            Product product = productRepo.findById(itemRequest.productId()).orElseThrow(() -> new RuntimeException("Product not found"));
            if (itemRequest.quantity() > product.getStockQuantity()) {
                throw new RuntimeException(
                        "Not enough stock. Available stock: " + product.getStockQuantity()
                );
            }
            product.setStockQuantity(product.getStockQuantity() - itemRequest.quantity());
            productRepo.save(product);
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemRequest.quantity())
                    .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.quantity())))
                    .order(order)
                    .build();
            orderItems.add(orderItem);
        }
            order.setOrderItems(orderItems);
            Order savedOrder = orderRepo.save(order);
            List<OrderItemResponse> itemResponses = new ArrayList<>();
            for (OrderItem item : order.getOrderItems()) {
                OrderItemResponse orderItemResponse = new OrderItemResponse(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getTotalPrice()
                );
                itemResponses.add(orderItemResponse);
            }

            OrderResponse orderResponse = new OrderResponse(
                    savedOrder.getOrderId(),
                    savedOrder.getCustomerName(),
                    savedOrder.getEmail(),
                    savedOrder.getStatus(),
                    savedOrder.getOrderDate(),
                    itemResponses
            );

            return orderResponse;

    }

    public List<OrderResponse> getAllOrderResponses() {
        List<Order> orders = orderRepo.findAll();
        List<OrderResponse> orderResponses = new ArrayList<>();

        for (Order order : orders) {


            List<OrderItemResponse> itemResponses = new ArrayList<>();

            for(OrderItem item : order.getOrderItems()) {
                OrderItemResponse orderItemResponse = new OrderItemResponse(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getTotalPrice()
                );
                itemResponses.add(orderItemResponse);

            }
            OrderResponse orderResponse = new OrderResponse(
                    order.getOrderId(),
                    order.getCustomerName(),
                    order.getEmail(),
                    order.getStatus(),
                    order.getOrderDate(),
                    itemResponses
            );
            orderResponses.add(orderResponse);
        }

        return orderResponses;
    }
}
