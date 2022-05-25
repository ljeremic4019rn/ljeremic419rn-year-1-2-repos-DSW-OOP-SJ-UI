import React from "react";
import PropTypes from "prop-types";
import classNames from "classnames";

const TEXT_FIELD_TYPE = {
  TEXT: 'text',
  PASSWORD: 'password',
}
function TextField(props) {
  const classes = classNames(
    "border-2 border-gray-300",
    "min-widht",
    "font-sans",
    "text-base",
    "bg-white-50", // background
    "text-gray text-base", // text
    "py-2 px-4", //spacing
    "rounded", // border
    "transition ease-in-out", // effects
    props.className // custom style
  );

  return (
    <input type={props.type} placeholder={props.placeholder} className={classes} onChange={(e) => props.onChange(e.target.value)} />
  );
}

TextField.propTypes = {
  type: PropTypes.oneOf([TEXT_FIELD_TYPE.TEXT, TEXT_FIELD_TYPE.PASSWORD]),
  className: PropTypes.string,
  onChange: PropTypes.func,
}

TextField.defaultProps = {
  type: TEXT_FIELD_TYPE.TEXT,
}

export default TextField
