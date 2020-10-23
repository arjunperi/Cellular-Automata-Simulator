package Controller;


/**
 * This class allows for custom exceptions to be throw in the Controller class.
 *
 * @author Arjun Peri
 */
public class ControllerException extends RuntimeException {

  /**
   * Allows throwing a ControllerException with custom message.
   * @param message
   */
  public ControllerException(String message) {
    super(message);
  }

  /**
   * Allows throwing a ControllerException with custom message and cause.
   * @param message
   */
  public ControllerException(String message, Throwable cause) {
    super(message, cause);
  }
}

