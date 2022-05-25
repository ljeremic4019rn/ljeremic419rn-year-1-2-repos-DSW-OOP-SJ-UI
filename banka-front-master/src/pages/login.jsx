
import React, { useState } from "react"
import { useNavigate } from "react-router-dom"
import Window from "../components/common/Window"
import TextField from "../components/common/TextField"
import Button from "../components/common/Button"
import Alert from "../components/common/Alert"
import { loginAction } from "../clients/client"
import { URLS } from "../routes"

export default function LoginPage() {
    let navigate = useNavigate();
    const [username, setUsername] = useState(null)
    const [password, setPassword] = useState(null)
    const [error, setError] = useState(null)

    function disableCta() {
        if (username == null || password == null) return true
        return false
    }

    async function login() {
        try {
            await loginAction(username, password)
            navigate(URLS.DASHBOARD.INDEX)
        } catch {
            setError("Failed to login.")
        }
    }

    return (
        <Window title="Welcome" className="mx-auto">
            <div className="flex flex-col gap-3">
                {error && <Alert design="danger" text={error} onDismiss={() => setError(null)} />}
                <TextField placeholder="Korisnicko ime" onChange={setUsername} />
                <TextField placeholder="Lozinka" onChange={setPassword} />
                <Button label="Login" disabled={disableCta()} onClick={login} />
            </div>
        </Window>
    )
}