import React from "react";
import PropTypes from "prop-types";
import classNames from "classnames";

export const BUTTON_DESIGN = {
  PRIMARY: 'primary',
  SECONDARY: 'secondary',
  INLINE: 'inline',
}

function Button(props) {
  const primaryStyle = classNames(
    "bg-indigo-500 hover:bg-indigo-700", // background
    "text-white text-base", // text
    "py-2 px-4", //spacing
    "rounded", // border
    "transition ease-in-out", // effects
    { 'opacity-60 bg-gray-300 hover:bg-gray-300 text-black cursor-not-allowed': props.disabled },
    props.className // custom style
  )

  const secondaryStyle = classNames(
    "text-indigo-500 hover:text-indigo-700 text-base", // text
    "py-2 px-4", //spacing
    "bg-transparent hover:bg-indigo-50", // background
    "border rounded border-indigo-500 hover:border-indigo-700", //border
    "transition ease-in-out", // effects
    { 'opacity-60 text-gray-400 hover:text-gray-400  cursor-not-allowed': props.disabled },
    props.className // custom style
  )

  const inlineStyle = classNames(
    "text-blue-500 hover:text-blue-700 font-bold text-base hover:underline", // text
    "transition ease-in-out", // effects
    { 'opacity-60 text-gray-400 hover:text-gray-400  cursor-not-allowed': props.disabled },
    props.className // custom style
  )

  let style = null
  switch (props.design) {
    case BUTTON_DESIGN.PRIMARY:
      style = primaryStyle
      break
    case BUTTON_DESIGN.SECONDARY:
      style = secondaryStyle
      break
    case BUTTON_DESIGN.INLINE:
      style = inlineStyle
      break
    default:
      style = primaryStyle
      break


  }

  return (
    <button
      className={style}
      type="button"
      onClick={props.onClick}
    >
      {props.label}
    </button>
  )
}

Button.propTypes = {
  label: PropTypes.string.isRequired,
  design: PropTypes.oneOf([BUTTON_DESIGN.PRIMARY, BUTTON_DESIGN.SECONDARY, BUTTON_DESIGN.INLINE]),
  disabled: PropTypes.bool,
  onClick: PropTypes.func,
  className: PropTypes.string,
};

Button.defaultProps = {
  design: BUTTON_DESIGN.PRIMARY,
  disabled: false,
  className: '',
  onClick: () => { }
};

export default Button;
