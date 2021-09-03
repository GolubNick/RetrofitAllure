package utils;

import io.qameta.allure.attachment.AttachmentData;
import io.qameta.allure.attachment.AttachmentProcessor;
import io.qameta.allure.attachment.DefaultAttachmentProcessor;
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;
import io.qameta.allure.attachment.http.HttpRequestAttachment;
import io.qameta.allure.attachment.http.HttpRequestAttachment.Builder;
import io.qameta.allure.attachment.http.HttpResponseAttachment;
import okhttp3.*;
import okio.Buffer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class CustomLoggingInterceptor implements Interceptor {

    private Logger logger = Logger.getLogger(CustomLoggingInterceptor.class.getName());
    private byte[] bytes;

    private String requestTemplatePath = "http-request.ftl";
    private String responseTemplatePath = "http-response.ftl";

    public CustomLoggingInterceptor() {
    }

    public Response intercept(Chain chain) throws IOException {
        AttachmentProcessor<AttachmentData> processor = new DefaultAttachmentProcessor();
        Request request = chain.request();
        printRequestLogs(request);
        String requestUrl = request.url().toString();
        Builder requestAttachmentBuilder = Builder.create("Request", requestUrl).setMethod(request.method()).setHeaders(toMapConverter(request.headers().toMultimap()));
        RequestBody requestBody = request.body();
        if (Objects.nonNull(requestBody)) {
            requestAttachmentBuilder.setBody(readRequestBody(requestBody));
        }

        HttpRequestAttachment requestAttachment = requestAttachmentBuilder.build();
        processor.addAttachment(requestAttachment, new FreemarkerAttachmentRenderer(this.requestTemplatePath));
        Response response = chain.proceed(request);
        HttpResponseAttachment.Builder responseAttachmentBuilder = HttpResponseAttachment.Builder.create("Response").setResponseCode(response.code()).setHeaders(toMapConverter(response.headers().toMultimap()));
        Response.Builder responseBuilder = response.newBuilder();
        ResponseBody responseBody = response.body();
        if (Objects.nonNull(responseBody)) {
            bytes = responseBody.bytes();
            responseAttachmentBuilder.setBody(new String(bytes, StandardCharsets.UTF_8));
            responseBuilder.body(ResponseBody.create(responseBody.contentType(), bytes));
        }
        printResponseLogs(response);
        HttpResponseAttachment responseAttachment = responseAttachmentBuilder.build();
        processor.addAttachment(responseAttachment, new FreemarkerAttachmentRenderer(this.responseTemplatePath));
        return responseBuilder.build();
    }

    private static Map<String, String> toMapConverter(Map<String, List<String>> items) {
        Map<String, String> result = new HashMap();
        items.forEach((key, value) -> {
            String var10000 = (String)result.put(key, String.join("; ", value));
        });
        return result;
    }

    private static String readRequestBody(RequestBody requestBody) throws IOException {
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        return buffer.readString(StandardCharsets.UTF_8);
    }

    private void printRequestLogs(Request request) throws IOException {
        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        String requestStartMessage = "--> "
                + request.method()
                + ' ' + request.url();
        if (hasRequestBody) {
            requestStartMessage += readRequestBody(requestBody) + " (" + requestBody.contentLength() + "-byte body)";
        }
        logger.info(requestStartMessage);
    }


    private void printResponseLogs(Response response) throws IOException {
        long startNs = System.nanoTime();
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        long contentLength = response.body().contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        logger.info("<-- "
                + response.code()
                + (response.message().isEmpty() ? "" : ' ' + response.message())
                + ' ' + response.request().url()
                + ' ' + new String(Objects.nonNull(bytes) ? bytes : new byte[0], StandardCharsets.UTF_8)
                + " (" + tookMs + "ms" + (", " + bodySize + " body") + ')');
    }


}
