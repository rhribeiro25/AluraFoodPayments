package br.com.alurafood.payments.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutExchangeRabbitMqConfig {

    @Bean
    public Queue paymentsConfirmedQueue() {
        return QueueBuilder
                .nonDurable("alura-food.payments-ms.payments-confirmed.evaluations-ms")
                .deadLetterExchange("payments.confirmed.evaluations.dlx")
                .build();
    }

    @Bean
    public Queue paymentsConfirmedDlq() {
        return QueueBuilder
                .nonDurable("alura-food.payments-ms.payments-confirmed.evaluations-ms.dlq")
                .build();
    }

    @Bean
    public FanoutExchange paymentsConfirmedFanoutExchange() {
        return ExchangeBuilder
                .fanoutExchange("payments.confirmed.fx")
                .build();
    }

    @Bean
    public FanoutExchange paymentsConfirmedDeadLetterExchange() {
        return ExchangeBuilder
                .fanoutExchange("payments.confirmed.evaluations.dlx")
                .build();
    }

    @Bean
    public Binding paymentsConfirmedBindingFanoutExchange() {
        return BindingBuilder
                .bind(paymentsConfirmedQueue())
                .to(paymentsConfirmedFanoutExchange());
    }

    @Bean
    public Binding paymentsConfirmedBindingDeadLetterExchange() {
        return BindingBuilder
                .bind(paymentsConfirmedDlq())
                .to(paymentsConfirmedDeadLetterExchange());
    }

}
