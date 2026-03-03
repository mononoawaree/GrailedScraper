package org.example.grailedmldemo.model;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Optional;

enum HttpStatus {
    OK(200),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    private final int code;

    HttpStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

//body + status code + headers (if needed) for storing the raw HTTP response from the Grailed website, which will then be parsed and normalized.
public class HttpResponse {

    private String httpVersion;
    private HttpStatus status;
    private int statusCode;
    private String reasonPhrase;
    private ByteArrayOutputStream body;
    private List<Cookie> cookies;
    private LinkedHashMap<String, List<String>> headers;

    public HttpResponse(String httpVersion, int statusCode, String reasonPhrase, ByteArrayOutputStream body, LinkedHashMap<String, List<String>> headers) {
        this.httpVersion = httpVersion;
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.body = body;
        this.headers = headers;
    }

    // ─────────────────────────────────────────────────────────────
// DECOMPRESSION — Grailed sends gzip/brotli on every response
// ─────────────────────────────────────────────────────────────

    /**
     * Decode body bytes using the Content-Encoding header automatically.
     * Handles gzip, deflate, br (Brotli). Returns raw bytes if no encoding.
     */
    public byte[] decodedBody() {
        return body.toByteArray();
    }

    /**
     * Decompress + decode bytes to String using charset from Content-Type.
     * This is what you actually call to get readable HTML/JSON from Grailed.
     */
    public String bodyAsString() {
        return "";
    }

    /**
     * Returns the encoding used e.g. "gzip", "br", or null if none.
     */
    public String contentEncoding() {
        return null;
    }


// ─────────────────────────────────────────────────────────────
// CHARSET — resolve charset from Content-Type header
// ─────────────────────────────────────────────────────────────

    /**
     * Parses Content-Type header and extracts charset.
     * e.g. "text/html; charset=UTF-8" → Charset.UTF_8
     * Falls back to UTF-8 if not specified.
     */
    public Charset charset() {
        return Charset.UTF_8;
    }


// ─────────────────────────────────────────────────────────────
// CLOUDFLARE DETECTION — Grailed is behind Cloudflare
// ─────────────────────────────────────────────────────────────

    /**
     * Returns true if response contains a CF-Ray header,
     * indicating you're behind Cloudflare.
     */
    public boolean isBehindCloudflare() {
        return false;
    }

    /**
     * Returns true if this is a Cloudflare challenge page —
     * a 200 or 403 whose body contains a JS challenge, not real content.
     * You cannot parse Grailed data from this response.
     */
    public boolean isCloudflareChallenge() {
        return false;
    }

    /**
     * Returns true if Cloudflare is returning a bot-block (403/1020).
     */
    public boolean isCloudflareBlocked() {
        return false;
    }


// ─────────────────────────────────────────────────────────────
// REDIRECT CHAIN — Grailed redirects listing URLs
// ─────────────────────────────────────────────────────────────

    /**
     * The final URL after all redirects were followed.
     * e.g. /listings/123 → /listings/123-nike-dunk-low
     * Essential for building correct item URLs.
     */
    public URI finalUrl() {
        return URI.create("");
    }

    /**
     * The full chain of URLs visited to reach this response.
     * [ original → redirect1 → redirect2 → final ]
     */
    public List<URI> redirectChain() {
        return List.of();
    }

    /**
     * How many redirects were followed to get this response.
     */
    public int redirectCount() {
        return redirectChain().size() - 1;
    }


// ─────────────────────────────────────────────────────────────
// RATE LIMITING — Grailed returns 429 with Retry-After
// ─────────────────────────────────────────────────────────────

    /**
     * Returns true if this is a 429 Too Many Requests response.
     */
    public boolean isRateLimited() {
        return false;
    }

    /**
     * Parses the Retry-After header and returns how many seconds
     * to wait before retrying. Returns -1 if header is absent.
     */
    public long retryAfterSeconds() {
        return 0;
    }


// ─────────────────────────────────────────────────────────────
// COOKIE JAR — Grailed needs cookies sent back every request
// ─────────────────────────────────────────────────────────────

    /**
     * Extract all Set-Cookie entries from this response
     * and merge them into the provided cookie jar for reuse
     * in the next request.
     */
    public void mergeCookiesInto(CookieJar jar) {

    }

    /**
     * Returns true if the response set or updated any cookies.
     * Useful for detecting session refresh.
     */
    public boolean hasNewCookies() {
        return false;
    }


// ─────────────────────────────────────────────────────────────
// CONTENT TYPE HELPERS — know what kind of body you got back
// ─────────────────────────────────────────────────────────────

    /**
     * Returns true if Content-Type is text/html.
     * Grailed listing pages → HTML.
     */
    public boolean isHtml() {
        return false;
    }

    /**
     * Returns true if Content-Type is application/json.
     * Grailed API endpoints → JSON.
     */
    public boolean isJson() {
        return false;
    }

    /**
     * Returns the raw Content-Type header value.
     */
    public String contentType() {
        return headers.get("Content-Type").get(0);
    }


// ─────────────────────────────────────────────────────────────
// CACHING — avoid re-fetching unchanged pages
// ─────────────────────────────────────────────────────────────

    /**
     * Returns the ETag header value if present.
     * Send this back as If-None-Match on the next request —
     * server returns 304 Not Modified if page hasn't changed.
     */
    public Optional<String> eTag() {
        return Optional.empty();
    }

    /**
     * Returns true if this is a 304 Not Modified —
     * meaning your cached version is still valid, don't re-parse.
     */
    public boolean isNotModified() {
        return false;
    }
}
