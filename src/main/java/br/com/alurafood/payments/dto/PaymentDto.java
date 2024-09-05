package br.com.alurafood.payments.dto;

import br.com.alurafood.payments.model.PaymentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private Long id;

    @NotNull
    @Positive
    private BigDecimal payValue;

    @NotBlank
    @Size(max = 100)
    private String payName;

    @NotBlank
    @Size(max = 19)
    private String payNumber;

    @NotBlank
    @Size(max = 7)
    private String payExpiration;

    @NotBlank
    @Size(min = 3, max = 3)
    private String payCode;

    private PaymentStatus payStatus;

    @NotNull
    private Long orderId;

    @NotNull
    private Long paymentMethodId;

}
