package exception; // Assure-toi que ce package correspond à l'emplacement du fichier

public class AucunLitDisponibleException extends Exception {

    public AucunLitDisponibleException(String message) {
        super(message);
    }

    public AucunLitDisponibleException(String message, Throwable cause) {
        super(message, cause);
    }
}