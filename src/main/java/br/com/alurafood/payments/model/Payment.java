package br.com.alurafood.payments.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "Payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentStatus payStatus;

    @NotNull
    private Long orderId;

    @NotNull
    private Long paymentMethodId;

}
