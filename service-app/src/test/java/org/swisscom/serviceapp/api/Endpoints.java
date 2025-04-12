package org.swisscom.serviceapp.api;

public enum Endpoints {

    SAVE("/v1/service"), UPDATE("/v1/service/{id}"), FIND_BY_ID("/v1/service/{id}");

    final String uri;

    Endpoints(String uri) {
        this.uri = uri;
    }
    public String getUri() {
        return this.uri;
    }
}
