package ru.mativ.lrfbb.rest.pojo;

public class Version {

    private final String method = "version";
    private final String name;
    private final String version;

    public Version(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getMethod() {
        return method;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

}
