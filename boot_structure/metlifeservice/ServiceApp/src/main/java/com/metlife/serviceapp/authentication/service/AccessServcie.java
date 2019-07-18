package com.metlife.serviceapp.authentication.service;

import com.metlife.commonrepository.entity.CustomerAccount;
import com.metlife.commonrepository.models.response.authentication.TokenCreateView;
import com.metlife.commons.enums.ResponseCode;
import com.metlife.commons.models.response.Response;
import com.metlife.serviceapp.common.model.request.TokenCreateRequest;

import java.rmi.RemoteException;

public class AccessServcie {



   /* //region Login
    public Response<TokenCreateView> login(TokenCreateRequest request) throws RemoteException {
        //TODO: Have to Refactor Code
        Response<TokenCreateView> model = new Response<>();
        CustomerAccount customerAccount = customerAccountService.getByCustomerNumber(request.getUserName());

        if (customerAccount != null) {
            //region Password verified
            Response passwordVerifierModel = passwordVerifierFactory.getVerifierService(customerAccount).verify(customerAccount, request.getPassword());
            if (passwordVerifierModel.getResponseCode() == ResponseCode.OPERATION_SUCCESSFUL.getCode()) {
                //region valid customer.
                this.tokenResponseForValidCustomer(model, customerAccount, request);
                //endregion
            } else {
                //region Login Password not matched.
                this.tokenResponseForInvalidCustomer(model, customerAccount);
                //endregion
            }
            //endregion
        } else {
            //User not found
            model.setResponseCode(ResponseCode.AUTHENTICATION_FAILED.getCode());
            model.getResponseMessages().add(authenticationUserNameNotFoundMessage);
        }

        return model;

    }*/
}
