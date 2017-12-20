package com.rsh.framework.weixin.utils;

import com.rsh.framework.weixin.model.msg.MediaFile;
import com.squareup.okhttp.FormEncodingBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Http请求工具类
 * Created with IntelliJ IDEA
 * Created By Rsh
 * Date: 2017/12/15
 * Time: 13:10
 */
public class HttpUtils {
    private HttpUtils() {}

    private static final String GET  = "GET";
    private static final String POST = "POST";
    private static String CHARSET = "UTF-8";

    public static String get(String url) {
        return delegate.get(url);
    }

    public static String get(String url, Map<String, String> params) {
        return delegate.get(url, params);
    }

    public static String post(String url, Map<String, String> params) {
        return delegate.post(url, params);
    }

    public static String postSSL(String url, Map<String, String> params, String certPath, String certPass) {
        return delegate.postSSL(url, params, certPath, certPass);
    }

    public static MediaFile download(String url) {
        return delegate.download(url);
    }
    public static InputStream download(String url, Map<String, String> params){
        return delegate.download(url, params);
    }

    public static String upload(String url, File file, Map<String, String> params) {
        return delegate.upload(url, file, params);
    }

    /**
     * http请求工具 委托
     * 优先使用OkHttp
     * 最后使用jdk HttpURLConnection
     */
    private interface HttpDelegate {
        String get(String url);
        String get(String url, Map<String, String> params);

        String post(String url, Map<String, String> params);
        String postSSL(String url, Map<String, String> params, String certPath, String certPass);

        MediaFile download(String url);
        InputStream download(String url, Map<String, String> params);

        String upload(String url, File file, Map<String, String> params);
    }

    // http请求工具代理对象
    private static final HttpDelegate delegate;

    static {
        HttpDelegate delegateToUse = null;
        // okhttp3.OkHttpClient?
        if (ClassUtils.isPresent("okhttp3.OkHttpClient", HttpUtils.class.getClassLoader())) {
            delegateToUse = new OkHttp3Delegate();
        }
        // com.squareup.okhttp.OkHttpClient?
        else if (ClassUtils.isPresent("com.squareup.okhttp.OkHttpClient", HttpUtils.class.getClassLoader())) {
            delegateToUse = new OkHttpDelegate();
        }
        // org.apache.http.impl.client.CloseableHttpClient
        else if (ClassUtils.isPresent("org.apache.http.impl.client.CloseableHttpClient", HttpUtils.class.getClassLoader())) {
            delegateToUse = new HttpClientDelegate();
        }
        // java.net.HttpURLConnection
        else {
            delegateToUse = new HttpKitDelegate();
        }
        delegate = delegateToUse;
    }

    /**
     * OkHttp2代理
     */
    private static class OkHttpDelegate implements HttpDelegate {
        private final com.squareup.okhttp.OkHttpClient httpClient;
        private final com.squareup.okhttp.OkHttpClient httpsClient;

        Lock lock = new ReentrantLock();

        public OkHttpDelegate() {
            httpClient = new com.squareup.okhttp.OkHttpClient();
            // 分别设置Http的连接,写入,读取的超时时间
            httpClient.setConnectTimeout(10, TimeUnit.SECONDS);
            httpClient.setWriteTimeout(10, TimeUnit.SECONDS);
            httpClient.setReadTimeout(30, TimeUnit.SECONDS);

            httpsClient = httpClient.clone();
        }

        private static final com.squareup.okhttp.MediaType CONTENT_TYPE_FORM =
                com.squareup.okhttp.MediaType.parse("application/x-www-form-urlencoded");

        private String exec(com.squareup.okhttp.Request request) {
            try {
                com.squareup.okhttp.Response response = httpClient.newCall(request).execute();

                if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);

                return response.body().string();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public String get(String url) {
            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder().url(url).get().build();
            return exec(request);
        }

        @Override
        public String get(String url, Map<String, String> params) {
            com.squareup.okhttp.HttpUrl.Builder urlBuilder = com.squareup.okhttp.HttpUrl.parse(url).newBuilder();
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
                }
            }
            com.squareup.okhttp.HttpUrl httpUrl = urlBuilder.build();
            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder().url(httpUrl).get().build();
            return exec(request);
        }

        @Override
        public String post(String url, Map<String, String> params) {
            com.squareup.okhttp.RequestBody formBody = null;
            if (params != null) {
                FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    formEncodingBuilder.add(entry.getKey(), entry.getValue());
                }
                formBody = formEncodingBuilder.build();
            }
            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            return exec(request);
        }

        @Override
        public String postSSL(String url, Map<String, String> params, String certPath, String certPass) {
            com.squareup.okhttp.RequestBody formBody = null;
            if (params != null) {
                FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    formEncodingBuilder.add(entry.getKey(), entry.getValue());
                }
                formBody = formEncodingBuilder.build();
            }
            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            InputStream inputStream = null;
            try {
                // 移动到最开始，certPath io异常unlock会报错
                lock.lock();

                KeyStore clientStore = KeyStore.getInstance("PKCS12");
                inputStream = new FileInputStream(certPath);
                clientStore.load(inputStream, certPass.toCharArray());

                KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                kmf.init(clientStore, certPass.toCharArray());
                KeyManager[] kms = kmf.getKeyManagers();
                SSLContext sslContext = SSLContext.getInstance("TLSv1");

                sslContext.init(kms, null, new SecureRandom());

                httpsClient.setSslSocketFactory(sslContext.getSocketFactory());

                com.squareup.okhttp.Response response = httpsClient.newCall(request).execute();

                if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);

                return response.body().string();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                IOUtils.closeQuietly(inputStream);
                lock.unlock();
            }
        }

        @Override
        public MediaFile download(String url) {
            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder().url(url).get().build();
            try {
                com.squareup.okhttp.Response response = httpClient.newCall(request).execute();

                if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);

                com.squareup.okhttp.ResponseBody body = response.body();
                com.squareup.okhttp.MediaType mediaType = body.contentType();
                MediaFile mediaFile = new MediaFile();
                if (mediaType.type().equals("text")) {
                    mediaFile.setError(body.string());
                } else {
                    BufferedInputStream bis = new BufferedInputStream(body.byteStream());

                    String ds = response.header("Content-disposition");
                    String fullName = ds.substring(ds.indexOf("filename=\"") + 10, ds.length() - 1);
                    String relName = fullName.substring(0, fullName.lastIndexOf("."));
                    String suffix = fullName.substring(relName.length()+1);

                    mediaFile.setFullName(fullName);
                    mediaFile.setFileName(relName);
                    mediaFile.setSuffix(suffix);
                    mediaFile.setContentLength(body.contentLength() + "");
                    mediaFile.setContentType(body.contentType().toString());
                    mediaFile.setFileStream(bis);
                }
                return mediaFile;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public InputStream download(String url, Map<String, String> params) {
            com.squareup.okhttp.Request request;
            if (params != null) {
                FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    formEncodingBuilder.add(entry.getKey(), entry.getValue());
                }
                com.squareup.okhttp.RequestBody formBody = formEncodingBuilder.build();
                request = new com.squareup.okhttp.Request.Builder().url(url).post(formBody).build();
            } else {
                request = new com.squareup.okhttp.Request.Builder().url(url).get().build();
            }
            try {
                com.squareup.okhttp.Response response = httpClient.newCall(request).execute();

                if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);

                return response.body().byteStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public String upload(String url, File file, Map<String, String> params) {
            com.squareup.okhttp.RequestBody fileBody = com.squareup.okhttp.RequestBody
                    .create(com.squareup.okhttp.MediaType.parse("application/octet-stream"), file);

            com.squareup.okhttp.MultipartBuilder builder = new com.squareup.okhttp.MultipartBuilder()
                    .type(com.squareup.okhttp.MultipartBuilder.FORM)
                    .addFormDataPart("media", file.getName(), fileBody);

            if (params != null) {
                if (params != null) {
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        builder.addFormDataPart(entry.getKey(), entry.getValue());
                    }
                }
                //builder.addFormDataPart("description", params);
            }
            com.squareup.okhttp.RequestBody requestBody = builder.build();
            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            return exec(request);
        }
    }

    /**
     * OkHttp3代理
     */
    private static class OkHttp3Delegate implements HttpDelegate {
        private okhttp3.OkHttpClient httpClient;

        public OkHttp3Delegate() {
            // 分别设置Http的连接,写入,读取的超时时间
            httpClient = new okhttp3.OkHttpClient().newBuilder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
        }

        private static final okhttp3.MediaType CONTENT_TYPE_FORM =
                okhttp3.MediaType.parse("application/x-www-form-urlencoded");

        private String exec(okhttp3.Request request) {
            try {
                okhttp3.Response response = httpClient.newCall(request).execute();

                if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);

                return response.body().string();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public String get(String url) {
            okhttp3.Request request = new okhttp3.Request.Builder().url(url).get().build();
            return exec(request);
        }

        @Override
        public String get(String url, Map<String, String> params) {
            okhttp3.HttpUrl.Builder urlBuilder = okhttp3.HttpUrl.parse(url).newBuilder();
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
                }
            }
            okhttp3.HttpUrl httpUrl = urlBuilder.build();
            okhttp3.Request request = new okhttp3.Request.Builder().url(httpUrl).get().build();
            return exec(request);
        }

        @Override
        public String post(String url, Map<String, String> params) {
            okhttp3.RequestBody formBody = null;
            if (params != null) {
                okhttp3.FormBody.Builder builder = new okhttp3.FormBody.Builder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue());
                }
                formBody = builder.build();
            }
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            return exec(request);
        }

        @Override
        public String postSSL(String url, Map<String, String> params, String certPath, String certPass) {
            okhttp3.RequestBody formBody = null;
            if (params != null) {
                okhttp3.FormBody.Builder builder = new okhttp3.FormBody.Builder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue());
                }
                formBody = builder.build();
            }
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            InputStream inputStream = null;
            try {
                KeyStore clientStore = KeyStore.getInstance("PKCS12");
                inputStream = new FileInputStream(certPath);
                char[] passArray = certPass.toCharArray();
                clientStore.load(inputStream, passArray);

                KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                kmf.init(clientStore, passArray);
                KeyManager[] kms = kmf.getKeyManagers();
                SSLContext sslContext = SSLContext.getInstance("TLSv1");

                sslContext.init(kms, null, new SecureRandom());

                okhttp3.OkHttpClient httpsClient = new okhttp3.OkHttpClient()
                        .newBuilder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .sslSocketFactory(sslContext.getSocketFactory())
                        .build();

                okhttp3.Response response = httpsClient.newCall(request).execute();

                if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);

                return response.body().string();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
        }

        @Override
        public MediaFile download(String url) {
            okhttp3.Request request = new okhttp3.Request.Builder().url(url).get().build();
            try {
                okhttp3.Response response = httpClient.newCall(request).execute();

                if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);

                okhttp3.ResponseBody body = response.body();
                okhttp3.MediaType mediaType = body.contentType();
                MediaFile mediaFile = new MediaFile();
                if (mediaType.type().equals("text")) {
                    mediaFile.setError(body.string());
                } else {
                    BufferedInputStream bis = new BufferedInputStream(body.byteStream());

                    String ds = response.header("Content-disposition");
                    String fullName = ds.substring(ds.indexOf("filename=\"") + 10, ds.length() - 1);
                    String relName = fullName.substring(0, fullName.lastIndexOf("."));
                    String suffix = fullName.substring(relName.length()+1);

                    mediaFile.setFullName(fullName);
                    mediaFile.setFileName(relName);
                    mediaFile.setSuffix(suffix);
                    mediaFile.setContentLength(body.contentLength() + "");
                    mediaFile.setContentType(body.contentType().toString());
                    mediaFile.setFileStream(bis);
                }
                return mediaFile;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public InputStream download(String url, Map<String, String> params) {
            okhttp3.Request request;
            if (params != null) {
                okhttp3.RequestBody formBody = null;
                okhttp3.FormBody.Builder builder = new okhttp3.FormBody.Builder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue());
                }
                formBody = builder.build();
                request = new okhttp3.Request.Builder().url(url).post(formBody).build();
            } else {
                request = new okhttp3.Request.Builder().url(url).get().build();
            }
            try {
                okhttp3.Response response = httpClient.newCall(request).execute();

                if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);

                return response.body().byteStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public String upload(String url, File file, Map<String, String> params) {
            okhttp3.RequestBody fileBody = okhttp3.RequestBody
                    .create(okhttp3.MediaType.parse("application/octet-stream"), file);

            okhttp3.MultipartBody.Builder builder = new okhttp3.MultipartBody.Builder()
                    .setType(okhttp3.MultipartBody.FORM)
                    .addFormDataPart("media", file.getName(), fileBody);

            if (params != null) {
                if (params != null) {
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        builder.addFormDataPart(entry.getKey(), entry.getValue());
                    }
                }
                //builder.addFormDataPart("description", params);
            }
            okhttp3.RequestBody requestBody = builder.build();
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            return exec(request);
        }

    }

    /**
     * HttpClient代理
     */
    private static class HttpClientDelegate implements HttpDelegate {

        private CloseableHttpClient httpclient;

        public HttpClientDelegate() {
            httpclient = HttpClients.custom()
                    .setDefaultRequestConfig(getRequestConfig())
                    .setMaxConnTotal(50)
                    .setMaxConnPerRoute(25)
                    .setRetryHandler(new DefaultHttpRequestRetryHandler(3, true))
                    .build();
        }

        private RequestConfig getRequestConfig() {
            return RequestConfig.custom().setConnectionRequestTimeout(
                    20000).setSocketTimeout(120000).setConnectTimeout(
                    20000).build();
        }

        private String exec(HttpRequestBase request) {
            try (CloseableHttpResponse response = httpclient.execute(request);) {
                if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        return EntityUtils.toString(entity, CHARSET);
                    }
                } else {
                    throw new RuntimeException("Unexpected statusCode " + response.getStatusLine().getStatusCode() + "，" + response.getStatusLine().getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }

        @Override
        public String get(String url) {
            return get(url, null);
        }

        @Override
        public String get(String url, Map<String, String> params) {
            HttpGet httpGet = new HttpGet(buildUrlWithQueryString(url, params));
            return exec(httpGet);
        }

        @Override
        public String post(String url, Map<String, String> params) {
            HttpPost httpPost = new HttpPost(url);
            try {
                if (params != null) {
                    List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                    Set<Map.Entry<String, String>> set = params.entrySet();

                    for (Map.Entry<String, String> entry : set) {
                        paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                    }
                    UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(paramList, CHARSET);
                    httpPost.setEntity(uefEntity);
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            return exec(httpPost);
        }

        @Override
        public String postSSL(String url, Map<String, String> params, String certPath, String certPass) {
            CloseableHttpClient httpclient = null;
            InputStream inputStream = null;
            try {
                KeyStore clientStore = KeyStore.getInstance(KeyStore.getDefaultType());
                inputStream = new FileInputStream(certPath);
                char[] passArray = certPass.toCharArray();
                clientStore.load(inputStream, passArray);

                // 相信自己的CA和所有自签名的证书
                SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(clientStore, new TrustSelfSignedStrategy()).build();

                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, SSLConnectionSocketFactory.getDefaultHostnameVerifier());

                HttpPost httpPost = new HttpPost(url);
                if (params != null) {
                    List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                    Set<Map.Entry<String, String>> set = params.entrySet();

                    for (Map.Entry<String, String> entry : set) {
                        paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                    }

                    UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(paramList, CHARSET);
                    httpPost.setEntity(uefEntity);
                }

                httpclient = HttpClients.custom()
                        .setSSLSocketFactory(sslsf)
                        .setDefaultRequestConfig(getRequestConfig())
                        .setMaxConnTotal(50)
                        .setMaxConnPerRoute(25)
                        .setRetryHandler(new DefaultHttpRequestRetryHandler(3, true))
                        .build();


                try (CloseableHttpResponse response = httpclient.execute(httpPost);) {
                    if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                        HttpEntity entity = response.getEntity();
                        if (entity != null) {
                            return EntityUtils.toString(entity, CHARSET);
                        }
                    } else {
                        throw new RuntimeException("Unexpected statusCode " + response.getStatusLine().getStatusCode() + "，" + response.getStatusLine().getReasonPhrase());
                    }
                }
                return null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                IOUtils.closeQuietly(httpclient);
                IOUtils.closeQuietly(inputStream);
            }
        }

        @Override
        public MediaFile download(String url) {
            try {
                HttpPost httpPost = new HttpPost(url);
                try (CloseableHttpResponse response = httpclient.execute(httpPost);) {
                    if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                        HttpEntity entity = response.getEntity();
                        MediaFile mediaFile = new MediaFile();
                        mediaFile.setContentType(entity.getContentType().getValue());
                        if ("text/plain".equalsIgnoreCase(entity.getContentType().getValue())) {
                            mediaFile.setError(EntityUtils.toString(entity, CHARSET));
                        } else {
                            BufferedInputStream bis = new BufferedInputStream(entity.getContent());

                            String ds =  response.getLastHeader("Content-disposition").getValue();
                            String fullName = ds.substring(ds.indexOf("filename=\"") + 10, ds.length() - 1);
                            String relName = fullName.substring(0, fullName.lastIndexOf("."));
                            String suffix = fullName.substring(relName.length()+1);

                            mediaFile.setFullName(fullName);
                            mediaFile.setFileName(relName);
                            mediaFile.setSuffix(suffix);
                            mediaFile.setContentLength(entity.getContentLength()+ "");
                            mediaFile.setFileStream(bis);
                        }
                        return mediaFile;
                    } else {
                        throw new RuntimeException("Unexpected statusCode " + response.getStatusLine().getStatusCode() + "，" + response.getStatusLine().getReasonPhrase());
                    }
                }
            } catch (ClientProtocolException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public InputStream download(String url, Map<String, String> params) {
            try {
                HttpPost httpPost = new HttpPost(url);
                if (params != null) {
                    List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                    Set<Map.Entry<String, String>> set = params.entrySet();

                    for (Map.Entry<String, String> entry : set) {
                        paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                    }

                    UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(paramList, CHARSET);
                    httpPost.setEntity(uefEntity);
                }

                try (CloseableHttpResponse response = httpclient.execute(httpPost);) {
                    if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                        HttpEntity entity = response.getEntity();
                        return new BufferedInputStream(entity.getContent());
                    } else {
                        throw new RuntimeException("Unexpected statusCode " + response.getStatusLine().getStatusCode() + "，" + response.getStatusLine().getReasonPhrase());
                    }
                }
            } catch (ClientProtocolException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public String upload(String url, File file, Map<String, String> params) {
            try {
                HttpPost httpPost = new HttpPost(url);
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                // 上传的文件
                builder.addBinaryBody("media", file);

                if (params != null) {
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        builder.addTextBody(entry.getKey(), entry.getValue(), ContentType.TEXT_PLAIN.withCharset("UTF-8"));
                    }
                }
                HttpEntity httpEntity = builder.build();
                httpPost.setEntity(httpEntity);

                try (CloseableHttpResponse response = httpclient.execute(httpPost);) {
                    if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                        HttpEntity entity = response.getEntity();
                        return EntityUtils.toString(entity, CHARSET);
                    } else {
                        throw new RuntimeException("Unexpected statusCode " + response.getStatusLine().getStatusCode() + "，" + response.getStatusLine().getReasonPhrase());
                    }
                }
            } catch (ClientProtocolException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * HttpKit代理
     */
    private static class HttpKitDelegate implements HttpDelegate {
        private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36";

        /**
         * https 域名校验
         */
        private static class TrustAnyHostnameVerifier implements HostnameVerifier {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        }

        /**
         * https 证书管理
         */
        private static class TrustAnyTrustManager implements X509TrustManager {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
        }

        private static final SSLSocketFactory sslSocketFactory = initSSLSocketFactory();
        private static final TrustAnyHostnameVerifier trustAnyHostnameVerifier = new TrustAnyHostnameVerifier();

        private static SSLSocketFactory initSSLSocketFactory() {
            try {
                TrustManager[] tm = {new TrustAnyTrustManager() };
                SSLContext sslContext = SSLContext.getInstance("TLS");	// ("TLS", "SunJSSE");

                sslContext.init(null, tm, new java.security.SecureRandom());
                return sslContext.getSocketFactory();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private static HttpURLConnection getHttpConnection(String url, String method, Map<String, String> headers)
                throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
            URL _url = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)_url.openConnection();
            if (conn instanceof HttpsURLConnection) {
                ((HttpsURLConnection)conn).setSSLSocketFactory(sslSocketFactory);
                ((HttpsURLConnection)conn).setHostnameVerifier(trustAnyHostnameVerifier);
            }
            conn.setRequestMethod(method);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setConnectTimeout(20000);
            conn.setReadTimeout(20000);

            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestProperty("User-Agent", DEFAULT_USER_AGENT);

            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            return conn;
        }

        @Override
        public String get(String url) {
            return get(url, null);
        }

        @Override
        public String get(String url, Map<String, String> params) {
            HttpURLConnection conn = null;
            try {
                conn = getHttpConnection(buildUrlWithQueryString(url, params), GET, null);
                conn.connect();
                return readResponseString(conn);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }

        @Override
        public String post(String url, Map<String, String> params) {
            HttpURLConnection conn = null;
            try {
                conn = getHttpConnection(url, POST, null);
                conn.connect();

                String data = buildPostDataString(params);
                if (data != null) {
                    OutputStream out = conn.getOutputStream();
                    out.write(data.getBytes(CHARSET));
                    out.flush();
                    out.close();
                }

                return readResponseString(conn);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }

        @Override
        public String postSSL(String url, Map<String, String> params, String certPath, String certPass) {
            HttpsURLConnection conn = null;
            OutputStream out = null;
            InputStream inputStream = null;
            BufferedReader reader = null;
            try {
                KeyStore clientStore = KeyStore.getInstance("PKCS12");
                clientStore.load(new FileInputStream(certPath), certPass.toCharArray());
                KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                kmf.init(clientStore, certPass.toCharArray());
                KeyManager[] kms = kmf.getKeyManagers();
                SSLContext sslContext = SSLContext.getInstance("TLSv1");

                sslContext.init(kms, null, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
                URL _url = new URL(url);
                conn = (HttpsURLConnection) _url.openConnection();

                conn.setConnectTimeout(25000);
                conn.setReadTimeout(25000);
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestProperty("User-Agent", DEFAULT_USER_AGENT);
                conn.connect();

                String data = buildPostDataString(params);
                if (data != null) {
                    out = conn.getOutputStream();
                    out.write(data.getBytes(Charsets.UTF_8));
                    out.flush();
                    out.close();
                }

                inputStream = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream, Charsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null){
                    sb.append(line).append("\n");
                }
                return sb.toString();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                IOUtils.closeQuietly(out);
                IOUtils.closeQuietly(reader);
                IOUtils.closeQuietly(inputStream);
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }

        @Override
        public MediaFile download(String url) {
            HttpURLConnection conn = null;
            try {
                MediaFile mediaFile = new MediaFile();
                URL _url = new URL(url);
                conn = (HttpURLConnection) _url.openConnection();
                // 连接超时
                conn.setConnectTimeout(25000);
                // 读取超时 --服务器响应比较慢，增大时间
                conn.setReadTimeout(25000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestProperty("User-Agent", DEFAULT_USER_AGENT);
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.connect();

                if("text/plain".equalsIgnoreCase(conn.getContentType())){
                    // 定义BufferedReader输入流来读取URL的响应
                    InputStream in = conn.getInputStream();
                    BufferedReader read = new BufferedReader(new InputStreamReader(in, Charsets.UTF_8));
                    String valueString = null;
                    StringBuffer bufferRes = new StringBuffer();
                    while ((valueString = read.readLine()) != null){
                        bufferRes.append(valueString);
                    }
                    read.close();
                    IOUtils.closeQuietly(in);
                    mediaFile.setError(bufferRes.toString());
                }else{
                    BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                    String ds = conn.getHeaderField("Content-disposition");
                    String fullName = ds.substring(ds.indexOf("filename=\"") + 10, ds.length() - 1);
                    String relName = fullName.substring(0, fullName.lastIndexOf("."));
                    String suffix = fullName.substring(relName.length()+1);

                    mediaFile.setFullName(fullName);
                    mediaFile.setFileName(relName);
                    mediaFile.setSuffix(suffix);
                    mediaFile.setContentLength(conn.getHeaderField("Content-Length"));
                    mediaFile.setContentType(conn.getHeaderField("Content-Type"));
                    mediaFile.setFileStream(bis);
                }
                return mediaFile;

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }

        @Override
        public InputStream download(String url, Map<String, String> params) {
            HttpURLConnection conn = null;
            try {
                URL _url = new URL(url);
                conn = (HttpURLConnection) _url.openConnection();
                // 连接超时
                conn.setConnectTimeout(25000);
                // 读取超时 --服务器响应比较慢，增大时间
                conn.setReadTimeout(25000);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "Keep-Alive");
                conn.setRequestProperty("User-Agent", DEFAULT_USER_AGENT);
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.connect();

                String data = buildPostDataString(params);
                if (data != null) {
                    OutputStream out = conn.getOutputStream();
                    out.write(data.getBytes(Charsets.UTF_8));
                    out.flush();
                    IOUtils.closeQuietly(out);
                }

                return conn.getInputStream();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }

        @Override
        public String upload(String url, File file, Map<String, String> params) {
            HttpURLConnection conn = null;
            try {
                URL urlGet = new URL(url);
                conn = (HttpURLConnection) urlGet.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("connection", "Keep-Alive");
                conn.setRequestProperty("user-agent", DEFAULT_USER_AGENT);
                conn.setRequestProperty("Charsert", "UTF-8");
                // 定义数据分隔线
                String BOUNDARY = "----WebKitFormBoundaryiDGnV9zdZA1eM1yL";
                conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

                OutputStream out = new DataOutputStream(conn.getOutputStream());

                StringBuilder mediaData = new StringBuilder();
                mediaData.append("--").append(BOUNDARY).append("\r\n");
                mediaData.append("Content-Disposition: form-data;name=\"media\";filename=\""+ file.getName() + "\"\r\n");
                mediaData.append("Content-Type:application/octet-stream\r\n\r\n");
                byte[] mediaDatas = mediaData.toString().getBytes();
                out.write(mediaDatas);

                DataInputStream fs = new DataInputStream(new FileInputStream(file));
                int bytes = 0;
                byte[] bufferOut = new byte[1024];
                while ((bytes = fs.read(bufferOut)) != -1) {
                    out.write(bufferOut, 0, bytes);
                }
                IOUtils.closeQuietly(fs);

                // 多个文件时，二个文件之间加入这个
                out.write("\r\n".getBytes());

                // 拼接文本类型参数
                if (params != null) {
                    StringBuilder paramData = new StringBuilder();
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        paramData.append("--").append(BOUNDARY).append("\r\n");
                        paramData.append("Content-Disposition: form-data; name=\""+entry.getKey()+"\"\r\n");
                        paramData.append("Content-Type: text/plain; charset=" + CHARSET + "\r\n");
                        paramData.append("Content-Transfer-Encoding: 8bit" + "\r\n");
                        paramData.append("\r\n").append(entry.getValue()).append("\r\n");
                    }
                    out.write(paramData.toString().getBytes(CHARSET));
                }

                // 定义最后数据分隔线
                byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
                out.write(end_data);
                out.flush();
                IOUtils.closeQuietly(out);

                // 定义BufferedReader输入流来读取URL的响应
                InputStream in = conn.getInputStream();
                BufferedReader read = new BufferedReader(new InputStreamReader(in, CHARSET));
                String valueString = null;
                StringBuffer bufferRes = null;
                bufferRes = new StringBuffer();
                while ((valueString = read.readLine()) != null){
                    bufferRes.append(valueString);
                }
                IOUtils.closeQuietly(in);

                return bufferRes.toString();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
    }

    /**
     * Build queryString of the url
     */
    private static String buildUrlWithQueryString(String url, Map<String, String> queryParas) {
        if (queryParas == null || queryParas.isEmpty()) {
            return url;
        }

        StringBuilder sb = new StringBuilder(url);
        boolean isFirst;
        if (url.indexOf('?') == -1) {
            isFirst = true;
            sb.append('?');
        }
        else {
            isFirst = false;
        }

        for (Map.Entry<String, String> entry : queryParas.entrySet()) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append('&');
            }

            String key = entry.getKey();
            String value = entry.getValue();
            if (value != null) {
                try {value = URLEncoder.encode(value, CHARSET);} catch (UnsupportedEncodingException e) {throw new RuntimeException(e);}
            }
            sb.append(key).append('=').append(value);
        }
        return sb.toString();
    }

    /**
     * Build postDataString
     */
    private static String buildPostDataString(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        boolean isFirst = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append('&');
            }

            String key = entry.getKey();
            String value = entry.getValue();
            if (value != null) {
                try {
                    value = URLEncoder.encode(value, CHARSET);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
            sb.append(key).append('=').append(value);
        }
        return sb.toString();
    }

    private static String readResponseString(HttpURLConnection conn) {
        BufferedReader reader = null;
        try {
            StringBuilder ret;
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), CHARSET));
            String line = reader.readLine();
            if (line != null) {
                ret = new StringBuilder();
                ret.append(line);
            } else {
                return "";
            }

            while ((line = reader.readLine()) != null) {
                ret.append('\n').append(line);
            }
            return ret.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) { }
            }
        }
    }
}
