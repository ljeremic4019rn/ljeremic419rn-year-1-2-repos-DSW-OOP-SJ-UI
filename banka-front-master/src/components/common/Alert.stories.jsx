import React from "react";

import Alert, { ALERT_TYPES } from "./Alert";

export default {
    title: "Common/Alert",
    component: Alert,
};

const Template = (args) => <Alert {...args} />;

export const Success = Template.bind({});
Success.args = {
    text: "Placeholder text",
    design: ALERT_TYPES.SUCCESS
};

export const Warning = Template.bind({});
Warning.args = {
    text: "Placeholder text",
    design: ALERT_TYPES.WARNING
};

export const Danger = Template.bind({});
Danger.args = {
    text: "Placeholder text",
    design: ALERT_TYPES.DANGER
};

