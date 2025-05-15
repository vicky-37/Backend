package net.engineeringdigest.journalApp.Services;

import net.engineeringdigest.journalApp.APICache.APICache;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String APIkey;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private APICache apiCache;
    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisService.get("Weather_of" + city, WeatherResponse.class);
        if (weatherResponse != null) {
            return weatherResponse;
        } else {
            String finalAPI = apiCache.appCache.get(APICache.keys.WEATHER_API_URL.toString()).replace("<CITY>", city)
                    .replace("<KEY>", APIkey);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null,
                    WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if (body != null) {
                redisService.set("Weather of : " + city, body, 300l);
            }
            return body;
        }
    }
}
