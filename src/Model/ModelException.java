package Model;

/**
 * Exception class to throw when an exception occurs in the model class
 *
 * @author Arjun Peri
 */

public class ModelException extends RuntimeException {

  public ModelException(String message) {
    super(message);
  }

  public ModelException(String message, Throwable cause) {
    super(message, cause);
  }
}
