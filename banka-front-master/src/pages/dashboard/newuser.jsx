
import React from "react"
import Card from "../../components/common/Card"
import TextField from "../../components/common/TextField"
import Button from "../../components/common/Button"

export default function NewUserPage() {

    return (
        <div className="w-[500px]">
            <Card title="Register new user">
                <div className="flex flex-col gap-3">
                    <TextField placeholder="Ime" />
                    <TextField placeholder="Prezime" />
                    <TextField placeholder="E-mail" />
                    <TextField placeholder="JMBG" />
                    <TextField placeholder="Broj telefona" />
                    <TextField placeholder="Pozicija u banci" />
                    <Button label="Register" />
                </div>
            </Card>
        </div>
    )
}