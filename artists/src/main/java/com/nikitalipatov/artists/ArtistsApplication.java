package com.nikitalipatov.artists;

import com.nikitalipatov.common.feign.AlbumClient;
import com.nikitalipatov.common.feign.ArtistClient;
import com.nikitalipatov.common.feign.TrackClient;
import com.nikitalipatov.common.kafka.KafkaConsumerConfig;
import com.nikitalipatov.common.kafka.KafkaProducerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableCaching
@Import({ KafkaConsumerConfig.class, KafkaProducerConfig.class})
@EnableFeignClients(clients = {TrackClient.class, AlbumClient.class, ArtistClient.class})
public class ArtistsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtistsApplication.class, args);
    }

}
