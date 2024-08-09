package br.com.alurafood.payments.controller;

import br.com.alurafood.payments.dto.PaymentDto;
import br.com.alurafood.payments.service.PaymentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping
    public Page<PaymentDto> allByPage(@PageableDefault(size = 10) Pageable pageable) {
        return paymentService.findAllByPage(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> paymentById(@PathVariable @NotNull Long id) {
        PaymentDto payment = paymentService.findById(id);
        return ResponseEntity.ok(payment);
    }

    @PostMapping
    public ResponseEntity<PaymentDto> add(@RequestBody @Valid PaymentDto dto, UriComponentsBuilder uriBuilder) {
        PaymentDto payment = paymentService.create(dto);
        URI uri = uriBuilder.path("/payments/{id}").buildAndExpand(payment.getId()).toUri();

        Message msg = new Message(("Created payment with ID: " + payment.getId()).getBytes());
        rabbitTemplate.send("alurafood.payments.created", msg);
        return ResponseEntity.created(uri).body(payment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> edit(@PathVariable @NotNull Long id, @RequestBody @Valid PaymentDto dto) {
        PaymentDto payment = paymentService.update(id, dto);
        return ResponseEntity.ok(payment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentDto> remove(@PathVariable @NotNull Long id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirm")
    @CircuitBreaker(name = "updateOrder", fallbackMethod = "paymentConfirmedWithPendingIntegration")
    public void paymentConfirm(@PathVariable @NotNull Long id){
        paymentService.paymentConfirmation(id);
    }

    public void paymentConfirmedWithPendingIntegration(Long id, Exception e){
        paymentService.updateStatus(id);
    }
        
}
