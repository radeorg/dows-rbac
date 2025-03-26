package org.dows.rbac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class EncryptionPropertiesPostProcessor implements EnvironmentPostProcessor {

    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "3fb2uPksjNOnxZI3";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Map<String, Object> decryptedProperties = new HashMap<>();
        // 遍历所有 PropertySource
        for (PropertySource<?> propertySource : environment.getPropertySources()) {
            if (propertySource instanceof MapPropertySource) {
                MapPropertySource mapPropertySource = (MapPropertySource) propertySource;
                // 遍历当前 PropertySource 中的所有属性
                for (String propertyName : mapPropertySource.getPropertyNames()) {
                    Object propertyValue = mapPropertySource.getProperty(propertyName);
                    if (propertyValue != null && propertyValue.toString().startsWith("encrypted$")) {
                        String encryptedValue = propertyValue.toString().substring("encrypted$".length());
                        String decryptedValue = decrypt(encryptedValue);
                        decryptedProperties.put(propertyName, decryptedValue);
                    }
                }
            }
        }
        // 将解密后的属性添加到环境中
        environment.getPropertySources().addFirst(new MapPropertySource("decryptedProperties", decryptedProperties));
    }

    private String decrypt(String encryptedValue) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedValue);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }
}