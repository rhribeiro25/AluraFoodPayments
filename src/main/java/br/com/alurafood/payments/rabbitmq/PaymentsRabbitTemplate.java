package br.com.alurafood.payments.rabbitmq;

import br.com.alurafood.payments.dto.PaymentDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentsRabbitTemplate {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendPaymentsCreated(PaymentDto payment){
        rabbitTemplate.convertAndSend("payments.created.ex", "", payment);
    }

    public void sendPaymentsConfirmed(PaymentDto payment){
        rabbitTemplate.convertAndSend("payments.confirmed.ex", "", payment);
    }

}
