import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import DashboardPageLayout from "./components/DashboardPageLayout";
import DashboardPage from "./pages/dashboard/index";
import InformationPage from "./pages/dashboard/information";
import PrivacyPage from "./pages/dashboard/privacy";
import ListPage from "./pages/dashboard/list";
import PageLayout from "./components/PageLayout";
import App from "./App";
import NewUserPage from "./pages/dashboard/newuser";
import LoginPage from "./pages/login";

export const URLS = {
	DASHBOARD: {
		INDEX: "/",
		INFORMATION: "information",
		PRIVACY: "privacy",
		LIST: {
			INDEX: "list",
			NEW_USER: "list/new-user",
		},
	},
	LOGIN: "login",
	REGISTER: "register",
};

export function AppRoutes() {
	return (
		<BrowserRouter>
			<Routes>
				<Route element={<App />}>
					<Route element={<DashboardPageLayout />}>
						<Route
							path={URLS.DASHBOARD.INDEX}
							element={<DashboardPage />}
						/>
						<Route
							path={URLS.DASHBOARD.INFORMATION}
							element={<InformationPage />}
						/>
						<Route
							path={URLS.DASHBOARD.PRIVACY}
							element={<PrivacyPage />}
						/>
						<Route
							path={URLS.DASHBOARD.LIST.INDEX}
							element={<ListPage />}
						/>
						<Route
							path={URLS.DASHBOARD.LIST.NEW_USER}
							element={<NewUserPage />}
						/>
					</Route>
					<Route element={<PageLayout />}>
						<Route path={URLS.LOGIN} element={<LoginPage />} />
					</Route>
				</Route>
			</Routes>
		</BrowserRouter>
	);
}
