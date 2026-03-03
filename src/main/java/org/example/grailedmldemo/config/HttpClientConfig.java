package org.example.grailedmldemo.config;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

//OKHttp bean configuration for making HTTP requests to external APIs, such as the Grailed API.
//+possibly include VPN/proxy configuration if needed for bypassing security.
@Configuration
public class HttpClientConfig {
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(Duration.ofSeconds(5))
                .readTimeout(Duration.ofSeconds(20))
                .writeTimeout(Duration.ofSeconds(20))
                .callTimeout(Duration.ofSeconds(30))
                // .addInterceptor(new HttpLoggingInterceptor().setLevel(Level.BASIC)) // Add logging interceptor for debugging purposes
                // .proxySelector(selector) // Add later if needed for VPN/proxy configuration
                .build();
    }
    // Example stub for a ProxySelector bean if we need to route requests through a proxy or VPN.
    // @Bean
    // public ProxySelector proxySelector() {
    //     // Return a ProxySelector that knows how to pick a proxy or VPN endpoint.
    //     // For a simple fixed proxy:
    //     // return ProxySelector.of(new InetSocketAddress("proxy.mycorp.com", 3128));
    // }
}
