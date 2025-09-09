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

public class JasperException extends Exception {

    public enum JasperError {
        REPORT_NOT_FOUND("Author not found", 404),
        JASPER_INTERNAL_ERROR("Internal error", 500);

        private final String message;
        private final int code;

        JasperError(String message, int code) {
            this.message = message;
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public int getCode() {
            return code;
        }
    }

    private JasperError error;
    private String msg;

    public JasperException(JasperError error, String msg) {
        super(msg);
        this.error = error;
    }

    public JasperError getError() {
        return error;
    }

    public void setError(JasperError error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
