package ru.jokardo.music_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.grpc.client.ImportGrpcClients;
import ru.jokardo.auth.contracts.grpc.UserProfileServiceGrpc;

@Configuration
public class GrpcClientConfig {

    @Bean
    public UserProfileServiceGrpc.UserProfileServiceBlockingStub userProfileStub(
            GrpcChannelFactory channelFactory) {
        // "auth-service" должно совпадать с именем канала в application.yaml
        return UserProfileServiceGrpc.newBlockingStub(
                channelFactory.createChannel("auth-service")
        );
    }

}
