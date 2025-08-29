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
