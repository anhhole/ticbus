package com.ticbus.backend.payload.response;

import com.ticbus.backend.model.Bus;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BusResponse extends GetSingleItemResponse<Bus> implements Serializable {

}
