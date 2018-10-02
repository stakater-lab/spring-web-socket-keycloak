package io.aurora.socketapp.config;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class HazelcastSessionConfig
{
    private Environment environment;

    public HazelcastSessionConfig(Environment environment)
    {
        this.environment = environment;
    }

    @Bean
    public HazelcastInstance hazelcastInstance()
    {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getNetworkConfig().addAddress(environment.getProperty("HAZELCAST_URL"));
        clientConfig.getNetworkConfig().setConnectionAttemptLimit(10);
        clientConfig.getNetworkConfig().setConnectionAttemptPeriod(24 * 60);
        clientConfig.getNetworkConfig().setConnectionTimeout(1000);
        HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);
        return hazelcastInstance;
    }

}
