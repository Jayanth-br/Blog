package org.web.upload;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@Configuration
@ConfigurationProperties(prefix = "post.upload")
public class PostUploadProperties {

    private String directory;
    private DataSize maxFileSiz;

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public DataSize getMaxFileSiz() {
        return maxFileSiz;
    }

    public void setMaxFileSiz(DataSize maxFileSiz) {
        this.maxFileSiz = maxFileSiz;
    }
}
