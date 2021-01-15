package com.yanger.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {
	
	@Value("${server.servlet.context-path}")
	private String serverName;
	
	@Value("${yg-file.ip}")
	private String ip;
	
	private int serverPort;

	public String getUrl() {
		return "http://" + ip + ":" + this.serverPort + serverName;
    }

	@Override
	public void onApplicationEvent(WebServerInitializedEvent event) {
		this.serverPort = event.getWebServer().getPort();
	}

}
