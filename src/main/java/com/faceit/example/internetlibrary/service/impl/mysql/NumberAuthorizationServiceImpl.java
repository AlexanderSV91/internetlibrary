package com.faceit.example.internetlibrary.service.impl.mysql;

import com.faceit.example.internetlibrary.model.mysql.NumberAuthorization;
import com.faceit.example.internetlibrary.repository.mysql.NumberAuthorizationRepository;
import com.faceit.example.internetlibrary.service.mysql.NumberAuthorizationService;
import com.faceit.example.internetlibrary.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NumberAuthorizationServiceImpl implements NumberAuthorizationService {

    private final NumberAuthorizationRepository numberAuthorizationRepository;

    @Autowired
    public NumberAuthorizationServiceImpl(NumberAuthorizationRepository numberAuthorizationRepository) {
        this.numberAuthorizationRepository = numberAuthorizationRepository;
    }

    @Override
    public NumberAuthorization getNumberAuthorizationById(long id) {
        Optional<NumberAuthorization> numberAuthorizationOptional = numberAuthorizationRepository.findById(id);
        return Utils.getDataFromTypeOptional(numberAuthorizationOptional);
    }

    @Override
    public NumberAuthorization updateNumberAuthorizationById(NumberAuthorization numberAuthorization) {
        return numberAuthorizationRepository.save(numberAuthorization);
    }
}
