package com.yanger.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {
	
	@Value("${server.servlet.context-path}")
	private String serverName;
	
	
	private int serverPort;

	public String getUrl() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "http://" + address.getHostAddress() + ":" + this.serverPort + serverName;
    }

	@Override
	public void onApplicationEvent(WebServerInitializedEvent event) {
		this.serverPort = event.getWebServer().getPort();
	}

}
