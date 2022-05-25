import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom"
import { authGetToken } from "../auth";
import { URLS } from "../routes"

function AuthRequired(props) {
    let navigate = useNavigate();
    const token = authGetToken()

    useEffect(() => {
        if (!token) navigate(URLS.LOGIN)
    }, [token])

    return (
        <div>
            {props.children}
        </div>
    )
}


export default AuthRequired