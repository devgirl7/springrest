package com.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

public interface ValidatableResource<DetailType,InternalType> {

    default void validate(DetailType detail, InternalType internal, HttpServletRequest request) throws ValidationException {
        //Override if resource needs validation
    }

}
