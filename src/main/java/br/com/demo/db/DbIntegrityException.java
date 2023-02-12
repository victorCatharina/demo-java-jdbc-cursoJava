package br.com.demo.db;

public class DbIntegrityException extends RuntimeException {

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    public DbIntegrityException(String msg) {
        super(msg);
    }
}
