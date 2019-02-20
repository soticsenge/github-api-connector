package github.repository.connector;

import github.repository.connector.models.HealthCheckInfo;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Maybe;
import reactor.core.publisher.Mono;
import io.micronaut.http.context.*;

import java.util.Map;


@Controller("/healthCheck")
public class HealthCheckController {
    @Value(value = "${version}")
    private String version;

    @Get(produces = MediaType.APPLICATION_JSON)
    public HealthCheckInfo index() {
        String host = ServerRequestContext
                .currentRequest()
                .map(HttpRequest::getServerName)
                .orElse("unkown");
        return new HealthCheckInfo(version, host);
    }
}
