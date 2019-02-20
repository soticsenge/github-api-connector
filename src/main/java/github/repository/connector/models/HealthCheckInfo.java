package github.repository.connector.models;

import java.util.HashMap;
import java.util.Map;

public class HealthCheckInfo {
    private String version;
    private String currentHost;

    public HealthCheckInfo(String version, String currentHost) {
        this.version = version;
        this.currentHost = currentHost;
    }

    public String getVersion() {
        return version;
    }

    public String getCurrentHost() {
        return currentHost;
    }

    public Map<String, String> toMap() {
        Map<String, String> m = new HashMap<>();
        m.put("version", getVersion());
        m.put("currentHost", getCurrentHost());
        return m;
    }

    public static HealthCheckInfo fromMap(Map<String, String> m) {
        return new HealthCheckInfo(
                m.getOrDefault("version", "0.0"),
                m.getOrDefault("currentHost", "unknown"));
    }

}
