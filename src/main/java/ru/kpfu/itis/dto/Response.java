package ru.kpfu.itis.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Response<T> {

	private T result;
	private Error error;
	@JsonIgnore
	private HttpStatus status;
	@JsonIgnore
	private HttpHeaders headers;

	/**
	 * Ответ с 200 http-кодом
	 *
	 * Основной код ответа. Обычно используется для того, чтобы показать успешность выполненой операции
	 *
	 * @param result данные ответа
	 * @param <T> тип данных ответа
	 * @return ответ с данными и http-кодом
	 */
	public static <T> Response<T> ok(T result) {
		return new Response<>(result, HttpStatus.OK);
	}

	public static Response ok() {
		return new Response<>(HttpStatus.OK);
	}

	/**
	 * Ответ с 201 http-кодом
	 *
	 * Подтверждение успешного создания (например посредством POST или PUT запроса).
	 * Заголовок Location содержит ссылку на только что созданный ресурс (при POST запросе).
	 * Тело ответа может быть пустым. Обычно так и бывает.
	 *
	 * @param result данные ответа
	 * @param <T> тип данных ответа
	 * @return ответ с данными и http-кодом
	 */
	public static <T> Response<T> created(T result) {
		return new Response<>(result, HttpStatus.CREATED);
	}

	public static Response created() {
		return new Response<>(HttpStatus.CREATED);
	}

	/**
	 * Ответ с 202 http-кодом.
	 *
	 * Запрос принят в обработку, но еще не завершен.
	 * Нет никаких гарантий, что запрос успешно выполнится в процессе обработки данных.
	 * Из-за асинхронного типа выполняемой операции отсутствует возможность повторной отправки статуса
	 *
	 * @param result данные ответа
	 * @param <T> тип данных ответа
	 * @return ответ с данными и http-кодом
	 */
	public static <T> Response<T> accepted(T result) {
		return new Response<>(result, HttpStatus.ACCEPTED);
	}

	public static Response accepted() {
		return new Response<>(HttpStatus.ACCEPTED);
	}

	/**
	 * Ответ с 204 http-кодом.
	 *
	 * Запрос был успешно обработан, но нет необходимости возвращать какие-либо данные.
	 * Так же в ответе может возвращаться новая, или обновленная информация, однако в итоге она не будет отличаться
	 * о того, что было изначально послано на сервер и, таким образом,
	 * считается что клиент и так обладает актуальной информацией.
	 *
	 * 204 - должен не содержать тела ответа.
	 * Если таковая имеется - она обычно игнорируется и считается, что после заголовков присутствет одна пустая линия.
	 *
	 * @return ответ с данными и http-кодом
	 */
	public static Response noContent() {
		return new Response<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Ответ с 400 http-кодом.
	 *
	 * Запрос не удалось обработать из-за синтаксической ошибки.
	 * Клиенту не следует повторять такой запрос без изменений.
	 *
	 * @param error информация об ошибке
	 * @return ответ с ошибками и http-кодом
	 */
	public static Response badRequest(Error error) {
		return new Response<>(error, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Ответ с 401 http-кодом.
	 *
	 * Запрос требует аунтефикации пользователя. Ответ должен содердать WWW-Authenticate заголовок (раздел 14.47).
	 * Клиент может повторить запрос с корректным Authorization заголовком (раздел 14.8).
	 * Если запрос уже содержит информацию для авторизации, в таком случае 401 код ответа показывает,
	 * что авторизация была отклонена.
	 *
	 * @param error информация об ошибке
	 * @return ответ с ошибками и http-кодом
	 */
	public static Response unauthorized(Error error) {
		return new Response<>(error, HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Ответ с 403 http-кодом.
	 *
	 * Сервер понял запрос, но отказывается его обрабатывать. Авторизация не поможет и этот запрос не следует повторять.
	 *
	 * @param error информация об ошибке
	 * @return ответ с ошибками и http-кодом
	 */
	public static Response forbidden(Error error) {
		return new Response<>(error, HttpStatus.FORBIDDEN);
	}

	/**
	 * Ответ с 404 http-кодом.
	 *
	 * Используется тогда, когда ресурс не был найден, не существует или был 401 или 403,
	 * но из соображений безопасности сервер не ответил этими кодами
	 *
	 * @param error информация об ошибке
	 * @return ответ с ошибками и http-кодом
	 */
	public static Response notFound(Error error) {
		return new Response<>(error, HttpStatus.NOT_FOUND);
	}

	/**
	 * Ответ с 410 http-кодом.
	 *
	 * Требуемый ресурс больше не доступен на сервере и адрес его расположения не известен.
	 * Предполагается, что это состояние постоянно
	 *
	 * @param error информация об ошибке
	 * @return ответ с ошибками и http-кодом
	 */
	public static Response gone(Error error) {
		return new Response<>(error, HttpStatus.GONE);
	}

	/**
	 * Ответ с 429 http-кодом.
	 *
	 * Код состояния 429 указывает, что пользователь отправил слишком много запросов
	 * за определенный промежуток времени («ограничение скорости»).
	 * Ответа должен включать подробности, объясняющие условие, и может включать заголовок Retry-After, указывающий,
	 * как долго ждать перед выполнением нового запроса.
	 *
	 * @param error информация об ошибке
	 * @param retryAfter заголовок, указывающий, как долго ждать перед выполнением нового запроса
	 * @return ответ с ошибками и http-кодом
	 */
	public static Response tooManyRequests(Error error, String retryAfter) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.RETRY_AFTER, retryAfter);
		return new Response<>(error, HttpStatus.TOO_MANY_REQUESTS, headers);
	}

	/**
	 * Ответ с 500 http-кодом.
	 *
	 * Сервер столкнулся с неожиданными условиями, которые не позволили ему обработать запрос.
	 * Универсальный код ошибки, когда на стороне сервера произошло исключение
	 *
	 * @param error информация об ошибке
	 * @return ответ с ошибками и http-кодом
	 */
	public static Response internalServerError(Error error) {
		return new Response<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private Response(HttpStatus  status) {
		this.status = status;
	}

	private Response(T result, HttpStatus  status) {
		this.result = result;
		this.status = status;
	}

	private Response(Error error, HttpStatus status) {
		this.error = error;
		this.status = status;
	}

	private Response(Error error, HttpStatus status, HttpHeaders headers) {
		this.error = error;
		this.status = status;
		this.headers = headers;
	}

	@JsonIgnore
	public boolean isEmpty() {
		return result == null && error == null;
	}

	@Setter
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class Error {
		private String message;
		private Type type;
		@Singular
		private List<Detail> details;

		@Getter
		@AllArgsConstructor
		public enum Type {
			USER_NOT_FOUND("user-not-found"),
			USER_NOT_REGISTERED("user-not-registered"),
			NOT_FOUND("not-found"),
			NO_MATCH("no-match"),
			ALREADY_EXIST("already-exist"),
			FORBIDDEN("forbidden"),
			NOT_AUTHORIZED("not-authorized"),
			BAD_REQUEST("bad-request"),
			BAD_GATEWAY("bad-gateway"),
			GATEWAY_TIMEOUT("gateway-timeout"),
			REQUEST_FAILED("request-failed"),
			BAD_CREDENTIALS("bad-credentials"),
			TOO_MANY_REQUESTS("too-many-requests"),
			VALIDATION_ERROR("validation-error"),
			GONE("gone");

			@JsonValue
			private String type;
		}

		@Setter
		@Getter
		@Builder
		@NoArgsConstructor
		@AllArgsConstructor
		@JsonInclude(JsonInclude.Include.NON_NULL)
		public static class Detail {
			private String message;
			private Type type;
			private String error;
			private String target;

			@Getter
			@AllArgsConstructor
			public enum Type {
				INVALID_PASSWORD("invalid-password"),
				INVALID_EMAIL("invalid-email"),
				NOT_CORRECT("not-correct"),
				EMPTY_PARAM("empty-param"),
				DUPLICATE("duplicate");
				private String type;
			}
		}
	}
}