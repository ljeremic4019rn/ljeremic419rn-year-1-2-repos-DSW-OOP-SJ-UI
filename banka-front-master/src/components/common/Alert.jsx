
import React from "react";
import PropTypes from "prop-types";
import classNames from "classnames";
import ExitButton from "./ExitButton";

export const ALERT_TYPES = {
    SUCCESS: "success",
    WARNING: "warning",
    DANGER: "danger",
}

function Alert(props) {
    const style = classNames(
        "p-3 w-full",
        "text-white font-bold",
        { "bg-emerald-400": props.design === ALERT_TYPES.SUCCESS },
        { "bg-amber-400": props.design === ALERT_TYPES.WARNING },
        { "bg-red-400": props.design === ALERT_TYPES.DANGER },
    )

    return (
        <div className={style} >
            <div className="flex justify-between">
                <div>
                    {props.text}
                </div>
                <ExitButton color="text-white hover:text-gray-200" onClick={props.onDismiss} />
            </div>
        </div>
    );
}

Alert.propTypes = {
    text: PropTypes.string.isRequired,
    design: PropTypes.oneOf([ALERT_TYPES.SUCCESS, ALERT_TYPES.WARNING, ALERT_TYPES.DANGER]).isRequired,
    onDismiss: PropTypes.func.isRequired,
}

export default Alert