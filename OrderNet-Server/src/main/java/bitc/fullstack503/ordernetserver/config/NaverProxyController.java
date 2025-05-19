package bitc.fullstack503.ordernetserver.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/proxy")
public class NaverProxyController {

    @GetMapping("/geocode")
    public ResponseEntity<?> getGeocode(@RequestParam String query) {
        // 인증 id
        String clientId = "9dw5r83hx7";
        // 인증 키
        String clientSecret = "J1Y2FZXxE2JirPFeAu6UhBCUi2DF5qyjutPHbIBX";

        WebClient webClient = WebClient.builder()
                // 지오코딩 api 주소
                .baseUrl("https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode")
                .defaultHeader("X-NCP-APIGW-API-KEY-ID", clientId)
                .defaultHeader("X-NCP-APIGW-API-KEY", clientSecret)
                .build();

        return webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("query", query).build())
                .retrieve()
                .toEntity(String.class)
                .block();
    }
}
