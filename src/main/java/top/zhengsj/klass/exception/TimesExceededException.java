package top.zhengsj.klass.exception;

public class TimesExceededException extends Exception{
    public TimesExceededException() {
    }

    public TimesExceededException(String message) {
        super(message);
    }
}
