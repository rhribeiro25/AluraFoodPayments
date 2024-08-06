package br.com.alurafood.payments.service;

import br.com.alurafood.payments.client.OrderClient;
import br.com.alurafood.payments.dto.PaymentDto;
import br.com.alurafood.payments.model.Payment;
import br.com.alurafood.payments.model.PaymentStatus;
import br.com.alurafood.payments.repository.PaymentRepository;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private ModelMapper modelMapper;

    public Page<PaymentDto> findAllByPage (Pageable pageable){
        return paymentRepository
                .findAll(pageable)
                .map(p -> modelMapper.map(p, PaymentDto.class));
    }

    public PaymentDto findById(Long id){
        Payment paymentModel = paymentRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(paymentModel, PaymentDto.class);
    }

    public PaymentDto create(PaymentDto payment){
        Payment paymentModel = modelMapper.map(payment, Payment.class);
        paymentModel.setPayStatus(PaymentStatus.CREATED);
        paymentRepository.save(paymentModel);
        return modelMapper.map(paymentModel, PaymentDto.class);
    }

    public PaymentDto update(Long id, PaymentDto dto) {
        Payment paymentModel = modelMapper.map(dto, Payment.class);
        paymentModel.setId(id);
        paymentModel = paymentRepository.save(paymentModel);
        return modelMapper.map(paymentModel, PaymentDto.class);
    }

    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }

    public void paymentConfirmation(Long id){
        Optional<Payment> payment = paymentRepository.findById(id);

        if (payment.isEmpty()) {
            throw new EntityNotFoundException();
        }

        payment.get().setPayStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment.get());
        orderClient.updatePayments(payment.get().getOrderId());
    }

    public void updateStatus(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);

        if (payment.isEmpty()) {
            throw new EntityNotFoundException();
        }

        payment.get().setPayStatus(PaymentStatus.CONFIRMED_WITHOUT_INTEGRATION);
        paymentRepository.save(payment.get());

    }
}
