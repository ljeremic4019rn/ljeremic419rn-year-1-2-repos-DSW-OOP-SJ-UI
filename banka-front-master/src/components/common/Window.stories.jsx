
import React from "react";
import Button from "./Button";
import TextField from "./TextField";
import Window from "./Window";


export default {
    title: "Common/Window",
    component: Window,
};

const Template = (args) => <Window {...args} />;

const deafultChildren = (
    <div className="flex flex-col gap-3">
        <p>Text</p>
        <TextField placeholder="Placeholder" />
        <TextField placeholder="Placeholder" />
        <TextField placeholder="Placeholder" />
        <Button label="Button" />
    </div>
)

export const Default = Template.bind({});
Default.args = {
    title: "Title",
    children: deafultChildren,
};