package com.strateknia.spring;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id="custom-actuator")
public class CustomActuator {

    @ReadOperation
    public String readOperation() {
        return "ReadOperation";
    }

    @WriteOperation
    public String writeOperation() {
        return "WriteOperation";
    }

    @DeleteOperation
    public String deleteOperation() {
        return "DeleteOperation";
    }
}
