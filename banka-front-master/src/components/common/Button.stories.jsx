import React from "react";

import Button, { BUTTON_DESIGN } from "./Button";

export default {
  title: "Common/Button",
  component: Button,
  argTypes: {
    design: {
      control: { type: 'select' }
    },
    onClick: { action: "clicked" },
  },
};

const Template = (args) => <Button {...args} />;

export const Primary = Template.bind({});
Primary.args = {
  label: "Button",
  design: BUTTON_DESIGN.PRIMARY,
  disabled: false,
};

export const Secondary = Template.bind({});
Secondary.args = {
  label: "Button",
  design: BUTTON_DESIGN.SECONDARY,
  disabled: false,
};

export const Inline = Template.bind({});
Inline.args = {
  label: 'Inline',
  design: BUTTON_DESIGN.INLINE,
  disabled: false,
};