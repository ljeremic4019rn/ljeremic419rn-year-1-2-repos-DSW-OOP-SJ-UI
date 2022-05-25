import React from "react";
import PropTypes from "prop-types";
import classNames from "classnames";


function Card(props) {
    const cardStyle = classNames(
        "border border-gray-300 rounded-2xl",
        "p-8",
        "bg-white",
        props.className,
    )

    const titleStyle = classNames(
        "relative px-3 left-20 top-3.5", // position
        "bg-white ", // background
        "text-xl",
    )

    return (

        <div>
            <span className={titleStyle}>
                <b>{props.title}</b>
            </span>
            <div className={cardStyle}>
                <div>
                    {props.children}
                </div>
            </div>
        </div>
    );
}

Card.propTypes = {
    title: PropTypes.string.isRequired,
    children: PropTypes.node,
    className: PropTypes.string,
}

export default Card