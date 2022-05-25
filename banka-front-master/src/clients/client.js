import { get, post } from "./api";
import { authSaveToken } from "../auth";

const BASE_URL = "http://localhost:8080/api";

export async function loginAction(username, password, otp = null) {
	let url = new URL(BASE_URL + "/login");
	let params = new URLSearchParams(url.search);
	params.append("username", username);
	params.append("password", password);
	if (otp) {
		params.append("otp", otp);
	}
	url = url + "?" + params;
	const r = await post(url);
	authSaveToken(r);
	return r;
}

export function getUsersAction() {
	let url = new URL(BASE_URL + "/users");
	return get(url);
}
