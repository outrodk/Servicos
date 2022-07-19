package com.soulcode.Servicos.Config;


import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
public class CacheConfig {

    //pega um objeto do redis e transforma em um objeto java e pega um objeto do java e transforma num objeto do redis
    private final RedisSerializationContext.SerializationPair<Object> serializationPair =
            RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer());

    @Bean
    public RedisCacheConfiguration cacheConfiguration() { //costumizar a config padrão do redis cache
        return RedisCacheConfiguration
                .defaultCacheConfig() //costumizar informações padrões
                .entryTtl(Duration.ofMinutes(5)) //todos os caches terão 5 min por padrão (tempo de vida)
                .disableCachingNullValues() // não salva valores nulos
                .serializeValuesWith(serializationPair); //converte do redis p/ json e vice-versa
    }


    //caso n crie essa configuração tudo terá o padrão de 5min como na função acima
    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration("clientesCache",
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofSeconds(30))
                        .serializeValuesWith(serializationPair)
        )
                .withCacheConfiguration("chamadosCache",
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofSeconds(10))
                        .serializeValuesWith(serializationPair)
        )
                .withCacheConfiguration("funcionariosCache",
                RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofSeconds(10))
                                .serializeValuesWith(serializationPair)
        )
                .withCacheConfiguration("userCache",
                RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofSeconds(5))
                                .serializeValuesWith(serializationPair)
        )
                .withCacheConfiguration("userCache",
                RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofSeconds(5))
                                .serializeValuesWith(serializationPair)
        )
                .withCacheConfiguration("authCache",
                RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofSeconds(5))
                                .serializeValuesWith(serializationPair)
        );


    }


}
