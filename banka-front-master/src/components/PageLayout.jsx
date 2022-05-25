import React from "react";
import { Outlet } from "react-router-dom";

export default function PageLayout() {

    return (
        <div className="container mx-auto pt-10">
            <Outlet />
        </div>
    )
}