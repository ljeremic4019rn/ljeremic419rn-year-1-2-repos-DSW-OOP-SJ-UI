import { authGetToken } from "../auth";

export function get(url) {
	return request("get", url);
}

export function post(url, body = null) {
	return request("post", url, body);
}

async function request(method, url, body) {
	const token = authGetToken();
	try {
		const response = await fetch(url, {
			method: method,
			body: JSON.stringify(body),
			mode: "cors",
			headers: {
				Authorization: "Bearer " + token,
			},
		});
		if (!response.ok) {
			throw Error("Request failed");
		}
		return await response.json();
	} catch (e) {
		throw e;
	}
}
