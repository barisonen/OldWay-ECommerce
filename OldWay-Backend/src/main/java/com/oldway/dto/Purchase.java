package com.oldway.dto;

import com.oldway.entity.Address;
import com.oldway.entity.Customer;
import com.oldway.entity.Order;
import com.oldway.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
