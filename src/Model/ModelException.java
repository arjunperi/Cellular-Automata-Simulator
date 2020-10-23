package Model;

/**
 * Exception class to throw when an exception occurs in the model class
 *
 * @author Arjun Peri
 */

public class ModelException extends RuntimeException {

  /**
   * Allows throwing a ModelException with custom message.
   * @param message
   */
  public ModelException(String message) {
    super(message);
  }


  /**
   * Allows throwing a ModelException with custom message and cause.
   * @param message
   */
  public ModelException(String message, Throwable cause) {
    super(message, cause);
  }
}
