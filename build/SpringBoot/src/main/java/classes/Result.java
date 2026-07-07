package classes;

import d3e.core.ListExt;
import java.util.List;

public class Result<T> {
  public ResultStatus status = ResultStatus.Success;
  public List<String> errors = ListExt.asList();
  public T value;

  public Result(List<String> errors, ResultStatus status, T value) {
    this.errors = errors;
    this.status = status;
    this.value = value;
  }
}
