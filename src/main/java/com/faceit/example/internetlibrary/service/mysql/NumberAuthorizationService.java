package com.faceit.example.internetlibrary.service.mysql;

import com.faceit.example.internetlibrary.model.mysql.NumberAuthorization;

public interface NumberAuthorizationService {

    NumberAuthorization getNumberAuthorizationById(long id);

    NumberAuthorization updateNumberAuthorizationById(NumberAuthorization numberAuthorization);
}
