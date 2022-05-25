export function authSaveToken(token) {
	localStorage.setItem("token", token);
}

export function authGetToken() {
	return localStorage.getItem("token");
}
