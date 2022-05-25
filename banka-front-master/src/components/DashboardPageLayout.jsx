import React from "react"
import Sidebar from "./Sidebar"
import { Outlet } from "react-router-dom"
import AuthRequired from "./AuthRequired"

function DashboardPageLayout() {

    return (
        <AuthRequired>
            <div className="flex h-screen">
                <Sidebar />
                <div className="grow p-10">
                    <Outlet />
                </div>
            </div>
        </AuthRequired>
    )
}

export default DashboardPageLayout