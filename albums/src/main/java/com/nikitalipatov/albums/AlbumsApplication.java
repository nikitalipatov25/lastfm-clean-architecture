package com.nikitalipatov.albums;

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
public class AlbumsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlbumsApplication.class, args);
    }

}
