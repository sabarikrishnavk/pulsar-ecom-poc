package org.springframework.integration.pulsar;

public class MyMsg2 {
    private String data;
    public MyMsg2(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
