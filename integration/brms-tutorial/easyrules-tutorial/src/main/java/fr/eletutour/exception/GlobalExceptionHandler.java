/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of easyrules-tutorial.
 *
 * easyrules-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * easyrules-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with easyrules-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ErrorResponse> handleTransactionException(TransactionException ex) {
        ErrorResponse errorResponse = new ErrorResponse(List.of(ex.getErrors()));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvocationTargetException.class)
    public ResponseEntity<String> handleInvocationTargetException(InvocationTargetException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof IllegalArgumentException) {
            return handleIllegalArgumentException((IllegalArgumentException) cause);
        }
        return new ResponseEntity<>(cause.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}