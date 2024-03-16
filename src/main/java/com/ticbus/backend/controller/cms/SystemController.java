package com.ticbus.backend.controller.cms;

import com.ticbus.backend.payload.request.SystemInformationRequest;
import com.ticbus.backend.payload.response.SystemInformationResponse;
import com.ticbus.backend.services.SystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cms/system")
@Slf4j
@Api(value = "CMS System Information Api", description = "CMS System Information API ")
public class SystemController {

  @Autowired
  private SystemService systemService;

  @ApiOperation(value = "Setting system information", authorizations = {
      @Authorization(value = "Bearer")})
  @PostMapping("/information")
  public ResponseEntity<SystemInformationResponse> settingSystemInformation(
      @RequestBody SystemInformationRequest request) {
    log.info("START: setting system information");
    SystemInformationResponse response = systemService.settingSystemInformation(request);
    log.info("END: setting system information");
    return ResponseEntity.ok(response);
  }

  @ApiOperation(value = "Updating system information", authorizations = {
      @Authorization(value = "Bearer")})
  @PutMapping("/{systemId}/information")
  public ResponseEntity<SystemInformationResponse> updateSystemInformation(
      @PathVariable(name = "systemId") String systemId,
      @RequestBody SystemInformationRequest request) {
    log.info("START: updating system information");
    SystemInformationResponse response = systemService.updateSystemInformation(systemId, request);
    log.info("END: updating system information");
    return ResponseEntity.ok(response);
  }
}
