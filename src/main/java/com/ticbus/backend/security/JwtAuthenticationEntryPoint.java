package com.ticbus.backend.security;

import com.ticbus.backend.payload.response.BaseResponse;
import com.ticbus.backend.util.JSONUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author AnhLH
 */
@Component
@Log4j2
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      AuthenticationException e) throws IOException, ServletException {
    log.error("Responding with unauthorized error. Message - {}", e.getMessage());
    BaseResponse response = new BaseResponse();
    response.setUnAuthor();
    response.setRd(e.getMessage());
    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    httpServletResponse.setContentType("application/json");
    httpServletResponse.getWriter().print(JSONUtils.Serialize(response));
  }
}
