package com.tam.service;

import com.tam.model.PaymentType;
import com.tam.repositories.PaymentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("paymentTypeService")
public class PaymentTypeService {

    @Autowired
    PaymentTypeRepository paymentTypeRepository;


    @Transactional
    public List<PaymentType> findAll() {
        return paymentTypeRepository.findAll();
    }
}

