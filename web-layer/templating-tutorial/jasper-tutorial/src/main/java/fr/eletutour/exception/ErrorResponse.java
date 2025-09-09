/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of jasper-tutorial.
 *
 * jasper-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * jasper-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jasper-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ErrorResponse {

    private HttpStatus status;
    private String message;
    private List<String> cause;

    public ErrorResponse(HttpStatus status, String message, List<String> cause) {
        this.status = status;
        this.message = message;
        this.cause = cause;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getCause() {
        return cause;
    }

    public void setCause(List<String> cause) {
        this.cause = cause;
    }
}
