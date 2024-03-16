package com.ticbus.backend.payload.response;

import com.ticbus.backend.model.Trip;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TripResponse extends GetSingleItemResponse<Trip> {
}
