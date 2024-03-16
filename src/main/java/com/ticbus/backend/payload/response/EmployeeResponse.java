package com.ticbus.backend.payload.response;

import com.ticbus.backend.model.Employee;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmployeeResponse extends GetSingleItemResponse<Employee> implements Serializable {

}
