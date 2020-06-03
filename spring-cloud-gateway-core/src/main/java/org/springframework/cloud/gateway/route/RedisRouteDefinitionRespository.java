package org.springframework.cloud.gateway.route;

import io.lettuce.core.RedisCommandExecutionException;
import org.springframework.data.redis.core.RedisTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 基于Redis的Routes仓库
 * @Author: wangzhichao
 * @Date: 2020/6/3 10:51
 */
public class RedisRouteDefinitionRespository implements RouteDefinitionRepository {

	private static final String REDIS_GATEWAY_REPO_KEY = "gateway:redis:routes:";

	private final RedisTemplate<String,RouteDefinition> redisTemplate;

	public RedisRouteDefinitionRespository(RedisTemplate<String,RouteDefinition> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public Flux<RouteDefinition> getRouteDefinitions() {
		return Flux.fromArray(redisTemplate.opsForHash()
				.entries(REDIS_GATEWAY_REPO_KEY)
				.entrySet().toArray(new RouteDefinition[]{}));
	}

	@Override
	public Mono<Void> save(Mono<RouteDefinition> route) {
		return route.flatMap(r -> {
			redisTemplate.opsForHash()
					.put(REDIS_GATEWAY_REPO_KEY,r.getId(),r);
			return Mono.empty();
		});
	}

	@Override
	public Mono<Void> delete(Mono<String> routeId) {
		return routeId.flatMap(id -> {
			if(redisTemplate.opsForHash().hasKey(REDIS_GATEWAY_REPO_KEY,id)) {
				redisTemplate.opsForHash()
						.delete(REDIS_GATEWAY_REPO_KEY,id);
				return Mono.empty();
			}
			return Mono.defer(() -> Mono.error(new RedisCommandExecutionException("未找到需要删除的Route")));
		});
	}
}
