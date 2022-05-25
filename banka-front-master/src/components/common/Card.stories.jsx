import React from "react";

import Card from "./Card";

export default {
    title: "Common/Card",
    component: Card,
};

const defaultNodes = (
    <div>
        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Enim facilisis gravida neque convallis. Phasellus vestibulum lorem sed risus ultricies tristique nulla aliquet enim. Mauris vitae ultricies leo integer malesuada nunc vel risus commodo. Libero nunc consequat interdum varius sit amet mattis vulputate. Rhoncus mattis rhoncus urna neque viverra justo nec ultrices dui. Cursus sit amet dictum sit amet justo donec enim. Sagittis id consectetur purus ut faucibus. Aliquam sem et tortor consequat id. Aliquet lectus proin nibh nisl. Blandit cursus risus at ultrices mi. Ipsum faucibus vitae aliquet nec ullamcorper sit amet risus. Facilisis sed odio morbi quis commodo. In fermentum et sollicitudin ac. In ornare quam viverra orci sagittis eu volutpat odio facilisis. Nisl nisi scelerisque eu ultrices vitae auctor eu. Hendrerit gravida rutrum quisque non tellus. Magna etiam tempor orci eu lobortis elementum nibh. Lacinia quis vel eros donec ac odio tempor.</p>
    </div>
)

const Template = (args) => <Card {...args} />;
export const Default = Template.bind({});
Default.args = {
    title: "Title",
    children: defaultNodes,
};