package com.formacionbdi.springboot.app.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PostElapsedTimeFilter extends ZuulFilter {

  @Override
  public Object run() throws ZuulException {
    RequestContext context = RequestContext.getCurrentContext();
    HttpServletRequest request = context.getRequest();

    log.info("Executing post filter");

    Long startTime = (Long) request.getAttribute("elapsedTime");
    request.setAttribute("startTime", startTime);
    Long finishTime = System.currentTimeMillis();
    Long elapsedTime = finishTime - startTime;

    log.info(String.format("Elapsed time in seconds: %s", (elapsedTime.doubleValue() / 1000)));
    log.info(String.format("Elapsed time in miliseconds: %s", elapsedTime));

    return null;
  }

  @Override
  public String filterType() {
    return "post";
  }

  @Override
  public int filterOrder() {
    return 0;
  }

  @Override
  public boolean shouldFilter() {
    return false;
  }
}
