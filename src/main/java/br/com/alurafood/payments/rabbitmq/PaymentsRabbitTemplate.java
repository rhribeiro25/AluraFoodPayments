package br.com.alurafood.payments.rabbitmq;

import br.com.alurafood.payments.dto.PaymentDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentsRabbitTemplate {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendPaymentToRabbitMQ(PaymentDto payment){
        rabbitTemplate.convertAndSend("payments.ex", "", payment);
    }

}
