//package cn.neud.trace.gateway.config;
//
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//public class CookiesFilter implements Ordered, GlobalFilter {
//
//    @Override
//    public Mono filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest().mutate().headers((httpHeaders) -> {
//            String csrf = new HttpCookie("csrf", "3838a26d-07f7-11e9-b5f7").toString();
//            String ssn = new HttpCookie("ssn", "MTU0NT").toString();
//            httpHeaders.set("Cookie", csrf + ";" + ssn);
//        }).build();
//        return chain.filter(exchange.mutate().request(request).build());
//    }
//
//    @Override
//    public int getOrder() {
//        return -100;
//    }
//
//}