package com.ticbus.backend.payload.request;

import com.ticbus.backend.common.enumeration.EnumStatus;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class CustomerUpdateRequest implements Serializable {
    @NotNull
    private EnumStatus status;
}
