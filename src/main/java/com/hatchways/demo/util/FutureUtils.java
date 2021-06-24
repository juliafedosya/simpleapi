package com.hatchways.demo.util;

import com.hatchways.demo.util.exception.FutureException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

@UtilityClass
public class FutureUtils {

  /**
   * Waits if necessary for the computation to complete, and then retrieves its result.
   *
   * @param future   future
   * @param errorMsg error message
   * @param <T>      generic type
   * @return the computed result
   */
  public <T> T getSafety(Future<T> future, String errorMsg) {
    try {
      return future.get();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw handleException(e, errorMsg);
    } catch (ExecutionException e) {
      throw handleException(e, errorMsg);
    }
  }

  private RuntimeException handleException(Exception e, String errorMsg) {
    return new FutureException(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR, e.getCause());
  }

}
