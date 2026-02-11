package Mattiazerbini.U5_W2_D3.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(long id) {
        super(String.valueOf(id));
    }
}
