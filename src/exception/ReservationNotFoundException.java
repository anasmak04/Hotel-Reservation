package exception;

public class ReservationNotFoundException extends RuntimeException  {

    public ReservationNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
