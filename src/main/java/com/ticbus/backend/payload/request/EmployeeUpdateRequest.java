package com.ticbus.backend.payload.request;

import com.ticbus.backend.common.enumeration.EnumRole;
import com.ticbus.backend.common.enumeration.EnumStatus;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class EmployeeUpdateRequest implements Serializable {
    @NotNull
    private Integer id;
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String password;
}
