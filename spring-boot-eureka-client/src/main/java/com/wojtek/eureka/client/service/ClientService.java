package com.wojtek.eureka.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class ClientService {

    private final DiscoveryClient discoveryClient;

    @Autowired
    public ClientService(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/services")
    public List<String> services() {
        return this.discoveryClient.getServices();
    }

    @GetMapping("/instances")
    public List<ServiceInstance> instances() {
        return services().stream()
                .flatMap(id -> instances(id).stream())
                .collect(toList());
    }

    @GetMapping("/instances/{id}")
    public List<ServiceInstance> instances(@PathVariable String id) {
        return this.discoveryClient.getInstances(id);
    }
}
