package br.com.alurafood.payments.dto;

import br.com.alurafood.payments.model.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentDto {

    private Long id;
    private BigDecimal value;
    private String name;
    private String number;
    private String expiration;
    private String code;
    private PaymentStatus status;
    private Long orderId;
    private Long paymentMethodId;

}
