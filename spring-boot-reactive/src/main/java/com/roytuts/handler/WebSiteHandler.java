package com.roytuts.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.roytuts.model.WebSite;
import com.roytuts.repository.WebSiteRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WebSiteHandler {

	@Autowired
	private WebSiteRepository webSiteRepository;

	public Mono<ServerResponse> getAllWebSites(ServerRequest serverRequest) {
		Flux<WebSite> webSites = webSiteRepository.findAll();
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(webSites, WebSite.class);
	}

	public Mono<ServerResponse> addWebSite(ServerRequest serverRequest) {
		Mono<WebSite> webSite = serverRequest.bodyToMono(WebSite.class);
		return ServerResponse.ok().build(webSiteRepository.add(webSite));
	}

	public Mono<ServerResponse> updateWebSite(ServerRequest serverRequest) {
		Mono<WebSite> webSite = serverRequest.bodyToMono(WebSite.class);
		return ServerResponse.ok().build(webSiteRepository.update(webSite));
	}

	public Mono<ServerResponse> deleteWebSite(ServerRequest serverRequest) {
		Integer webSiteId = Integer.valueOf(serverRequest.pathVariable("id"));
		webSiteRepository.delete(webSiteId);
		return ServerResponse.ok().build(Mono.empty());
	}

	public Mono<ServerResponse> getWebSite(ServerRequest serverRequest) {
		Integer webSiteId = Integer.valueOf(serverRequest.pathVariable("id"));
		Mono<ServerResponse> notFound = ServerResponse.notFound().build();
		Mono<WebSite> webSiteMono = webSiteRepository.findById(webSiteId);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(webSiteMono, WebSite.class)
				.switchIfEmpty(notFound);
	}

}
