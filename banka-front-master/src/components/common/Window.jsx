import React from "react";
import PropTypes from "prop-types";
import classNames from "classnames";

function Window(props) {
    const formWrapperClasses = classNames(
        "box-border border border-gray-300",
        "h-full w-[500px]",
        "border-1",
        "bg-gray-50",
        props.className,
    )

    const tittleClasses = classNames(
        "box-border",
        "text-2xl text-white font-semibold",
        "py-2 px-10",
        "border-1",
        "bg-gray-500"
    )

    return (
        <div className={formWrapperClasses}>
            <div className={tittleClasses}>
                {props.title}
            </div>
            <div className="py-6 px-10">
                {props.children}
            </div>
        </div>
    )
}

Window.propTypes = {
    title: PropTypes.string.isRequired,
    children: PropTypes.node.isRequired,
    className: PropTypes.string,
}

export default Window