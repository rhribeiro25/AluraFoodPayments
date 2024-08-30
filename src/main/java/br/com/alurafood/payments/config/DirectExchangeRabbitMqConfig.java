package br.com.alurafood.payments.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectExchangeRabbitMqConfig {

    @Bean
    public Queue paymentsCreatedQueue() {
        return QueueBuilder
                .nonDurable("alura-food.payments-ms.payments-created.orders-ms")
                .deadLetterExchange("payments.created.orders.dlx")
                .build();
    }

    @Bean
    public Exchange paymentsCreatedDeadLetterExchange() {
        return ExchangeBuilder
                .fanoutExchange("payments.created.orders.dlx")
                .build();
    }

    @Bean
    public DirectExchange paymentsCreatedDirectExchange(){
        return new DirectExchange("payments.created.dx");
    }

    @Bean
    public Binding paymentsCreatedBinding() {
        return BindingBuilder
                .bind(paymentsCreatedQueue())
                .to(paymentsCreatedDirectExchange())
                .withQueueName();
    }

}
