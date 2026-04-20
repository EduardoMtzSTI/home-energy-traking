package com.marman.device_service.handler;

public class DeviceNotFoudException extends RuntimeException {
    public DeviceNotFoudException(String msg){
        super(msg);
    }
}
