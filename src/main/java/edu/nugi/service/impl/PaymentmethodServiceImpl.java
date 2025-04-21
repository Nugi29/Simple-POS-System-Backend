package edu.nugi.service.impl;

import edu.nugi.dto.Paymentmethod;
import edu.nugi.entity.PaymentmethodEntity;
import edu.nugi.repository.PaymentmethodRepository;
import edu.nugi.service.PaymentmethodService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentmethodServiceImpl implements PaymentmethodService {

    final PaymentmethodRepository repository;
    final ModelMapper mapper;

    @Override
    public List<Paymentmethod> getAll() {
        ArrayList<Paymentmethod> paymentmethodList = new ArrayList<>();
        List<PaymentmethodEntity> all = repository.findAll();

        all.forEach(paymentmethodEntity -> {
            paymentmethodList.add(mapper.map(paymentmethodEntity, Paymentmethod.class));
        });
        return paymentmethodList;
    }
}
