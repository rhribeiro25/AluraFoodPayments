package br.com.alurafood.payments.controller;

import br.com.alurafood.payments.service.PaymentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v2/payments")
public class PaymentV2Controller {

    @Autowired
    private PaymentService paymentService;

    @PatchMapping("/{id}/confirm")
    @CircuitBreaker(name = "updateOrder")
    public void paymentConfirmV2(@PathVariable @NotNull Long id){
        paymentService.paymentConfirmationV2(id);
    }

}
