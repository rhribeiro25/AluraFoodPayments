package br.com.alurafood.payments.dto;

import br.com.alurafood.payments.model.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentDto {

    private Long id;
    private BigDecimal payValue;
    private String payName;
    private String payNumber;
    private String payExpiration;
    private String payCode;
    private PaymentStatus payStatus;
    private Long orderId;
    private Long paymentMethodId;

}
