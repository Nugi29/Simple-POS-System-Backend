package edu.nugi.controller;

import edu.nugi.dto.Category;
import edu.nugi.dto.Paymentmethod;
import edu.nugi.service.CategoryService;
import edu.nugi.service.PaymentmethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/paymentmethod")
@CrossOrigin
@RequiredArgsConstructor
public class PaymentmethodController {

    @Autowired
    PaymentmethodService service;

    @RequestMapping("/get-all/list")
    public List<Paymentmethod> getAll() {
        return service.getAll();
    }
}
