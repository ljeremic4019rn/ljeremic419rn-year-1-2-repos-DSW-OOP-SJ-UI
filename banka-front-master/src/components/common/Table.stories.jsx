import React from "react";

import Table from "./Table";
import Button, { BUTTON_DESIGN } from "./Button";

export default {
  title: "Common/Table",
  component: Table,
};

const Template = (args) => <Table {...args} />;

export const Default = Template.bind({});
Default.args = {
  headings: ['Ime i prezime', 'E-mail', 'JMBG', 'Broj telefona', 'Pozicija u banci', 'Akcija'],
  rows: [
    [<span className="text-black">Jon Doe 1</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 2</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 3</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 4</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 5</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 6</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 7</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
  ],
};

export const EmptyState = Template.bind({});
EmptyState.args = {
  headings: ['Ime i prezime', 'E-mail', 'JMBG', 'Broj telefona', 'Pozicija u banci', 'Akcija'],
  rows: [],
};


export const WithPagination = Template.bind({});
WithPagination.args = {
  headings: ['Ime i prezime', 'E-mail', 'JMBG', 'Broj telefona', 'Pozicija u banci', 'Akcija'],
  rows: [
    [<span className="text-black">Jon Doe 1</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 2</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 3</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 4</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 5</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 6</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 7</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 1</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 2</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 3</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 4</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 5</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 6</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 7</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 1</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 2</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 3</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 4</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 5</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 6</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 7</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 1</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 2</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 3</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 4</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 5</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 6</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 7</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 1</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 2</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 3</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 4</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 5</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 6</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 7</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 1</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 2</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 3</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 4</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 5</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 6</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 7</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 1</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 2</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 3</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 4</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 5</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 6</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
    [<span className="text-black">Jon Doe 7</span>, 'jdoe@gmail.com', '0123456789', '060/12345678', 'Developer', <Button design={BUTTON_DESIGN.INLINE} label="Button" />],
  ],
  pagination: true,
};