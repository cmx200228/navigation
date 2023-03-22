package com.cap.navigation.config;import lombok.Data;import org.apache.http.client.HttpClient;import org.apache.http.client.config.RequestConfig;import org.apache.http.conn.HttpClientConnectionManager;import org.apache.http.impl.client.HttpClientBuilder;import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;import org.springframework.http.client.ClientHttpRequestFactory;import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;import org.springframework.http.converter.HttpMessageConverter;import org.springframework.http.converter.StringHttpMessageConverter;import org.springframework.web.client.RestTemplate;import java.nio.charset.Charset;import java.util.List; /**
 * @author 陈蒙欣
 * @date 2023/3/21 21:10
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "application-dev", ignoreUnknownFields = true)
public class HttpClientConfig {

    /**
     * 连接池的最大连接数
     */
    @Value("${http.maxTotal}")
    private Integer maxTotal;

    /**
     * 单个主机的最大连接数
     */
    @Value("${http.defaultMaxPerRoute}")
    private Integer defaultMaxPerRoute;

    /**
     * 连接超时时间
     */
    @Value("${http.connectTimeout}")
    private Integer connectTimeout;

    /**
     * 请求超时时间
     */
    @Value("${http.connectionRequestTimeout}")
    private Integer connectionRequestTimeout;

    /**
     * 响应超时时间
     */
    @Value("${http.socketTimeout}")
    private Integer socketTimeout;

    /**
     * HttpClient连接池
     * @return
     */
    @Bean
    public HttpClientConnectionManager httpClientConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxTotal);
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        return connectionManager;
    }

    /**
     * 注册RequestConfig
     * @return
     */
    @Bean
    public RequestConfig requestConfig() {
        return RequestConfig.custom().setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout).setSocketTimeout(socketTimeout)
                .build();
    }

    /**
     * 注册HttpClient
     * @param manager
     * @param config
     * @return
     */
    @Bean
    public HttpClient httpClient(HttpClientConnectionManager manager, RequestConfig config) {
        return HttpClientBuilder.create().setConnectionManager(manager).setDefaultRequestConfig(config)
                .build();
    }

    @Bean
    public ClientHttpRequestFactory requestFactory(HttpClient httpClient) {
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }
    /**
     * 使用HttpClient来初始化一个RestTemplate
     * @param requestFactory
     * @return
     */
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory requestFactory) {
        RestTemplate template = new RestTemplate(requestFactory);

        List<HttpMessageConverter<?>> list = template.getMessageConverters();
        for (HttpMessageConverter<?> mc : list) {
            if (mc instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) mc).setDefaultCharset(Charset.forName("UTF-8"));
            }
        }
        return template;
    }
}
