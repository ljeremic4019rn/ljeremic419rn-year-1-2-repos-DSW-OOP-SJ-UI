
import React from "react";

import Form from "./Form";

export default {
  title: "Components/Form",
  component: Form,
};

const Template = (args) => <Form {...args} />;

export const Login = Template.bind({});
Login.args = {
  title: "PRIJAVA",
  fields: [['Korisničko ime'], ['Šifra', 'password']],
  link: ["Zaboravili ste šifru?", 'wwww.google.com'],
  button: "Prijavite se"
};

export const ChangePassword = Template.bind({});
ChangePassword.args = {
  title: "PROMENITE ŠIFRU",
  fields: [['Šifra', 'password'], ['Ponovite šifru', 'password']],
  button: "Prijavite se"
};

export const TwoFactorAuth = Template.bind({});
TwoFactorAuth.args = {
  title: "2-FACTOR AUTENTIFIKACIJA",
  info: "Vaš administator zahteva da unesete 6-cifreni kod iz Google authenticatora kako biste pristupili Vašem nalogu.",
  fields: [['Kod']],
  button: "Nastavite"
};


export const NewEmployee = Template.bind({});
NewEmployee.args = {
  title: "DODAVANJE ZAPOSLENOG",
  fields: [['Ime'], ['Prezime'], ['E-mail'], ['JMBG'], ['Broj telefona'], ['Pozicija u banci']],
  button: "Dodajte zaposlenog"
};