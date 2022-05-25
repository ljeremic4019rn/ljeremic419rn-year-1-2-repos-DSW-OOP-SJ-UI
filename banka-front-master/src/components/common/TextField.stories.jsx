import React from "react";

import TextField from "./TextField";

export default {
  title: "Common/TextField",
  component: TextField,
};

const Template = (args) => <TextField {...args} />;

export const Default = Template.bind({});
Default.args = {
  placeholder: "Placeholder",
};

export const Password = Template.bind({});
Password.args = {
  placeholder: "Placeholder",
  type: "password",
  onChange: (value) => console.log(value)
};
