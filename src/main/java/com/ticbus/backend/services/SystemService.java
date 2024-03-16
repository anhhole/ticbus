package com.ticbus.backend.services;

import com.ticbus.backend.model.SystemInformation;
import com.ticbus.backend.payload.request.SystemInformationRequest;
import com.ticbus.backend.payload.response.SystemInformationResponse;
import com.ticbus.backend.repository.SystemRepository;
import com.ticbus.backend.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class SystemService {

    @Autowired
    private SystemRepository systemRepository;

    public SystemInformationResponse settingSystemInformation(SystemInformationRequest request) {
        SystemInformationResponse response = new SystemInformationResponse();
        response.setRd(CommonConstants.SETTING_SYSTEM_INFORMATION_FAIL);
        try {
            if (!Objects.isNull(request)) {
                SystemInformation systemInformation = new SystemInformation();
                systemInformation = checkingSystemInformationRequestAndManipulateData(request);
                response.setItem(systemInformation);
                response.setRd(CommonConstants.STR_SUCCESS_STATUS);
                response.setRc(HttpStatus.CREATED.value());
            }
        } catch (Exception e) {
            log.error("Exception while setting system information: ", e);
        }
        return response;
    }

    public SystemInformationResponse updateSystemInformation(String systemId, SystemInformationRequest request) {
        SystemInformationResponse response = new SystemInformationResponse();
        response.setRd(CommonConstants.UPDATING_SYSTEM_INFORMATION_FAIL);
        try {
            if (StringUtils.isNotEmpty(systemId) && !Objects.isNull(request)) {
                Optional<SystemInformation> systemInformation = systemRepository.findSystemInformationById(systemId);
                if (systemInformation.isPresent()) {
                    SystemInformation result = systemInformation.get();
                    result = checkingSystemInformationRequestAndManipulateData(request);
                    response.setItem(result);
                    response.setRd(CommonConstants.STR_SUCCESS_STATUS);
                    response.setRc(HttpStatus.OK.value());
                }
            }
        } catch (Exception e) {
            log.error("Exception while updating system information");
        }
        return response;
    }

    private SystemInformation checkingSystemInformationRequestAndManipulateData(SystemInformationRequest request) {
        SystemInformation systemInformation = new SystemInformation();
        if (StringUtils.isNotEmpty(request.getBusStationName())) {
            systemInformation.setBusStationName(request.getBusStationName());
        }
        if (StringUtils.isNotEmpty(request.getAddress1())) {
            systemInformation.setAddress1(request.getAddress1());
        }
        if (StringUtils.isNotEmpty(request.getAddress2())) {
            systemInformation.setAddress2(request.getAddress2());
        }
        if (StringUtils.isNotEmpty(request.getBusStationRouteDescription())) {
            systemInformation.setBusStationRouteDescription(request.getBusStationRouteDescription());
        }
        if (StringUtils.isNotEmpty(request.getDescription())) {
            systemInformation.setDescription(request.getDescription());
        }
        if (StringUtils.isNotEmpty(request.getHotlineNumber())) {
            systemInformation.setHotlineNumber(request.getHotlineNumber());
        }
        if (StringUtils.isNotEmpty(request.getPhone())) {
            systemInformation.setPhone(request.getPhone());
        }
        if (StringUtils.isNotEmpty(request.getWebsiteName())) {
            systemInformation.setWebsiteName(request.getWebsiteName());
        }
        return systemInformation;
    }
}
