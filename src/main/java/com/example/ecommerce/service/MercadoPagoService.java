package com.example.ecommerce.service;

import com.example.ecommerce.model.Order;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoService {

    @Value("${mercadopago.access.token}")
    private String mercadoPagoAccessToken;

    public String createPreference(Order order) throws MPException, MPApiException {
        MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

        PreferenceClient client = new PreferenceClient();

        List<PreferenceItemRequest> items = new ArrayList<>();
        order.getItems().forEach(orderItem -> {
            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title(orderItem.getProduct().getName())
                    .quantity(orderItem.getQuantity())
                    .unitPrice(BigDecimal.valueOf(orderItem.getPrice().doubleValue()))
                    .build();
            items.add(item);
        });

        PreferenceRequest request = PreferenceRequest.builder()
                .items(items)
                .externalReference(order.getId().toString())
                .build();

        Preference preference = client.create(request);
        return preference.getId();
    }

    public Payment getPaymentInfo(String paymentId) throws MPException, MPApiException {
        MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);
        PaymentClient client = new PaymentClient();
        return client.get(Long.parseLong(paymentId));
    }
}