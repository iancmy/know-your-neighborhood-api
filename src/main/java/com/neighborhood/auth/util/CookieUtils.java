package com.neighborhood.auth.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.util.SerializationUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtils {
	public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();

		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return Optional.of(cookie);
				}
			}
		}

		return Optional.empty();
	}

	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		// Cookie cookie = new Cookie(name, value);
		// cookie.setPath("/");
		// cookie.setSecure(true);
		// cookie.setHttpOnly(true);
		// cookie.setMaxAge(maxAge);
		// response.addCookie(cookie);

		ResponseCookie cookie = ResponseCookie.from(name, value)
				.path("/")
				.secure(true)
				.httpOnly(true)
				.maxAge(maxAge)
				.sameSite("None")
				.build();
		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
	}

	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					cookie.setValue("");
					cookie.setPath("/");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
	}

	public static String serialize(Object object) {
		return Base64.getUrlEncoder()
				.encodeToString(SerializationUtils.serialize(object));
	}

	public static <T> T deserialize(Cookie cookie, Class<T> cls) throws IOException, ClassNotFoundException {
		byte[] cookieValue = Base64.getUrlDecoder().decode(cookie.getValue());
		ByteArrayInputStream bis = new ByteArrayInputStream(cookieValue);
		ObjectInputStream ois = new ObjectInputStream(bis);
		Object object = ois.readObject();

		return cls.cast(object);
	}
}
