package com.oldway.service;

import com.oldway.dto.Purchase;
import com.oldway.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
