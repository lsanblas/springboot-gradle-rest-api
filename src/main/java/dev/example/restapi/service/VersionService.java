package dev.example.restapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class VersionService {

    @Value("${rest.api.versions.supported}")
    private String versionsSupported;

    public boolean isVersionSupported(String apiVersion) {
        List<String> versionsSupported = Arrays.asList(this.versionsSupported.split(","));
        return versionsSupported.contains(apiVersion);
    }

    public String extractVersion(String accept) {
        try{
            String application = accept.split("/")[0];
            String mediatypeAndVersion = accept.split("/")[1];
            return mediatypeAndVersion.split("\\+")[0];
        } catch (Exception e) {
            return null;
        }
    }
}
