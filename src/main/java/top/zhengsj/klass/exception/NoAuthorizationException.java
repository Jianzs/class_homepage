package top.zhengsj.klass.exception;

public class NoAuthorizationException extends Exception{
    public NoAuthorizationException() {
    }

    public NoAuthorizationException(String message) {
        super(message);
    }
}
