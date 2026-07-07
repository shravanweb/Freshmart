package d3e.core;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HttpContext {
  @Autowired private HttpServletRequest request;
  @Autowired private HttpServletResponse response;
  
  public HttpServletResponse getResponse() {
    return response;
  }
  
  public HttpServletRequest getRequest() {
    return request;
  }
}
