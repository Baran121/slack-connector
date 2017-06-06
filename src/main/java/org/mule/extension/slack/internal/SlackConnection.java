package org.mule.extension.slack.internal;

import static java.lang.String.valueOf;
import org.mule.runtime.core.api.util.IOUtils;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpClientConfiguration;
import org.mule.runtime.http.api.domain.ParameterMap;
import org.mule.runtime.http.api.domain.entity.InputStreamHttpEntity;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class SlackConnection {

    private HttpService httpService;
    private String token;
    private final HttpClient httpClient;

    public SlackConnection(HttpService httpService, String token) {
        this.httpService = httpService;
        this.token = token;

        HttpClientConfiguration build = new HttpClientConfiguration.Builder().setName("hola").build();
        httpClient = httpService.getClientFactory().create(build);
        httpClient.start();
    }

    public InputStream postMessage(@Content(primary = true) String message,
                                   String channel,
                                   @Content @Optional InputStream attachments,
                                   @Optional String username,
                                   @Optional(defaultValue = "false") boolean asUser) throws IOException, TimeoutException {
        ParameterMap parameterMap = new ParameterMap();
        parameterMap.put("channel", channel);
        parameterMap.put("text", message);
        ifPresent(attachments, att -> parameterMap.put("attachments", IOUtils.toString(att)));
        ifPresent(username, user -> parameterMap.put("username", user));
        ifPresent(asUser, asUs -> parameterMap.put("as_user", valueOf(asUs)));
        return sendRequest("https://slack.com/api/chat.postMessage", parameterMap);
    }

    public InputStream listChannels() throws IOException, TimeoutException {
        return sendRequest("https://slack.com/api/channels.list", new ParameterMap());
    }

    private InputStream sendRequest(String uri, ParameterMap parameterMap) throws IOException, TimeoutException {
        parameterMap.put("token", token);

        HttpResponse send = httpClient.send(HttpRequest.builder()
                .setMethod("GET")
                .setUri(uri)
                .setQueryParams(parameterMap)
                .build(), 5000, true, null);

        return ((InputStreamHttpEntity) send.getEntity()).getInputStream();
    }

    private <T> void ifPresent(T value, Consumer<T> consumer){
        if(value != null) {
            consumer.accept(value);
        }
    }

    public void disconnect(){
        httpClient.stop();
    }
}
