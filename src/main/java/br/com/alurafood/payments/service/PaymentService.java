package br.com.alurafood.payments.service;

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

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<PaymentDto> findAllByPage (Pageable pageable){
        return paymentRepository
                .findAll(pageable)
                .map(p -> modelMapper.map(p, PaymentDto.class));
    }

    public PaymentDto findById(Long id){
        Payment payment = paymentRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(payment, PaymentDto.class);
    }

    public PaymentDto create(PaymentDto payment){
        Payment paymentModel = modelMapper.map(payment, Payment.class);
        paymentModel.setStatus(PaymentStatus.CREATED);
        paymentRepository.save(paymentModel);
        return modelMapper.map(payment, PaymentDto.class);
    }

    public PaymentDto update(Long id, PaymentDto dto) {
        Payment pagamento = modelMapper.map(dto, Payment.class);
        pagamento.setId(id);
        pagamento = paymentRepository.save(pagamento);
        return modelMapper.map(pagamento, PaymentDto.class);
    }

    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }
}
